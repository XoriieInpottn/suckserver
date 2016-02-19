package org.lioxa.ustc.suckserver.routine.crawler.impl;

import java.util.Iterator;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;

/**
 *
 * @author xi
 * @since Nov 20, 2015
 */
public class Select extends CrawlerRoutine {

    @Param(name = "path", essential = true)
    String path;

    @Override
    public void exec() throws ParameterException, ExecutionException {
        if (this.globalContext.isStopReq()) {
            return;
        }
        Element dom = (Element) this.getMasterContext().get("dom");
        if (dom == null) {
            //
            // If there is no DOM in current context:
            // 1) the template have errors
            // 2) the routine mechanism may have bugs
            // in such situation, the crawler cannot continue to work any more.
            ExecutionException e = new ExecutionException("There is no DOM loaded in current context.");
            e.setFatal(true);
            throw e;
        }
        Elements elems = dom.select(this.path);
        Iterator<Element> iter = elems.iterator();
        while (iter.hasNext()) {
            Element elem = iter.next();
            this.localContext.put("dom", elem);
            this.executeSubRoutines();
        }
    }

}
