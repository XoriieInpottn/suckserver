package org.lioxa.ustc.suckserver.routine.crawler.impl;


import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author kevin
 *
 */
public class Drop extends CrawlerRoutine {

    @Param(name = "count")
    int count = 1;
    
    @Param(name = "delay")
    long delay = 1;
    
    @Override
    public void exec() throws ParameterException, ExecutionException {
        if (this.globalContext.isStopReq()) {
            return;
        }
        for(int i = 0; i < this.count; i++) {
        	this.globalContext.getBrowserDriver().scrollDown(delay);
        }
        WebElement dom =  this.globalContext.getBrowserDriver().select("body").get(0);
        this.getMasterContext().put("dom",dom);
    }

}
