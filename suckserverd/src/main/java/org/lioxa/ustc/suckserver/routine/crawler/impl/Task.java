package org.lioxa.ustc.suckserver.routine.crawler.impl;

import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;

/**
 *
 * @author xi
 * @since Nov 20, 2015
 */
public class Task extends CrawlerRoutine {

    @Param(name = "name", essential = true)
    String name;

    @Override
    public void exec() throws ParameterException, ExecutionException {
        this.executeSubRoutines();
    }

}
