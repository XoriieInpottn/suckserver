package org.lioxa.ustc.suckserver.routine.crawler;

import org.lioxa.ustc.suckserver.routine.Routine;

/**
 * The {@link CrawlerRoutine} is the abstract routine for template driven
 * crawler.<br/>
 * Every implementation of {@link CrawlerRoutine} is associated with an XML tag
 * in the template.
 *
 * @author xi
 * @since Nov 16, 2015
 */
public abstract class CrawlerRoutine extends Routine<CrawlerContext> {

    int lineNumber = -1;
    int columnNumber = -1;

    /**
     * Get the associated tag line number.
     *
     * @return The line number.
     */
    public int getLineNumber() {
        return this.lineNumber;
    }

    /**
     * Set the associated tag line number.
     *
     * @param lineNumber
     *            The line number.
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * Get the associated tag column number.
     *
     * @return The column number.
     */
    public int getColumnNumber() {
        return this.columnNumber;
    }

    /**
     * Set the associated tag column number.
     *
     * @param columnNumber
     *            The column number.
     */
    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

}
