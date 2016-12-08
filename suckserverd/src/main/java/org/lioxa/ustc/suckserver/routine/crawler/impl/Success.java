package org.lioxa.ustc.suckserver.routine.crawler.impl;

import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Order;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author kevin
 *
 */
@Order(3500)
public class Success extends CrawlerRoutine {

	@Override
	public void exec() throws ExecutionException, ParameterException {
		if (this.globalContext.isStopReq()) {
			return;
		}
		WebElement dom = (WebElement) this.getMasterContext().get("dom");
		this.localContext.put("dom", dom);
		System.out.println("现在正在执行成功的这一部分");
		for (int i = 0; i < this.subRoutines.size(); i++) {
			try {
				this.subRoutines.get(i).execute();
			} catch (Exception e) {
				this.masterRoutine.getLocalContext().put("success", 0);
				System.out.println(e.getMessage());
				System.out.println("success部分出错");
				return;
			}
		}
		this.masterRoutine.getLocalContext().put("success", 1);
		return;
	}
}
