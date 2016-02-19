package org.lioxa.ustc.suckserver.routine;

/**
 *
 * @author xi
 * @since Nov 26, 2015
 */
public interface ExceptionHandler {

    boolean handleException(Context context, Routine<? extends Context> routine, Exception e);

}
