package org.lioxa.ustc.suckserver.common.vo;

import java.io.Serializable;

/**
 * The {@link TaskStat} is used to store some statistics of the running task.
 *
 * @author xi
 * @since Feb 11, 2016
 */
public class TaskStat implements Serializable {

    private static final long serialVersionUID = -1732404844317940189L;

    long successCount = 0;
    long errorCount = 0;

    public long getSuccessCount() {
        return this.successCount;
    }

    public void setSuccessCount(long successCount) {
        this.successCount = successCount;
    }

    public long getErrorCount() {
        return this.errorCount;
    }

    public void setErrorCount(long errorCount) {
        this.errorCount = errorCount;
    }

    //
    //
    //
    // Some shortcuts.

    /**
     * Increase the success count.
     */
    public void success() {
        this.successCount++;
    }

    /**
     * Increase the error count.
     */
    public void error() {
        this.errorCount++;
    }

}
