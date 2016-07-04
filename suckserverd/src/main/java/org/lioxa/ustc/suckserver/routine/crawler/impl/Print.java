package org.lioxa.ustc.suckserver.routine.crawler.impl;

import org.lioxa.ustc.suckserver.log.Loggers;
import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Order;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.openqa.selenium.WebElement;

/**
 *
 * @author xi
 * @since Nov 20, 2015
 */
@Order(3500)
public class Print extends CrawlerRoutine {

    @Param(name = "content")
    String content;

    @Param(name = "html")
    boolean html = false;

    @Override
    public void exec() throws ExecutionException {
        if (this.globalContext.isStopReq()) {
            return;
        }
        long tid = this.globalContext.getRunnableTask().getId();
        if (this.content != null) {
            Loggers.getDefault().writeLog(tid, this.content);
            return;
        }
        WebElement dom = (WebElement) this.getMasterContext().get("dom");
        
        if (dom == null) {
            return;
        }
        Loggers.getDefault().writeLog(tid, dom.getText());
    }

}
