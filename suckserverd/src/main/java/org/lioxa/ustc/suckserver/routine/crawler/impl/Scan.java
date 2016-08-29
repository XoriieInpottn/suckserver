package org.lioxa.ustc.suckserver.routine.crawler.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Order;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.Routine;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerContext;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;

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
	
	Connection conn = null;
	Statement state = null;

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
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/suckserver", "postgres",
					"postgres");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			state = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		long a = this.after;
		List<Map.Entry<Long, String>> list =  scan(a);
		long num = 0;
		while (list.size() > 0 && num < count) {
			for (int i = 0; i < list.size() && num < count; i++) {
				Long id = list.get(i).getKey();
				String content = list.get(i).getValue();
				this.globalContext.getVars().remove(this.var);
				this.globalContext.getVars().put(this.var, content);
				for (Routine<CrawlerContext> cmd : this.subRoutines) {
					cmd.execute();
				}
				String sql = "update " + this.name + " set _isvisited = 1 where id = '" + id + "';";
				try {
					if(!this.globalContext.getRunnableTask().isTest()) {
						state.executeUpdate(sql);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				num ++;
			}
			a = this.after;
			list = scan(after);
		}
		try {
			state.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public synchronized List<Map.Entry<Long, String>> scan(long after) throws ExecutionException {
		ResultSet result = null;
		List<Map.Entry<Long, String>> r = new ArrayList<>();
		Map<Long, String> map = new LinkedHashMap<>();
		String sql = "SELECT id, " + this.field + " FROM " + this.name + " where _isvisited = 0 ORDER BY _timestamp ASC";
		String sql2 =  "SELECT MAX(_timestamp) FROM " + this.name;
		if (after > 0) {
			sql = "SELECT id, " + this.field + " FROM " + this.name + " where _timestamp > " + after + " and _isvisited = 0 ORDER BY _timestamp ASC";
		}
		try {
			result =  state.executeQuery(sql);
			while(result.next()) {
				long id = result.getLong(1);
				String content = result.getString(2);
				map.put(id, content);
			}
			result = state.executeQuery(sql2);
			if(result.next()) {
				this.after = result.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(Map.Entry<Long, String> e : map.entrySet()) {
			r.add(e);
		}
		return r;
	}
}
