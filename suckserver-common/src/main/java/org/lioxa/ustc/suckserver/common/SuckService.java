package org.lioxa.ustc.suckserver.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import org.lioxa.ustc.suckserver.common.vo.Log;
import org.lioxa.ustc.suckserver.common.vo.Task;
import org.lioxa.ustc.suckserver.common.vo.TaskStat;

/**
 * This is the RPC interface of suckserverd.<br/>
 * Through this interface, a client can communicate with the crawler server on
 * which runs the suckserverd service.<br/>
 *
 * The remote implementation can be obtained through the following code:<br/>
 *
 * SuckService srv = (SuckService)
 * Naming.lookup(&quot;rmi://host:port/SuckService&quot;);<br/>
 *
 * where "host" means the server address and "port" means the port that
 * suckserverd listened.<br/>
 *
 * @author xi
 * @since Dec 19, 2015
 */
public interface SuckService extends Remote {

	/**
	 * Create a new task from a crawl template.<br/>
	 * The template content is given directly.
	 *
	 * @param template
	 *            The crawl template of a task.
	 * @param isTest
	 *            Is run in test mode.
	 * @return If success, the system will return the task ID (tid).
	 * @throws RemoteException
	 */
	long createTask(String template, boolean isTest) throws RemoteException;

	/**
	 * Create a new task from a template file.
	 *
	 * @param path
	 *            The path of the crawl template file on the server.
	 * @param isTest
	 *            Is run in test mode.
	 * @return If success, the system will return the task ID (tid).
	 * @throws RemoteException
	 */
	long createTaskFromFile(String path, boolean isTest) throws RemoteException;

	/**
	 * Stop the running task.
	 *
	 * @param tid
	 *            The task ID.
	 * @throws RemoteException
	 */
	void stopTask(long tid) throws RemoteException;

	/**
	 * Get all kinds of tasks, include the running, stopped ... <br/>
	 * The tasks is ordered by time descending.
	 *
	 * @param start
	 *            The start index of the result set.
	 * @param count
	 *            The count of record in the result set.
	 * @return A tuple in which [0] is a list and [1] is total count. Each
	 *         element in the list is a tuple in which [0] is the {@link Task}
	 *         object and [1] is the {@link TaskStat} object.
	 * @throws RemoteException
	 */
	Object[] getTasks(int start, int count) throws RemoteException;

	/**
	 * Get the task information.
	 *
	 * @param tid
	 *            The task ID.
	 * @return A tuple in which [0] is the {@link Task} object and [1] is the
	 *         {@link TaskStat} object.
	 * @throws RemoteException
	 */
	Object[] getTask(long tid) throws RemoteException;

	/**
	 * Get logs for a given task after the specific time.
	 *
	 * @param tid
	 *            The task ID.
	 * @param after
	 *            The time after which logs will be selected.
	 * @param maxCount
	 *            The maximum count of the result.
	 * @return
	 * @throws RemoteException
	 */
	List<Log> getLogs(long tid, Date after, int maxCount)
			throws RemoteException;

	/**
	 * Remove the specific task and its logs.
	 *
	 * @param tid
	 *            The task ID.
	 * @throws RemoteException
	 */
	void removeTask(long tid) throws RemoteException;

	void sendIC(long tid, String icValue) throws RemoteException;

}
