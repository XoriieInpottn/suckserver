package org.lioxa.ustc.suckserver.routine.crawler.impl;

import java.util.Iterator;
import java.util.List;

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
@Order(8000)
public class Select extends CrawlerRoutine {

	@Param(name = "path", essential = true)
	String path;

	@Override
	public void exec() throws ParameterException, ExecutionException {
		if (this.globalContext.isStopReq()) {
			return;
		}
		WebElement dom = (WebElement) this.getMasterContext().get("dom");

		if (dom == null) {
			//
			// If there is no DOM in current context:
			// 1) the template have errors
			// 2) the routine mechanism may have bugs
			// in such situation, the crawler cannot continue to work any more.
			ExecutionException e = new ExecutionException(
					"There is no DOM loaded in current context.");
			e.setFatal(true);
			throw e;
		}
		if (this.path.length() == 0) {
			throw new ParameterException("Parameter path should not be empty.");
		}

		// List<WebElement> elems = dom.findElements(By.cssSelector(path));
		List<WebElement> elems = this.globalContext.getBrowserDriver().select(
				dom, path, 3, 2);
		if (elems.size() == 0) {
			throw new ExecutionException("Content of select is null.");
		}
		this.localContext.put("repeatNum", 0);
		int MAX_REPEAT = 5;
		Iterator<WebElement> iter = elems.iterator();
		while (iter.hasNext()) {
			if (this.globalContext.isStopReq()) {
				return;
			}
			if ((int) this.localContext.get("repeatNum") > MAX_REPEAT) {
				long tid = this.getGlobalContext().getRunnableTask().getId();
				Loggers.getDefault().writeLog(tid,
						"The table has included these info!");
				return;
			}
			WebElement elem = iter.next();
			this.localContext.put("dom", elem);
			this.executeSubRoutines();
		}
	}

}
