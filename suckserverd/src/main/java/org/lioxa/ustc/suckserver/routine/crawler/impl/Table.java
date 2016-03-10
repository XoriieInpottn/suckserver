package org.lioxa.ustc.suckserver.routine.crawler.impl;

import java.util.Map.Entry;

import org.hibernate.Session;
import org.lioxa.ustc.suckserver.log.Loggers;
import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.Routine;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerContext;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.lioxa.ustc.suckserver.utils.TableInfo;
import org.lioxa.ustc.suckserver.utils.Utils;

/**
 *
 * @author xi
 * @since Nov 19, 2015
 */
public class Table extends CrawlerRoutine {

    @Param(name = "name", essential = true)
    String name;

    @Param(name = "overlap")
    boolean overlap = false;

    //
    // execution

    @Override
    public void exec() throws ParameterException, ExecutionException {
        if (this.globalContext.isStopReq()) {
            return;
        }
        //
        // check parameters
        if (this.name == null || (this.name = this.name.trim()).length() == 0) {
            throw new ParameterException("Parameter name should not be empty.");
        }
        if (!this.name.matches("^[a-zA-Z]\\w*$")) {
            String msg = String.format("Invalid parameter \"%s\" for name.", this.name);
            throw new ParameterException(msg);
        }
        //
        // make table info
        TableInfo tblInfo = new TableInfo();
        tblInfo.setName(this.name);
        this.localContext.put("tblInfo", tblInfo);
        for (Routine<CrawlerContext> cmd : this.subRoutines) {
            cmd.execute();
        }
        //
        // We must check stopReq, since if we don't stop now, or we will get a
        // polluted TableInfo object
        if (this.globalContext.isStopReq()) {
            return;
        }
        //
        // put table info to context
        this.globalContext.getTables().put(this.name, tblInfo);
        //
        // do create table
        String dropSQL = String.format("DROP TABLE IF EXISTS %s;", this.name);
        String createSQL = makeSQL(tblInfo);
        if (this.globalContext.getRunnableTask().isTest()) {
            //
            // if test is set, just write the SQLs to log
            String log = String.format("TEST: \"%s\"", createSQL);
            long tid = this.globalContext.getRunnableTask().getId();
            Loggers.getDefault().writeLog(tid, log);
        } else {
            //
            // execute SQL
            Session dbSession = Utils.getDBSession();
            dbSession.beginTransaction();
            try {
                if (this.overlap) {
                    dbSession.createSQLQuery(dropSQL).executeUpdate();
                }
                dbSession.createSQLQuery(createSQL).executeUpdate();
                dbSession.getTransaction().commit();
            } catch (RuntimeException e) {
                dbSession.getTransaction().rollback();
                //
                // If it failed to create table, there is no need to continue,
                // since the obtained data cannot be stored.
                String msg = String.format("Failed to create table \"%s\".", this.name);
                ExecutionException e1 = new ExecutionException(msg, e);
                e1.setFatal(true);
                throw e1;
            } finally {
                dbSession.close();
            }
        }
    }

    static String makeSQL(TableInfo tblInfo) {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sb.append(tblInfo.getName());
        sb.append(" (id SERIAL PRIMARY KEY, ");
        sb.append("_timeStamp bigint,");
        for (Entry<String, String> entry : tblInfo.entrySet()) {
            String colName = entry.getKey();
            String colType = entry.getValue();
            sb.append(colName);
            sb.append(' ');
            sb.append(colType);
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(");");
        return sb.toString();
    }

}
