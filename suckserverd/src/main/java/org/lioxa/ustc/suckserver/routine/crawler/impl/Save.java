package org.lioxa.ustc.suckserver.routine.crawler.impl;

import java.util.HashMap;
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
        //
        // do insert
        if (this.globalContext.getRunnableTask().isTest()) {
            //
            // if test is set, just write the SQLs to log
            long tid = this.globalContext.getRunnableTask().getId();
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