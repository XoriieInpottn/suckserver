package org.lioxa.ustc.suckserver.log;

import java.util.Date;
import java.util.List;

import org.lioxa.ustc.suckserver.common.vo.Log;
import org.lioxa.ustc.suckserver.common.vo.Task;

/**
 * The {@link Logger} is a interface behaves like a writer/reader to write/read
 * task status and logs to the persistent storage.<br/>
 * For example:
 * <ul>
 * <li>Write logs to the database.</li>
 * <li>Write logs to files.</li>
 * </ul>
 *
 * @author xi
 * @since Jan 28, 2016
 */
public interface Logger {

    /**
     * Create a new {@link Task} and add it to the storage.
     *
     * @param name
     *            The task name.
     * @param isTest
     *            Is in test mode?
     * @return The {@link Task}.
     */
    Task writeTask(String name, boolean isTest);

    /**
     * Update the status of an existing {@link Task}.
     *
     * @param tid
     *            The task ID.
     * @param status
     *            The new status.
     * @return The new {@link Task}.
     */
    Task updateTask(long tid, int status);

    /**
     * Read the specific {@link Task}.
     *
     * @param tid
     *            The task ID.
     * @return The Task.
     */
    Task readTask(long tid);

    /**
     * Read all kinds of {@link Tasks}s from database.
     *
     * @param start
     *            The start index of the result set.
     * @param count
     *            The count of result set.
     * @return Return a tuple, where [0] is the {@link Task} list and [1] is the
     *         total count of {@link Task}s in the database.
     */
    Object[] readTasks(int start, int count);

    /**
     * Create a new Log VO with the specific content for task tid.
     *
     * @param tid
     *            The task ID.
     * @param content
     *            The log content.
     * @return The Log VO.
     */
    Log writeLog(long tid, String content);

    /**
     * Create a new error type Log VO with the specific content for task tid.
     *
     * @param tid
     *            The task ID.
     * @param content
     *            The error content.
     * @return The Log VO.
     */
    Log writeError(long tid, String content);

    /**
     * Create a new error type Log VO with the specific exception for task tid.
     *
     * @param tid
     *            The task ID.
     * @param e
     *            The throwable object.
     * @return The {@link Log} VO.
     */
    Log writeError(long tid, Throwable e);

    /**
     *
     * @param tid
     *            The task ID.
     * @param e
     *            The throwable object.
     * @param lineNum
     *            The line number.
     * @param colNum
     *            The column number.
     * @return The {@link Log} VO.
     */
    Log writeError(long tid, Throwable e, int lineNum, int colNum);

    /**
     * Read logs (include the error type log) that wrote after the specific time
     * for task tid.
     *
     * @param tid
     *            The task ID.
     * @param after
     *            The time after which logs will be read.
     * @param maxCount
     *            The maximum count of the result.
     * @return The list of {@link Log} VOs.
     */
    List<Log> readLogs(long tid, Date after, int maxCount);

}
