package org.lioxa.ustc.suckserver.routine;

/**
 *
 * @author xi
 * @since Nov 16, 2015
 */
public class ExecutionException extends Exception {

    private static final long serialVersionUID = 5808774428728822467L;

    public ExecutionException(String msg) {
        super(msg);
    }

    public ExecutionException(String msg, Throwable cause) {
        super(msg, cause);
    }

    boolean isFatal = false;

    public boolean isFatal() {
        return this.isFatal;
    }

    public void setFatal(boolean isFatal) {
        this.isFatal = isFatal;
    }

}
