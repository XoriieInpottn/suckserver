package org.lioxa.ustc.suckserver.routine.crawler.impl;

import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Order;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.Routine;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerContext;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.openqa.selenium.WebElement;

/**
 *
 * @author kevin
 * 
 */
@Order(3500)
public class Test extends CrawlerRoutine {

	@Param(name = "delay")
	int delay = 0;

	@Param(name = "retry")
	int retry = 1;

	@Override
	public void exec() throws ParameterException, ExecutionException {
		if (this.globalContext.isStopReq()) {
			return;
		}
		WebElement dom = (WebElement) this.getMasterContext().get("dom");
		this.localContext.put("dom", dom);
		Routine<CrawlerContext> successRoutine = null;
		Routine<CrawlerContext> failRoutine = null;
		for (int i = 0; i < this.subRoutines.size(); i++) {
			Routine<CrawlerContext> rt = this.subRoutines.get(i);
			String name = rt.getClass().getSimpleName();
			if (name.equals("Success")) {
				successRoutine = rt;
			}
			if (name.equals("Fail")) {
				failRoutine = rt;
			}
		}
		this.localContext.put("success", 1);//this is a flag. If the command of success is error, this value is 0
		successRoutine.execute();
		for (int i = 0; i < this.retry; i++) {
			if((int)this.localContext.get("success") == 0) {
				System.out.println("******");
				System.out.println("现在进入错误处理阶段");
				failRoutine.execute();
				try {
					Thread.sleep(this.delay * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("错误处理结束");
				successRoutine.execute();
			} else {
				System.out.println("跳出Test");
				return;
			}
		}
	}
}


