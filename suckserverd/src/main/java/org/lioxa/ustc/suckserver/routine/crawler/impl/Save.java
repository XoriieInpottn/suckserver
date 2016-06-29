package org.lioxa.ustc.suckserver.routine.crawler.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.lioxa.ustc.suckserver.common.vo.TaskStat;
import org.lioxa.ustc.suckserver.log.Loggers;
import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.lioxa.ustc.suckserver.utils.TableInfo;
import org.lioxa.ustc.suckserver.utils.Utils;

/**
 *
 * @author xi
 * @since Nov 24, 2015
 */
public class Save extends CrawlerRoutine {

    @Param(name = "table", essential = true)
    String table;

    @Param(name = "field")
    String field = "";

    //
    // add the timeStamp to the table
    long currentTime = 0L;
    int currentTimeCount = 0;

    //
    // execution

    @Override
    public void exec() throws ExecutionException {
        if (this.globalContext.isStopReq()) {
            return;
        }
        //
        // get table info
        TableInfo tblInfo = this.globalContext.getTables().get(this.table);
        if (tblInfo == null) {
            //
            // no table info means the lack of table definition before
            String msg = String.format("Could not find table \"%s\".", this.table);
            ExecutionException e = new ExecutionException(msg);
            e.setFatal(true);
            throw e;
        }
        //
        // make row
        Map<String, Object> row = new HashMap<>();
        for (String col : tblInfo.keySet()) {
            String value = this.globalContext.getVars().get(col).toString();
            if (value == null) {
                String msg = String.format("No value found for column \"%s\".", col);
                ExecutionException e = new ExecutionException(msg);
                e.setFatal(true);
                throw e;
            }
            row.put(col, value);
        }
        //
        // make sure that cannot insert a instance more than 1000 times per
        // 1000ms
        if (!this.globalContext.getVars().containsKey("currentTime")) {
            this.globalContext.getVars().put("currentTime", "0");
            this.globalContext.getVars().put("currentTimeCount", "0");
            this.currentTime = 0;
            this.currentTimeCount = 0;
        } else {
            this.currentTime = Long.parseLong(this.globalContext.getVars().get("currentTime").toString());
            this.currentTimeCount = Integer.parseInt(this.globalContext.getVars().get("currentTimeCount").toString());
        }
        long t = System.currentTimeMillis();
        if (this.currentTime == t) {
            this.currentTimeCount++;
            this.globalContext.getVars().put("currentTimeCount", this.currentTimeCount);
            if (this.currentTimeCount >= 1000) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    this.currentTimeCount = 0;
                    this.globalContext.getVars().put("currentTimeCount", this.currentTimeCount);
                }
                t = System.currentTimeMillis();
            }
        } else {
            this.currentTimeCount = 0;
            this.globalContext.getVars().put("currentTimeCount", this.currentTimeCount);
        }
        this.currentTime = t;
        this.globalContext.getVars().put("currentTime", this.currentTime);
        row.put("_timeStamp", this.currentTime);
        //
        // make SQL
        String[] cols = row.keySet().toArray(new String[0]);
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(tblInfo.getName());
        sb.append(" (");
        sb.append(StringUtils.join(cols, ", "));
        sb.append(")  VALUES (:");
        sb.append(cols[0]);
        for (int i = 1; i < cols.length; i++) {
            sb.append(", :");
            sb.append(cols[i]);
        }
        sb.append(");");
        long tid = this.globalContext.getRunnableTask().getId();
        //
        // do insert
        if (this.field.length() > 0) {
            String fields[] = this.field.split(",");
            // Map<String,String> map = new HashMap<>();
            String v[] = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                v[i] = this.globalContext.getVars().get(fields[i]).toString();
            }
            StringBuilder sb1 = new StringBuilder("SELECT * FROM ");
            sb1.append(this.table + " WHERE ");
            sb1.append(fields[0] + " = '" + v[0] + "'");
            for (int i = 1; i < fields.length; i++) {
                sb1.append(" AND " + fields[i] + " = '" + v[i] + "'");
            }
            Session dbSession = Utils.getDBSession();
            dbSession.beginTransaction();
            List<?> lst = null;
            try {
                lst = dbSession.createSQLQuery(sb1.toString()).list();
            } catch (RuntimeException e) {
                dbSession.getTransaction().rollback();
                throw new ExecutionException("Failed to insert instance", e);
            } finally {
                dbSession.close();
                if (lst != null) {
                    if (lst.size() > 0) {
                        Loggers.getDefault().writeLog(tid, "The table has included this info!");
                        return;
                    }
                }
            }
            // String v = this.globalContext.getVars().get(field).toString();
            // Session dbSession = Utils.getDBSession();
            // dbSession.beginTransaction();
            // String sql = "SELECT * FROM " + this.table + " WHERE " +
            // this.field
            // + "= '" + v + "'";
            // List list = null;
            // try {
            // list = dbSession.createSQLQuery(sql).list();
            // } catch (RuntimeException e) {
            // dbSession.getTransaction().rollback();
            // throw new ExecutionException("Failed to insert instance", e);
            // } finally {
            // dbSession.close();
            // if(list != null) {
            // if(list.size() > 0) {
            // Loggers.getDefault().writeLog(tid,
            // "The table has included this info!");
            // return;
            // }
            // }
            // }
        }
        if (this.globalContext.getRunnableTask().isTest()) {
            //
            // if test is set, just write the SQLs to log
            Loggers.getDefault().writeLog(tid, "TEST: insert an instance");
        } else {
            Session dbSession = Utils.getDBSession();
            dbSession.beginTransaction();
            try {
                SQLQuery q = dbSession.createSQLQuery(sb.toString());
                for (int i = 0; i < cols.length; i++) {
                    q.setParameter(cols[i], row.get(cols[i]));
                }
                q.executeUpdate();
                dbSession.getTransaction().commit();
            } catch (RuntimeException e) {
                dbSession.getTransaction().rollback();
                throw new ExecutionException("Failed to insert instance", e);
            } finally {
                dbSession.close();
            }
        }
        //
        // Change the task statistics.
        TaskStat taskStat = this.globalContext.getRunnableTask().getTaskStat();
        taskStat.success();
    }
}
