package org.lioxa.ustc.suckserver.routine.crawler.impl;


import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author kevin
 *
 */
public class Drop extends CrawlerRoutine {

    @Param(name = "count")
    int count = 1;
    
    @Param(name = "time")
    long time = 1;
    
    @Override
    public void exec() throws ParameterException, ExecutionException {
        if (this.globalContext.isStopReq()) {
            return;
        }
        for(int i = 0; i < this.count; i++) {
        	this.globalContext.getBrowserDriver().scrollDown(time);
        }
        WebElement dom =  this.globalContext.getBrowserDriver().findElement(By.tagName("body"));
        this.getMasterContext().put("dom",dom);
    }

}
