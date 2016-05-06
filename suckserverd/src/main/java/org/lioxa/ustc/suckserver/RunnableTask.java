package org.lioxa.ustc.suckserver;

import org.lioxa.ustc.suckserver.common.vo.Task;
import org.lioxa.ustc.suckserver.common.vo.TaskStat;
import org.lioxa.ustc.suckserver.log.Logger;
import org.lioxa.ustc.suckserver.log.Loggers;
import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerContext;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.lioxa.ustc.suckserver.routine.crawler.RoutineTreeException;
import org.lioxa.ustc.suckserver.template.TemplateException;
import org.lioxa.ustc.suckserver.template.TemplateNode;
import org.lioxa.ustc.suckserver.template.TemplateReader;

/**
 *
 * @author xi
 * @since Dec 25, 2015
 */
public class RunnableTask implements Runnable {

    TaskController controller;

    Task task;
    TaskStat taskStat;
    CrawlerContext context;
    CrawlerRoutine root;

    public RunnableTask(TaskController controller, String template, boolean isTest) throws TemplateException,
    RoutineTreeException {
        this.controller = controller;
        //
        // Create template tree and write task VO
        TemplateReader reader = new TemplateReader();
        TemplateNode root;
        root = reader.read(template);
        int lineNum = root.getLineNumber();
        int colNum = root.getColumnNumber();
        if (!"task".equals(root.getName())) {
            String msg = String.format("line %d, column %d: The root node must be \"task\".", lineNum, colNum);
            throw new TemplateException(msg);
        }
        String name = root.getParams().get("name");
        if (name == null || (name = name.trim()).length() == 0) {
            String msg = String.format("line %d, column %d: Task name should not be empty.", lineNum, colNum);
            throw new TemplateException(msg);
        }
        if (name.length() > 64) {
            String msg = String.format("line %d, column %d: Task name \"%s\" is too long.", lineNum, colNum, name);
            throw new TemplateException(msg);
        }
        this.task = Loggers.getDefault().writeTask(name, isTest);
        this.taskStat = new TaskStat();
        //
        // Create context and routine tree.
        this.context = new CrawlerContext(this);
        try {
            this.root = this.context.createRoutineTree(template);
        } catch (RoutineTreeException e) {
            //
            // We must log the error since crawler context cannot use the
            // exception handler chain.
            Logger logger = Loggers.getDefault();
            long tid = this.task.getId();
            logger.writeError(tid, e);
            logger.updateTask(tid, Task.STATUS_ERROR);
            throw e;
        }
    }
     public CrawlerContext getContext() {
    	 return this.context;
     }
    //
    //
    //
    // Task status information.

    /**
     * Get the task ID (tid) which is identically the same as the one of
     * {@link Task} in the database.
     *
     * @return The task ID.
     */
    public long getId() {
        return this.task.getId();
    }

    /**
     * Does the crawler run in test mode? (not really save data, just write some
     * logs) or a deploy task.
     *
     * @return True if the task is a testing task. False if not.
     */
    public boolean isTest() {
        return this.task.getType() == Task.TYPE_TEST;
    }

    /**
     * Get the {@link TaskStat} object.
     *
     * @return The {@link TaskStat} object.
     */
    public TaskStat getTaskStat() {
        return this.taskStat;
    }

    //
    //
    //
    // Start and stop the thread.

    @Override
    public void run() {
        this.executeRoutineTree();
        this.controller.removeTask(this.task.getId());
    }

    void executeRoutineTree() {
        if (this.root == null) {
            return;
        }
        try {
            try {
                this.root.execute();
            } catch (ParameterException | ExecutionException | RuntimeException e) {
                //
                // End with exception
                // This happens when the "exception handler chain" cannot handle
                // the exception, which means this is a fatal error.
                // The exception message has been logged by the
                // "log exception handler".
                Loggers.getDefault().updateTask(this.task.getId(), Task.STATUS_ERROR);
                return;
            }
            if (this.context.isStopReq()) {
                //
                // Stop by user
                // No fatal errors, but the user found something goes wrong.
                // She / He turn off the crawler manually.
                Loggers.getDefault().updateTask(this.task.getId(), Task.STATUS_STOP);
                return;
            } else {
                //
                // Complete
                // This is the BEST result.
                // Nothing serious happened.
                Loggers.getDefault().updateTask(this.task.getId(), Task.STATUS_COMPLETE);
                return;
            }
        } catch (RuntimeException e) {
            //
            // This is the LAST DEFENSE line to handle the exception.
            // That means the situation is SO SERIOUS and even the "log writer"
            // cannot work.
            // This may happen when the network connection goes down or the
            // database crashes.
            // To protect the server, we have no choice but exit this thread.
            return;
        }
    }

    public void sendStopReq() {
        this.context.setStopReq(true);
    }

}
