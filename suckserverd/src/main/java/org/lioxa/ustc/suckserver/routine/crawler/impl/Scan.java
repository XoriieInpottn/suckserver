package org.lioxa.ustc.suckserver.routine.crawler.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Order;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.Routine;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerContext;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.lioxa.ustc.suckserver.utils.Utils;

/**
 * 
 * @author kevin
 *
 */
@Order(4000)
public class Scan extends CrawlerRoutine {

	@Param(name = "name", essential = true)
	String name;

	@Param(name = "field", essential = true)
	String field;
	
	@Param(name = "var", essential = true)
	String var;
	
	@Param(name = "count")
	long count = Long.MAX_VALUE;

	long after = 0;

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
			String msg = String.format("Invalid parameter \"%s\" for name.",
					this.name);
			throw new ParameterException(msg);
		}
		if (this.var == null || (this.var = this.var.trim()).length() == 0) {
			throw new ParameterException("Parameter var should not be empty.");
		}
		if (!this.var.matches("[a-zA-Z]\\w*")) {
			throw new ParameterException("Invalid parameter \"var\".");
		}
		long a = this.after;
		List<String> list =  scan(a);
		long num = 0;
		while (list.size() > 0 && num < count) {
			for (int i = 0; i < list.size() && num < count; i++) {
				String content = (String) list.get(i);
				this.globalContext.getVars().remove(this.var);
				this.globalContext.getVars().put(this.var, content);
				for (Routine<CrawlerContext> cmd : this.subRoutines) {
					cmd.execute();
				}
				num ++;
			}
			a = this.after;
			list = scan(after);
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized List<String> scan(long after) throws ExecutionException {
		List<Object> result;
		List<String> r = new ArrayList<>();
		String sql = "SELECT " + this.field + " FROM " + this.name + " ORDER BY _timestamp ASC";
		String sql2 =  "SELECT MAX(_timestamp) FROM " + this.name;
		if (after > 0) {
			sql = "SELECT " + this.field + " FROM " + this.name + " where _timestamp > " + after + " ORDER BY _timestamp ASC";
		}
		Session dbSession = Utils.getDBSession();
		dbSession.beginTransaction();
		try {
			result = dbSession.createSQLQuery(sql).list();
			Object obj =  dbSession.createSQLQuery(sql2).list().get(0);
			//
			//attention that we give the value to this.after 
			this.after = Long.parseLong(obj.toString());
			dbSession.getTransaction().commit();
		} catch (RuntimeException e) {
			dbSession.getTransaction().rollback();
			//
			// If it failed to create table, there is no need to continue,
			// since the obtained data cannot be stored.
			String msg = String.format("Failed to scan table \"%s\".",
					this.name);
			ExecutionException e1 = new ExecutionException(msg, e);
			e1.setFatal(true);
			throw e1;
		} finally {
			dbSession.close();
		}
		for(int i = 0; i < result.size(); i++) {
			r.add(result.get(i).toString());
		}
		return r;
	}
}
