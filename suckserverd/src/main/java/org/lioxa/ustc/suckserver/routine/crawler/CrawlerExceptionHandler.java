package org.lioxa.ustc.suckserver.routine.crawler;

import org.lioxa.ustc.suckserver.RunnableTask;
import org.lioxa.ustc.suckserver.common.vo.TaskStat;
import org.lioxa.ustc.suckserver.log.Loggers;
import org.lioxa.ustc.suckserver.routine.Context;
import org.lioxa.ustc.suckserver.routine.ExceptionHandler;
import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Routine;

/**
 *
 * @author xi
 * @since Dec 29, 2015
 */
public class CrawlerExceptionHandler implements ExceptionHandler {

    @Override
    public boolean handleException(Context context, Routine<? extends Context> routine, Exception e) {
        CrawlerContext crawlerContext = (CrawlerContext) context;
        CrawlerRoutine crawlerRoutine = (CrawlerRoutine) routine;
        RunnableTask runnableTask = crawlerContext.getRunnableTask();
        long tid = runnableTask.getId();
        int lineNum = crawlerRoutine.getLineNumber();
        int colNum = crawlerRoutine.getColumnNumber();
        Loggers.getDefault().writeError(tid, e, lineNum, colNum);
        //
        // Change the task statistics.
        TaskStat taskStat = runnableTask.getTaskStat();
        taskStat.error();
        //
        // Is fatal ?
        // If is a fatal error, cannot continue.
        if (e instanceof ExecutionException) {
            return ((ExecutionException) e).isFatal();
        }
        //
        // ParameterException, RuntimeException and other exceptions are
        // regarded as fatal errors.
        return true;
    }

}
