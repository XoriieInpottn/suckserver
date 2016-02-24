package org.lioxa.ustc.suckserver.log;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.lioxa.ustc.suckserver.common.vo.Log;
import org.lioxa.ustc.suckserver.common.vo.Task;
import org.lioxa.ustc.suckserver.utils.Utils;

/**
 * An implementation of Logger interface.<br/>
 * DBLogWriter is used to read/write logs from/to the database.<br/>
 * Note that the session of the database is obtained from the global Utils.
 *
 * @author xi
 * @since Jan 28, 2016
 */
public class DBLogger implements Logger {

    @Override
    public synchronized Task writeTask(String name, boolean isTest) {
        Session dbSession;
        try {
            dbSession = Utils.getDBSession();
            dbSession.beginTransaction();
        } catch (RuntimeException e) {
            throw new LogException("Failed to write task.", e);
        }
        try {
            Task task = new Task();
            task.setName(name);
            task.setType(isTest ? Task.TYPE_TEST : Task.TYPE_DEPLOY);
            task.setStatus(Task.STATUS_RUNNING);
            task.setStartTime(new Date());
            dbSession.save(task);
            dbSession.getTransaction().commit();
            return task;
        } catch (RuntimeException e) {
            dbSession.getTransaction().rollback();
            throw new LogException("Failed to write task.", e);
        } finally {
            dbSession.close();
        }
    }

    @Override
    public synchronized Task updateTask(long tid, int status) {
        Session dbSession;
        try {
            dbSession = Utils.getDBSession();
            dbSession.beginTransaction();
        } catch (RuntimeException e) {
            throw new LogException("Failed to update task.", e);
        }
        try {
            //
            // get Task VO from database
            String hql = "from Task where id=:tid";
            Query q = dbSession.createQuery(hql);
            q.setLong("tid", tid);
            Task task = (Task) q.uniqueResult();
            //
            // update
            task.setStatus(status);
            switch (status) {
            case Task.STATUS_COMPLETE:
            case Task.STATUS_ERROR:
            case Task.STATUS_STOP:
                task.setEndTime(new Date());
                break;
            default:
            }
            dbSession.update(task);
            dbSession.getTransaction().commit();
            return task;
        } catch (RuntimeException e) {
            dbSession.getTransaction().rollback();
            throw new LogException("Failed to update task.", e);
        } finally {
            dbSession.close();
        }
    }

    @Override
    public Task readTask(long tid) {
        Task task;
        Session dbSession;
        try {
            dbSession = Utils.getDBSession();
            dbSession.beginTransaction();
        } catch (RuntimeException e) {
            throw new LogException("Failed to read task.", e);
        }
        try {
            Query q = dbSession.createQuery("from Task where id=:tid");
            q.setLong("tid", tid);
            task = (Task) q.uniqueResult();
            dbSession.getTransaction().commit();
        } catch (RuntimeException e) {
            dbSession.getTransaction().rollback();
            throw new LogException("Failed to read task.", e);
        } finally {
            if (dbSession != null) {
                dbSession.close();
            }
        }
        return task;
    }

    @Override
    public Object[] readTasks(int start, int count) {
        List<Task> lst;
        long totalCount;
        Session dbSession;
        try {
            dbSession = Utils.getDBSession();
            dbSession.beginTransaction();
        } catch (RuntimeException e) {
            throw new LogException("Failed to read tasks.", e);
        }
        try {
            Query q;
            //
            // Query total count.
            q = dbSession.createQuery("SELECT COUNT(*) FROM Task");
            totalCount = (long) q.uniqueResult();
            //
            // Query list.
            q = dbSession.createQuery("FROM Task ORDER BY startTime DESC");
            if (start >= 0) {
                q.setFirstResult(start);
            }
            if (count > 0) {
                q.setMaxResults(count);
            }
            @SuppressWarnings("unchecked")
            List<Task> lst1 = q.list();
            lst = lst1;
            dbSession.getTransaction().commit();
        } catch (RuntimeException e) {
            dbSession.getTransaction().rollback();
            throw new LogException("Failed to read tasks.", e);
        } finally {
            dbSession.close();
        }
        return new Object[] { lst, totalCount };
    }

    @Override
    public synchronized Log writeLog(long tid, String content) {
        return writeLog(tid, Log.TYPE_INFO, content);
    }

