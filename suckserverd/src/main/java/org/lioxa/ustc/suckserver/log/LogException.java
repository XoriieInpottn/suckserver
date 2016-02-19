package org.lioxa.ustc.suckserver.log;

/**
 *
 * @author xi
 * @since Dec 29, 2015
 */
public class LogException extends RuntimeException {

    private static final long serialVersionUID = 2213690017337417740L;

    public LogException(String msg) {
        super(msg);
    }

    public LogException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
