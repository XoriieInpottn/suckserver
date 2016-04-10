package org.lioxa.ustc.suckserver.routine.crawler.impl;

import org.jsoup.nodes.Element;
import org.lioxa.ustc.suckserver.log.Loggers;
import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;

/**
 *
 * @author xi
 * @since Nov 20, 2015
 */
public class Print_bak extends CrawlerRoutine {

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
        Element dom = (Element) this.getMasterContext().get("dom");
        if (dom == null) {
            return;
        }
        Loggers.getDefault().writeLog(tid, this.html ? dom.html() : dom.text());
    }

}
