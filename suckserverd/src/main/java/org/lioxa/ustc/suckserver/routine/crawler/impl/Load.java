package org.lioxa.ustc.suckserver.routine.crawler.impl;

import org.lioxa.ustc.suckserver.log.Loggers;
import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author kevin
 *
 */
public class Load extends CrawlerRoutine {

	@Param(name = "url")
	String url;

	@Param(name = "maxPage")
	long maxPage = 1;

	@Param(name = "nextPath")
	String nextPath;

	@Override
	public void exec() throws ParameterException, ExecutionException {
		if (this.globalContext.isStopReq()) {
			return;
		}
		if (this.url != null) {
			try {
				this.globalContext.getBrowserDriver().get(url);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		this.goPage();
		for (int i = 1; i < this.maxPage; i++) {
			if (this.globalContext.isStopReq()) {
				return;
			}
			long tid = this.globalContext.getRunnableTask().getId();
			if (nextPath == null) {
				Loggers.getDefault().writeError(tid,
						"nextPath of Load cannot be null!");
				return;
			}
			WebElement element = this.globalContext.getBrowserDriver()
					.select(nextPath, 3, 2).get(0);
			if (element == null) {
				Loggers.getDefault().writeLog(tid,
						"Load cannot go to the next Page!");
				return;
			}
			if (!this.globalContext.getBrowserDriver().click(element, 2)) {
				Loggers.getDefault().writeLog(tid,
						"Load cannot click the element!");
				return;
			}
			this.globalContext.getBrowserDriver().windowForwardWithoutBefore();
			this.goPage();
		}
	}

	public void goPage() throws ParameterException, ExecutionException {
		WebElement elements = this.globalContext.getBrowserDriver()
				.select("body", 3, 2).get(0);
		this.localContext.put("dom", elements);
		this.executeSubRoutines();
	}

}