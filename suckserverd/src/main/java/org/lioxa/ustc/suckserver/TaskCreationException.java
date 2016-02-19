package org.lioxa.ustc.suckserver;

/**
 * The {@link TaskCreationException} is thrown when there are errors during the
 * procedure of creating a runnable task.
 *
 * @author xi
 * @since Feb 10, 2016
 */
public class TaskCreationException extends Exception {

    private static final long serialVersionUID = 4663232830654375682L;

    public TaskCreationException(String msg) {
        super(msg);
    }

    public TaskCreationException(String msg, Throwable e) {
        super(msg, e);
    }

}
