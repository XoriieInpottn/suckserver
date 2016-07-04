package org.lioxa.ustc.suckserver.routine.crawler.impl;

import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Order;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;

/**
 *
 * @author xi
 * @since Nov 30, 2015
 */
@Order(6500)
public class Var extends CrawlerRoutine {

    @Param(name = "name", essential = true)
    String name;

    @Param(name = "value", essential = true)
    String value;

    @Override
    protected void exec() throws ParameterException, ExecutionException {
        if (this.globalContext.isStopReq()) {
            return;
        }
        // check parameters
        if (this.name == null || (this.name = this.name.trim()).length() == 0) {
            throw new ParameterException("Parameter \"name\" should not be empty.");
        }
        if (!this.name.matches("[a-zA-Z]\\w*")) {
            throw new ParameterException("Invalid parameter \"name\".");
        }
        // put into global context
        this.globalContext.getVars().put(this.name, this.value);
    }

}
