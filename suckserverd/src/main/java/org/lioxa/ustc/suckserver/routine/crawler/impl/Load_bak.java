package org.lioxa.ustc.suckserver.routine.crawler.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.lioxa.ustc.suckserver.Main;
import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;

/**
 *
 * @author xi
 * @since Nov 20, 2015
 */
public class Load_bak extends CrawlerRoutine {

    @Param(name = "url", essential = true)
    String url;

    @Param(name = "max-page")
    long maxPage = Long.MAX_VALUE;

    @Param(name = "page")
    String page;

    @Param(name = "from")
    int from;

    @Param(name = "to")
    int to;

    @Param(name = "step")
    int step = 1;

    @Override
    public void execute() throws ParameterException, ExecutionException {
        this.next = true;
        for (int i = 0; i < this.maxPage && this.next; i++) {
            super.execute();
        }
    }

    //
    // The "load" routine can load next page automatically, but, when the URL is
    // invalid or has the "stopReq", the loop must be stop.
    // "next" is used to indicate whether it should be continue.
    boolean next;

    @Override
    public void exec() throws ParameterException, ExecutionException {
        if (this.globalContext.isStopReq()) {
            //
            // "next" must be set as false in order to break the loop.
            this.next = false;
            return;
        }
        /*
         * TODO: lack of parameter check!!!!
         */
        if (this.page == null) {
            this.goPage();
        } else {
            /*
             * TODO: there may be some problem with this situation
             */
            this.goPages();
        }
    }

    void goPage() throws ParameterException, ExecutionException {
        Document doc = this.request(this.url);
        if (doc == null) {
            //
            // The URL is the same as the last one, so it will be considered to
            // be "no next page" and the loop must be stop.
            this.next = false;
            return;
        }
        this.localContext.put("dom", doc);
        this.executeSubRoutines();
    }

    String lastURL;

    Document request(String url) throws ExecutionException {
        if (url.equals(this.lastURL)) {
            this.lastURL = null;
            return null;
        }
        this.lastURL = url;
        Document doc = null;
        Exception e = null;
        int retryCount = Main.conf.getInt("retry-count");
        int retryDelay = Main.conf.getInt("retry-delay");
        for (int i = 0; i < retryCount; i++) {
            try {
                e = null;
                doc = Jsoup.connect(url).get();
            } catch (Exception e1) {
                e = e1;
                // System.err.println("Failed to connect, retry later.");
                try {
                    Thread.sleep(retryDelay);
                } catch (InterruptedException e2) {
                }
                continue;
            }
            break;
        }
        if (e != null) {
            String err = String.format("Failed to load \"%s\"", this.url);
            throw new ExecutionException(err, e);
        }
        return doc;
    }

    void goPages() throws ParameterException, ExecutionException {
        for (int i = this.from; i <= this.to; i += this.step) {
            String url1;
            if (this.url.indexOf('?') == -1) {
                url1 = String.format("%s?%s=%d", this.url, this.page, i);
            } else {
                url1 = String.format("%s&%s=%d", this.url, this.page, i);
            }
            Document doc = this.request(url1);
            if (doc == null) {
                break;
            }
            this.localContext.put("dom", doc);
            this.executeSubRoutines();
        }
        this.next = false;
    }    
}