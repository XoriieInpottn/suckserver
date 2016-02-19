package org.lioxa.ustc.suckserver.routine;

/**
 * The {@link HandledException} is thrown when any other exceptions are handled
 * by an {@link org.lioxa.ustc.suckserver.routine.ExceptionHandler}.
 *
 * @author xi
 * @since Feb 12, 2016
 */
public class HandledException extends ExecutionException {

    private static final long serialVersionUID = 1405295309094119425L;

    public HandledException(String msg) {
        super(msg);
    }

    public HandledException(String msg, Throwable e) {
        super(msg, e);
    }

}
