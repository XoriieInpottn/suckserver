package org.lioxa.ustc.suckserver.routine;

/**
 * The Parameter Exception<br/>
 * <br/>
 * This exception will be thrown when a routine: <br/>
 * 1) is lack of essential parameters<br/>
 * 2) has the incorrect parameter value<br/>
 *
 * @author xi
 * @since Dec 29, 2015
 */
public class ParameterException extends Exception {

    private static final long serialVersionUID = 6306209103664027840L;

    public ParameterException(String msg) {
        super(msg);
    }

    public ParameterException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
