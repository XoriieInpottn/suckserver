package org.lioxa.ustc.suckserver.routine.crawler.impl;

import org.lioxa.ustc.suckserver.log.Loggers;
import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Order;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;

/**
 * 
 * @author kevin
 *
 */
@Order(4500)
public class While extends CrawlerRoutine {

	@Param(name = "var", essential = true)
	String var;

	@Param(name = "start")
	int start = 0;

	@Param(name = "end")
	int end = Integer.MAX_VALUE;

	@Param(name = "step")
	int step = 1;

	@Override
	public void exec() throws ParameterException, ExecutionException {
		for (int i = this.start; i < this.end; i = i + this.step) {
			if (this.globalContext.isStopReq()) {
				return;
			}
			this.globalContext.getVars().put(var, i);
			try {
				this.executeSubRoutines();
			} catch (Exception e) {
				long tid = this.globalContext.getRunnableTask().getId();
				Loggers.getDefault().writeError(tid, e.getMessage());
			}
		}
	}
}