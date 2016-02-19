package org.lioxa.ustc.suckserver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.lioxa.ustc.suckserver.routine.crawler.RoutineTreeException;
import org.lioxa.ustc.suckserver.template.TemplateException;

/**
 * TaskController is used execute a RunnableTask<br/>
 * It includes functions below:
 * <ul>
 * <li>It creates thread to run a task and generate a task ID (tid) at the same
 * time.</li>
 * <li>The main thread can get the RunnableTask object through the tid.</li>
 * <li>The main thread can stop the specific task through the tid.</li>
 * <li>When a task is complete or exit due to fatal exceptions, the RunnableTask
 * object will be removed from the controller.</li>
 * </ul>
 *
 * @author xi
 * @since Jan 27, 2016
 */
public class TaskController {

    /**
     * The thread pool.
     */
    ExecutorService pool = Executors.newCachedThreadPool();

    /**
     * Task map that map tid to RunnableTask.
     */
    Map<Long, RunnableTask> tasks = new HashMap<>();

    /**
     * Create a runnable task and start to run it in another thread.
     *
     * @param template
     *            The template of the crawler task.
     * @param isTest
     *            Does the task run in test model ?
     * @return The generated {@link RunnableTask}.
     * @throws TaskCreationException
     */
    public synchronized RunnableTask createTask(String template, boolean isTest) throws TaskCreationException {
        //
        // create runnable task
        RunnableTask runnableTask;
        try {
            runnableTask = new RunnableTask(this, template, isTest);
        } catch (RuntimeException | RoutineTreeException | TemplateException e) {
            throw new TaskCreationException("Failed to create runnable task.", e);
        }
        //
        // stop the duplicated task
        long tid = runnableTask.getId();
        RunnableTask runnableTask1 = this.tasks.get(tid);
        if (runnableTask1 != null) {
            runnableTask1.sendStopReq();
        }
        //
        // execute the task
        this.tasks.put(tid, runnableTask);
        this.pool.execute(runnableTask);
        return runnableTask;
    }

    /**
     * Stop the specific task. <br/>
     * The task cannot be stopped immediately. It just send a stop signal to the
     * thread.
     *
     * @param tid
     *            The task ID.
     */
    public synchronized void stopTask(long tid) {
        RunnableTask runnableTask = this.tasks.get(tid);
        if (runnableTask != null) {
            runnableTask.sendStopReq();
        }
    }

    /**
     * Get the RunnableTask object from the tid.
     *
     * @param tid
     *            The tid.
     * @return The RunnableTask object if there is one correspond with the tid.
     *         Otherwise, null will be returned.
     */
    public synchronized RunnableTask getTask(long tid) {
        return this.tasks.get(tid);
    }

    /**
     * Remove the RunnableTask object from the map. <br/>
     * The method will be called by the {@link RunnableTask} when the task is
     * not active (complete, exception, stop).
     *
     * @param tid
     *            The tid.
     * @return The removed task object.
     */
    synchronized RunnableTask removeTask(long tid) {
        return this.tasks.remove(tid);
    }

}
