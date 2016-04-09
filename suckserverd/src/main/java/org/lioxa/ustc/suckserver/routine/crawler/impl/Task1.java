package org.lioxa.ustc.suckserver.routine.crawler.impl;

import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.Browser;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;

/**
 *
 * @author xi
 * @since Nov 20, 2015
 */
public class Task1 extends CrawlerRoutine {

    @Param(name = "name", essential = true)
    String name;
    
    @Param(name = "path", essential = true)
    String path;
    
    @Override
    public void exec() throws ParameterException, ExecutionException {
    	System.setProperty("webdriver.firefox.bin", path);
    	Browser browserDriver = new Browser("@suckin-0.0.1.xpi");
    	this.globalContext.getVars().put("BrowserDriver", browserDriver);
        this.executeSubRoutines();
    }

}
