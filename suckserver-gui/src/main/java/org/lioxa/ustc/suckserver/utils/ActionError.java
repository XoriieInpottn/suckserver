package org.lioxa.ustc.suckserver.utils;

/**
 *
 * @author xi
 * @since Feb 7, 2015
 */
public class ActionError extends RuntimeException {

    private static final long serialVersionUID = -620364424104108086L;

    public ActionError(String msg) {
        super(msg);
    }

    public ActionError(String msg, Throwable cause) {
        super(msg, cause);
    }
}