    @Override
    public synchronized Log writeError(long tid, String content) {
        return writeLog(tid, Log.TYPE_ERROR, content);
    }

    @Override
    public synchronized Log writeError(long tid, Throwable e) {
        return writeLog(tid, Log.TYPE_ERROR, makeMsg(e).toString());
    }

    @Override
    public Log writeError(long tid, Throwable e, int lineNum, int colNum) {
        StringBuilder sb = makeMsg(e);
        sb.insert(0, String.format("line %d, column %d: ", lineNum, colNum));
        return writeLog(tid, Log.TYPE_ERROR, sb.toString());
    }

    static StringBuilder makeMsg(Throwable e) {
        StringBuilder sb = new StringBuilder();
        for (int lvl = 0; e != null; e = e.getCause(), lvl++) {
            String msg = e.getMessage();
            if (msg == null) {
                continue;
            }
            for (int i = 0; i < lvl; i++) {
                sb.append("    ");
            }
            sb.append(msg);
            sb.append(" Caused by:\n");
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - " Caused by:\n".length(), sb.length());
        }
        return sb;
    }

    static Log writeLog(long tid, int type, String content) {
        Session dbSession;
        try {
            dbSession = Utils.getDBSession();
            dbSession.beginTransaction();
        } catch (RuntimeException e) {
            throw new LogException("Failed to write log.", e);
        }
        try {
            Log log = new Log();
            log.setTid(tid);
            log.setType(type);
            log.setTime(new Date());
            log.setContent(content);
            dbSession.save(log);
            dbSession.getTransaction().commit();
            return log;
        } catch (RuntimeException e) {
            dbSession.getTransaction().rollback();
            throw new LogException("Failed to write log.", e);
        } finally {
            dbSession.close();
        }
    }

    @Override
    public synchronized List<Log> readLogs(long tid, Date after, int maxCount) {
        List<Log> lst;
        Session dbSession;
        try {
            dbSession = Utils.getDBSession();
            dbSession.beginTransaction();
        } catch (RuntimeException e) {
            throw new LogException("Failed to read logs.", e);
        }
        try {
            Query q;
            if (after != null) {
                String hql = "from Log where tid=:tid and time > :after order by time asc";
                q = dbSession.createQuery(hql);
                q.setLong("tid", tid);
                q.setTimestamp("after", after);
                if (maxCount > 0) {
                    q.setMaxResults(maxCount);
                }
            } else {
                String hql = "from Log where tid=:tid order by time asc";
                q = dbSession.createQuery(hql);
                q.setLong("tid", tid);
                if (maxCount > 0) {
                    q.setMaxResults(maxCount);
                }
            }
            @SuppressWarnings("unchecked")
            List<Log> lst1 = q.list();
            lst = lst1;
            dbSession.getTransaction().commit();
        } catch (RuntimeException e) {
            dbSession.getTransaction().rollback();
            throw new LogException("Failed to read logs.", e);
        } finally {
            dbSession.close();
        }
        return lst;
    }

    @Override
    public void removeTask(long tid) {
        Session dbSession;
        try {
            dbSession = Utils.getDBSession();
            dbSession.beginTransaction();
        } catch (RuntimeException e) {
            throw new LogException("Failed to remove task.", e);
        }
        try {
            Query q;
            String hql = "from Task where id=:tid";
            q = dbSession.createQuery(hql);
            q.setLong("tid", tid);
            Task task = (Task) q.uniqueResult();
            if (task.getStatus() == Task.STATUS_RUNNING) {
                String msg = String.format("Task %s is running, stop it first.", task.getName());
                throw new LogException(msg);
            }
            hql = "DELETE FROM Task WHERE id=:tid";
            q = dbSession.createQuery(hql);
            q.setLong("tid", tid);
            q.executeUpdate();
            hql = "DELETE FROM Log WHERE tid=:tid";
            q = dbSession.createQuery(hql);
            q.setLong("tid", tid);
            q.executeUpdate();
            dbSession.getTransaction().commit();
        } catch (RuntimeException e) {
            dbSession.getTransaction().rollback();
            if (e instanceof LogException) {
                throw e;
            }
            throw new LogException("Failed to read logs.", e);
        } finally {
            dbSession.close();
        }
    }

}
