package org.lioxa.ustc.suckserver.routine.crawler.impl;

//import org.lioxa.ustc.suckserver.Main;
import org.lioxa.ustc.suckserver.log.Loggers;
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
public class Load1 extends CrawlerRoutine {
	
	  @Param(name = "url", essential = true)
      String url;

	  @Param(name = "maxPage")
	  long maxPage = 1;
	  
	  @Param(name = "nextPath")
	  String nextPath;
      
	  @Override
	  public void exec() throws ParameterException, ExecutionException {
		  if (this.globalContext.isStopReq()) {
			  return;
		  }
		  try {
			  this.globalContext.getBrowserDriver().get(url);
		  } catch(Exception e) {
			  e.printStackTrace();
			  return;
		  }
		  this.goPage();
		  for(int i = 1; i < this.maxPage; i++) {
			  if(nextPath == null) {
				  long tid = this.globalContext.getRunnableTask().getId();
				  Loggers.getDefault().writeError(tid, "nextPath of Load cannot be null!");
				  return;
			  }
			  WebElement element = this.globalContext.getBrowserDriver().findElement(By.cssSelector(nextPath));
			  element.click();
			  this.globalContext.getBrowserDriver().windowForwardWithoutBefore();
			  this.goPage();
		  }
		  this.globalContext.getBrowserDriver().quit();
	  }
	  
	  public void goPage() throws ParameterException, ExecutionException {
		  WebElement elements = this.globalContext.getBrowserDriver().findElement(By.tagName("body"));
		  this.localContext.put("dom", elements);
		  this.executeSubRoutines();
	  }
	  
}