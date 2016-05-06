package org.lioxa.ustc.suckserver.routine.crawler.impl;

import java.io.IOException;

import org.lioxa.ustc.suckserver.log.Loggers;
import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * this command can send a value for a webelement
 * @author kevin
 *
 */
public class Type extends CrawlerRoutine {

    @Param(name = "path")
    String path = "";

    @Param(name = "value")
    String value;
    
    //the path of identifying code
    @Param(name = "ICpath")
    String ICpath = "";

    @Override
    public void exec() throws ExecutionException, ParameterException {
        if (this.globalContext.isStopReq()) {
            return;
        }
//        this.globalContext.setIcBase64("hello world");
//        System.out.println("执行Type指令的结果是" + this.globalContext.getIcBase64());
//         while(this.globalContext.getIcvalue().length() == 0) {
//        	   if (this.globalContext.isStopReq()) {
//                   return;
//               }
//        	 try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//         }
//    	 System.out.println("已经获取到验证，验证是"+this.globalContext.getIcvalue());
    	 
    	 
    	 
    	 
    	 
        WebElement element = (WebElement) this.getMasterContext().get("dom");
        if(this.ICpath.length() > 0) {
        	long tid = this.globalContext.getRunnableTask().getId();
        	WebElement e1;
        	if(element != null) {
        		e1 = element.findElement(By.cssSelector(ICpath)); 
        	} else {
        		e1 = this.globalContext.getBrowserDriver().findElement(By.cssSelector(ICpath));
        	}
        	String base64 = "";
        	try {
				base64 = this.globalContext.getBrowserDriver().createElementImage(e1);
			} catch (IOException e) {
				Loggers.getDefault().writeError(tid, "Type is wrong. Cannot get the identifing code of the page!");
			}
        	this.globalContext.setIcBase64(base64);
        	while(this.globalContext.getIcvalue().length() == 0) {
        		if(this.globalContext.isStopReq()) {
        			return;
        		}
        		try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	value = this.globalContext.getIcvalue();
        	this.globalContext.setIcvalue("");
        }
        if(this.path.length() > 0) {
        	if(element != null) {
        		element = element.findElement(By.cssSelector(path));
        	} else {
        		element = this.globalContext.getBrowserDriver().findElement(By.cssSelector(path));
        	}
    	}
        element.sendKeys(value);
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
