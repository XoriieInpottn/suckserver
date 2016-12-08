package org.lioxa.ustc.suckserver.routine.crawler.impl;

import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Order;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.openqa.selenium.WebElement;

/**
 *
 * @author xi
 * @since Nov 20, 2015
 */
@Order(3500)
public class Fail extends CrawlerRoutine {

	@Param(name = "delay")
	int delay;

	@Param(name = "retry")
	int retry;

	@Override
	public void exec() throws ExecutionException, ParameterException {
		if (this.globalContext.isStopReq()) {
			return;
		}
		WebElement dom = (WebElement) this.getMasterContext().get("dom");
		this.localContext.put("dom", dom);
		this.executeSubRoutines();
	}
}
