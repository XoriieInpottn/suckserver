package org.lioxa.ustc.suckserver.routine.crawler.impl;

import org.lioxa.ustc.suckserver.log.Loggers;
import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * this command should be used to open a new Link
 * @author kevin
 *
 */
public class Click extends CrawlerRoutine {

    @Param(name = "path")
    String path;
    
    @Param(name = "count")
    int count = 1;
    
    @Param(name = "time")
    int time = 0;

    @Override
    public void exec() throws ExecutionException, ParameterException {
        if (this.globalContext.isStopReq()) {
            return;
        }
        WebElement element = (WebElement) this.getMasterContext().get("dom");
        long tid = this.globalContext.getRunnableTask().getId();
        if(this.path.length() != 0) {
        	if(element != null) {
        		try {
					element = this.globalContext.getBrowserDriver().findElement(element, path, time);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		if(element == null) {
        			Loggers.getDefault().writeLog(tid, "The path of Click is invalid!");
        			return;
        		}
//        		element = element.findElement(By.cssSelector(path));
        	} else {
        		element = this.globalContext.getBrowserDriver().findElement(By.cssSelector(path));
        	}
    	}
        for(int i = 0; i < this.count; i++) {
//        	element.click();
        	if(!this.globalContext.getBrowserDriver().click(element, time)) {
        		Loggers.getDefault().writeLog(tid, "The command of click cannot make effect!");
        		return;
        	}
        	try {
				Thread.sleep(time*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        WebElement newElement = this.globalContext.getBrowserDriver().findElement(By.tagName("body"));
        this.getMasterContext().put("dom", newElement);
    }

}
