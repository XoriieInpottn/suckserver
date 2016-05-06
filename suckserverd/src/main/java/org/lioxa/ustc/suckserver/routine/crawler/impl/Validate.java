package org.lioxa.ustc.suckserver.routine.crawler.impl;

import java.io.IOException;

import org.lioxa.ustc.suckserver.log.Loggers;
import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * this command should be used to open a new Link
 * 
 * @author kevin
 *
 */
public class Validate extends CrawlerRoutine {

	@Param(name = "path", essential = true)
	String path;

	@Param(name = "delay")
	long delay = 0;

	@Param(name = "value")
	String value;
	
	@Override
	public void exec() throws ExecutionException, ParameterException {
		if (this.globalContext.isStopReq()) {
			return;
		}
		long tid = this.globalContext.getRunnableTask().getId();
		if (this.path.length() == 0) {
			Loggers.getDefault().writeError(tid,
					"The path of validate cannot be null.");
			return;
		}
		WebElement element = (WebElement) this.getMasterContext().get("dom");
		if (this.path.length() > 0) {
			WebElement e1;
			if (element != null) {
				e1 = element.findElement(By.cssSelector(path));
			} else {
				e1 = this.globalContext.getBrowserDriver().findElement(
						By.cssSelector(path));
			}
			String base64 = "";
			try {
				base64 = this.globalContext.getBrowserDriver()
						.createElementImage(e1);
			} catch (IOException e) {
				Loggers.getDefault()
						.writeError(tid,
								"Type is wrong. Cannot get the identifing code of the page!");
			}
			this.globalContext.setIcBase64(base64);
			while (this.globalContext.getIcvalue().length() == 0) {
				if (this.globalContext.isStopReq()) {
					return;
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			value = this.globalContext.getIcvalue();
			this.globalContext.setIcvalue("");
		}
	}
}
