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
public class LinkClick extends CrawlerRoutine {

    @Param(name = "path")
    String path;

    @Param(name = "closeBefore")
    boolean closeBefore = false;

    @Override
    public void exec() throws ExecutionException, ParameterException {
        if (this.globalContext.isStopReq()) {
            return;
        }
        WebElement element = (WebElement) this.getMasterContext().get("dom");
        if(this.path.length() != 0) {
        	if(element != null) {
//        		element = element.findElement(By.cssSelector(path));
        		try {
					element = this.globalContext.getBrowserDriver().findElement(element, path, 5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	} else {
        		element = this.globalContext.getBrowserDriver().findElement(By.cssSelector(path));
        	}
    	}
        long tid = this.globalContext.getRunnableTask().getId();
        if(element == null) {
        	Loggers.getDefault().writeLog(tid, "LinkClick cannot find the element");
        	return;
        }
        //element.click();
    	if(!this.globalContext.getBrowserDriver().click(element, 2)) {
    		Loggers.getDefault().writeLog(tid, "LinkClick cannot click the element");
    		return;
    	}
        if(this.closeBefore) {
        	this.globalContext.getBrowserDriver().windowForwardWithoutBefore();
        	this.goPage();
        } else {
        	this.globalContext.getBrowserDriver().windowForward();
        	this.goPage();
        	this.globalContext.getBrowserDriver().windowBack();
        }
        
    }
    
    public void goPage() throws ParameterException, ExecutionException {
    	WebElement newElement = this.globalContext.getBrowserDriver().findElement(By.tagName("body"));
    	this.localContext.put("dom", newElement);
    	this.executeSubRoutines();
    	//this.getMasterContext().put("dom", newElement);
    }

}
