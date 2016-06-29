package org.lioxa.ustc.suckserver;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.lioxa.ustc.suckserver.common.SuckService;
import org.lioxa.ustc.suckserver.common.vo.Log;
import org.lioxa.ustc.suckserver.common.vo.Task;
import org.lioxa.ustc.suckserver.common.vo.TaskStat;
import org.lioxa.ustc.suckserver.log.Loggers;

/**
 * This is the default implementation of SuckService.<br/>
 *
 * @author xi
 * @since Dec 25, 2015
 */
public class SuckServiceImpl extends UnicastRemoteObject implements SuckService {

    private static final long serialVersionUID = 3492094983970647247L;

    protected SuckServiceImpl() throws RemoteException {
        super();
    }

    TaskController controller = new TaskController();

    @Override
    public long createTask(String template, boolean isTest) throws RemoteException {
        RunnableTask runnableTask;
        try {
            runnableTask = this.controller.createTask(template, isTest);
        } catch (TaskCreationException e) {
            throw new RemoteException("Failed to create Task.", e);
        }
        return runnableTask.getId();
    }

    @Override
    public long createTaskFromFile(String path, boolean isTest) throws RemoteException {
        String template;
        try {
            template = FileUtils.readFileToString(new File(path));
        } catch (IOException e) {
            throw new RemoteException("Failed to read file.", e);
        }
        return this.createTask(template, isTest);
    }

    @Override
    public void stopTask(long tid) throws RemoteException {
        this.controller.stopTask(tid);
    }

    @Override
    public Object[] getTasks(int start, int count) throws RemoteException {
        Object[] tuple = Loggers.getDefault().readTasks(start, count);
        @SuppressWarnings("unchecked")
        List<Task> lst = (List<Task>) tuple[0];
        List<Object[]> lst1 = new ArrayList<>(lst.size());
        for (Task task : lst) {
            if (task.getStatus() != Task.STATUS_RUNNING) {
                lst1.add(new Object[] { task, null });
                continue;
            }
            //
            // The task statistics object.
            RunnableTask runnableTask = this.controller.getTask(task.getId());
            TaskStat taskStat = runnableTask.getTaskStat();
            lst1.add(new Object[] { task, taskStat });
        }
        tuple[0] = lst1;
        return tuple;
    }

    @Override
    public Object[] getTask(long tid) throws RemoteException {
        //
        // The task object.
        Task task = Loggers.getDefault().readTask(tid);

        if (task.getStatus() != Task.STATUS_RUNNING) {
            return new Object[] { task, null };
        }
        //
        // The task statistics object.
        RunnableTask runnableTask = this.controller.getTask(task.getId());
        String image = "";
        if (runnableTask != null) {
            image = runnableTask.getContext().getIcBase64();
            runnableTask.getContext().setIcBase64("");
        }
        TaskStat taskStat = runnableTask.getTaskStat();
        if (image.length() > 0) {
            return new Object[] { task, taskStat, image };
        } else {
            return new Object[] { task, taskStat };
        }
    }

    @Override
    public List<Log> getLogs(long tid, Date after, int maxCount) throws RemoteException {
        return Loggers.getDefault().readLogs(tid, after, maxCount);
    }

    @Override
    public void removeTask(long tid) throws RemoteException {
        Loggers.getDefault().removeTask(tid);
    }

    @Override
    public void sendIC(long tid, String icValue) throws RemoteException {
        RunnableTask runnableTask = this.controller.getTask(tid);
        System.out.println("SENDIC的value = " + icValue);
        runnableTask.getContext().setIcvalue(icValue);
    }

    //
    //
    //

    /**
     * Stop all running tasks.
     */
    public void stopAllTasks() {
        this.controller.stopAllTasks();
    }

}
