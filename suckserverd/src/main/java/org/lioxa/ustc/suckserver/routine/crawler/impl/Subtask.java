package org.lioxa.ustc.suckserver.routine.crawler.impl;

import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;

/**
 * 
 * @author kevin
 *
 */
public class Subtask extends CrawlerRoutine implements Runnable {

    @Param(name = "name", essential = true)
    String name;

    @Override
    public void exec() throws ParameterException, ExecutionException {
        new Thread(this).start();
    }
    
    public void run() {
      try {
          this.executeSubRoutines();
      } catch (ParameterException | ExecutionException e) {
		// TODO Auto-generated catch block
    	  e.printStackTrace();
      }
    }

}
