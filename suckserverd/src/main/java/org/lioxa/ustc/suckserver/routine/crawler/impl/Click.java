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
    int time = 1;
    
    @Param(name = "closeBefore")
    String closeBefore;

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
					element = this.globalContext.getBrowserDriver().findElement(element, path, 5);
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
        //element.click();
    	if(!this.globalContext.getBrowserDriver().click(element, 2)) {
    		Loggers.getDefault().writeLog(tid, "Click cannot click the element");
    		return;
    	}
    	if(time > 0) {
	    	try {
				Thread.sleep(time*1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
    	//
    	//when closeBefore is null, the command is just a behavior, it cannot execute SubRoutines
    	//when closeBefore is not null, it will load a new page and it will execute SubRoutines
        if(this.closeBefore != null) {
        	if(this.closeBefore.equals("true")) {
        		this.globalContext.getBrowserDriver().windowForwardWithoutBefore();
            	this.goPage();
        	} else {
        		this.globalContext.getBrowserDriver().windowForward();
            	this.goPage();
            	this.globalContext.getBrowserDriver().windowBack();
        	}
        	return;
        }
        
        for(int i = 1; i < this.count; i++) {
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
    
    public void goPage() throws ParameterException, ExecutionException {
    	WebElement newElement = this.globalContext.getBrowserDriver().findElement(By.tagName("body"));
    	this.localContext.put("dom", newElement);
    	this.executeSubRoutines();
    	//this.getMasterContext().put("dom", newElement);
    }

}
