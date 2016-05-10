package org.lioxa.ustc.suckserver.routine.crawler.impl;

import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * this command can send a value for a webelement
 * 
 * @author kevin
 *
 */
public class Type extends CrawlerRoutine {

	@Param(name = "path")
	String path = "";

	@Param(name = "value")
	String value = "";

	@Param(name = "delay")
	long delay = 1;

	@Override
	public void exec() throws ExecutionException, ParameterException {
		if (this.globalContext.isStopReq()) {
			return;
		}
		WebElement element = (WebElement) this.getMasterContext().get("dom");
		if (this.path.length() > 0) {
			if (element != null) {
				element = element.findElement(By.cssSelector(path));
			} else {
				element = this.globalContext.getBrowserDriver().select(path).get(0);
			}
		}
		element.sendKeys(value);
		try {
			Thread.sleep(delay * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
