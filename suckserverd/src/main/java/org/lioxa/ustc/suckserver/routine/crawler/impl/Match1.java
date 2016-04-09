package org.lioxa.ustc.suckserver.routine.crawler.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 *
 * @author xi
 * @since Nov 21, 2015
 */
public class Match1 extends CrawlerRoutine {

    @Param(name = "var", essential = true)
    String var;

    @Param(name = "path")
    String path;

    @Param(name = "attr")
    String attr;

    @Param(name = "regexp")
    String regexp = ".*";

    @Param(name = "flags")
    String flags = "m";

    @Param(name = "index")
    String index = "0";

    //
    // execution

    @Override
    public void exec() throws ParameterException, ExecutionException {
        if (this.globalContext.isStopReq()) {
            return;
        }
        //
        // check parameters
        if (this.var == null || (this.var = this.var.trim()).length() == 0) {
            throw new ParameterException("Parameter \"var\" should not be empty.");
        }
        if (!this.var.matches("[a-zA-Z]\\w*")) {
            throw new ParameterException("Invalid parameter \"var\".");
        }
        //
        // build pattern
        int flags = 0;
        if (this.flags != null) {
            for (int i = 0; i < this.flags.length(); i++) {
                char flag = this.flags.charAt(i);
                switch (flag) {
                case 'i':
                    flags |= Pattern.CASE_INSENSITIVE;
                    break;
                case 'm':
                    flags |= Pattern.MULTILINE;
                    break;
                default:
                    String err = String.format("Invalid flag \"%c\".", flag);
                    throw new ParameterException(err);
                }
            }
        }
        Pattern pattern = Pattern.compile(this.regexp, flags);
        //
        // build index array
        int[] indexArr;
        this.index = this.index.trim();
        if (this.index.length() == 0) {
            indexArr = new int[] { 0 };
        } else {
            String[] tokens = this.index.split("\\s*\\,\\s*");
            indexArr = new int[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                try {
                    indexArr[i] = Integer.parseInt(tokens[i]);
                } catch (NumberFormatException e) {
                    String msg = String.format("Invalid index %s.", this.index);
                    throw new ParameterException(msg, e);
                }
            }
        }
        //
        // We remove the variable first, since if we failed matching in the next
        // steps, the routine may continue to execute, and the original value
        // must be clear. This is VERY IMPORTANT, because it guarantees that the
        // database not be polluted.
        this.globalContext.getVars().remove(this.var);
        //
        // get raw string from DOM
        WebElement dom = (WebElement) this.getMasterContext().get("dom");
        if (dom == null) {
            //
            // If there is no DOM in current context:
            // 1) the template have errors
            // 2) the routine mechanism may have bugs
            // In such situation, the crawler cannot continue to work any more.
            ExecutionException e = new ExecutionException("There is no DOM loaded in current context.");
            e.setFatal(true);
            throw e;
        }
        String rawStr;
        if (this.path != null) {
        	 WebElement elems = dom.findElement(By.cssSelector(this.path));
            rawStr = this.attr == null ? elems.getText() : elems.getAttribute(this.attr);
        } else {
            rawStr = this.attr == null ? dom.getText() : dom.getAttribute(this.attr);
        }
        //
        // matcher and find sub patterns
        Matcher m = pattern.matcher(rawStr);
        String[] matches = new String[indexArr.length];
        if (!m.find()) {
            //
            // the given pattern cannot be found
            String msg = String.format("Could not find value for \"%s\" with regexp \"%s\" to \"%s\".", this.var,
                    this.regexp, rawStr);
            throw new ExecutionException(msg);
        }
        for (int i = 0; i < indexArr.length; i++) {
            try {
                matches[i] = m.group(indexArr[i]);
            } catch (RuntimeException e) {
                //
                // the given pattern can be found, but the sub patterns can't
                String msg = String.format("Could not find value for \"%s\" with regexp \"%s\" to \"%s\".", this.var,
                        this.regexp, rawStr);
                throw new ExecutionException(msg, e);
            }
        }
        //
        // join these sub patterns
        String value = StringUtils.join(matches, " ");
        this.globalContext.getVars().put(this.var, value);
//        if (this.path != null) {
//       	 	WebElement elems = dom.findElement(By.cssSelector(this.path));
//       	 	elems.click();
//       	 	this.globalContext.getBrowserDriver().windowForward();
//       	 	try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//       	 	this.globalContext.getBrowserDriver().windowBack();
//       	 }
    }
}
