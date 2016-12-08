package org.lioxa.ustc.suckserver.routine.crawler.impl;

import org.lioxa.ustc.suckserver.log.Loggers;
import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Order;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.openqa.selenium.WebElement;

/**
 *
 * @author kevin
 *
 */
@Order(4400)
public class Sleep extends CrawlerRoutine {

	@Param(name = "from")
	int from = 0;

	@Param(name = "to")
	int to = 0;

	@Override
	public void exec() throws ExecutionException, ParameterException {
		if (this.globalContext.isStopReq()) {
			return;
		}
		long tid = this.globalContext.getRunnableTask().getId();
		if (this.to == 0) {
			try {
				Thread.sleep(this.from * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if (this.from > this.to) {
				Loggers.getDefault().writeLog(tid, "Sleep is wrong!");
				return;
			}
			int time = (int) (this.from + Math.random()
					* (this.to - this.from + 1)) * 1000;
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
