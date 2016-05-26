package org.lioxa.ustc.suckserver.routine.crawler.impl.bak;


import java.util.Iterator;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;

/**
 * 
 * @author kevin
 *
 */
public class Select_bak extends CrawlerRoutine {

    @Param(name = "path", essential = false)
    String path;
    
    @Param(name = "isJSON", essential = true)
    boolean isJSON = false ;
    
    @Param(name = "field", essential = false)
    String field ;

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
        if(this.isJSON) {
        	if(this.field.length() == 0) {
        		throw new ParameterException("Parameter filed should not be empty.");
        	}
        	String sdom = dom.toString();
        	JSONObject obj = new JSONObject(sdom);
        	String value = obj.getString(this.field);
        	dom = (Element)Jsoup.parse(value);
        	System.out.println("hello  world");
        	if(this.path.length() == 0) {
        		this.localContext.put("dom", dom);
        		this.executeSubRoutines();
        		return;
        	}
        } else {
        	if(this.path.length() == 0) {
        		throw new ParameterException("Parameter path should not be empty.");
        	}
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
