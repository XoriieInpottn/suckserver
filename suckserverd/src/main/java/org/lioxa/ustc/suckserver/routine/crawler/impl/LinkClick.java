package org.lioxa.ustc.suckserver.routine.crawler.impl;

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
        		element = element.findElement(By.cssSelector(path));
        	} else {
        		element = this.globalContext.getBrowserDriver().findElement(By.cssSelector(path));
        	}
    	}
        element.click();
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
