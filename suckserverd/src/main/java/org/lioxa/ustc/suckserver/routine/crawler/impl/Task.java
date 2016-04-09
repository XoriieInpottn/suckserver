package org.lioxa.ustc.suckserver.routine.crawler.impl;

//import org.lioxa.ustc.suckserver.routine.ExecutionException;
//import org.lioxa.ustc.suckserver.routine.Param;
//import org.lioxa.ustc.suckserver.routine.ParameterException;
//import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
//
///**
// *
// * @author xi
// * @since Nov 20, 2015
// */
//public class Task extends CrawlerRoutine {
//
//    @Param(name = "name", essential = true)
//    String name;
//
//    @Override
//    public void exec() throws ParameterException, ExecutionException {
//        this.executeSubRoutines();
//    }
//
//}


import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.Browser;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;

/**
 *
 * @author Kevin
 */
public class Task extends CrawlerRoutine {

    @Param(name = "name", essential = true)
    String name;
    
    @Param(name = "accept")
    String accept;
    
    @Override
    public void exec() throws ParameterException, ExecutionException {
//    	System.setProperty("webdriver.firefox.bin", "/home/kevin/Desktop/firefox/firefox");
    	System.setProperty("webdriver.firefox.bin", "/usr/bin/firefox");
    	Browser browserDriver = new Browser("./@suckin-0.0.1.xpi");
    	//***************************
    	//需要重写！！！！
    	if(this.accept != null) {
    		String a[] = {accept};
    		browserDriver.setAccept(a);
    	}
    	this.globalContext.setBrowserDriver(browserDriver);
        this.executeSubRoutines();
    }

}