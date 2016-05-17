package org.lioxa.ustc.suckserver.routine.crawler.impl;

import org.lioxa.ustc.suckserver.log.Loggers;
import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.openqa.selenium.WebElement;

/**
 * this command should be used to open a new Link
 *
 * @author kevin
 *
 */
public class Click extends CrawlerRoutine {

    @Param(name = "path")
    String path;

    @Param(name = "count")
    int count = 1;

    @Param(name = "retry")
    int retry = 2;

    @Param(name = "delay")
    int delay = 2;

    @Param(name = "closeBefore")
    Boolean closeBefore = false;

    @Override
    public void exec() throws ExecutionException, ParameterException {
        if (this.globalContext.isStopReq()) {
            return;
        }
        WebElement element = (WebElement) this.getMasterContext().get("dom");
        long tid = this.globalContext.getRunnableTask().getId();
        if (this.path != null) {
            if (element != null) {
                element = this.globalContext.getBrowserDriver().select(element, this.path, this.retry, this.delay)
                        .get(0);
                if (element == null) {
                    Loggers.getDefault().writeLog(tid, "The path of Click is invalid!");
                    return;
                }
            } else {
                element = this.globalContext.getBrowserDriver().select(this.path, this.retry, this.delay).get(0);
            }
        }
        if (!this.globalContext.getBrowserDriver().click(element, 8)) {
            Loggers.getDefault().writeLog(tid, "Click cannot click the element");
            return;
        }
        if (this.delay > 0) {
            try {
                Thread.sleep(this.delay * 1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        //
        // when closeBefore is null, the command is just a behavior, it cannot
        // execute SubRoutines
        if (this.closeBefore != null) {
            // when closeBefore is not null, it will load a new page and it will
            // execute SubRoutines
            if (this.closeBefore) {
                this.globalContext.getBrowserDriver().windowForwardWithoutBefore();
                this.goPage();
            } else {
                this.globalContext.getBrowserDriver().windowForward();
                this.goPage();
                this.globalContext.getBrowserDriver().windowBack();
            }
            return;
        }

        for (int i = 1; i < this.count; i++) {
            if (this.globalContext.isStopReq()) {
                return;
            }
            if (!this.globalContext.getBrowserDriver().click(element, 8)) {
                Loggers.getDefault().writeLog(tid, "The command of click cannot make effect!");
                return;
            }
            try {
                Thread.sleep(this.delay * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        WebElement newElement = this.globalContext.getBrowserDriver().select("body", this.retry, this.delay).get(0);
        this.getMasterContext().put("dom", newElement);
    }

    public void goPage() throws ParameterException, ExecutionException {
        WebElement newElement = this.globalContext.getBrowserDriver().select("body", this.retry, this.delay).get(0);
        this.localContext.put("dom", newElement);
        this.executeSubRoutines();
    }

}
