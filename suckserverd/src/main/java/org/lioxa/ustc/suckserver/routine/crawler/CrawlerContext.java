package org.lioxa.ustc.suckserver.routine.crawler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lioxa.ustc.suckserver.RunnableTask;
import org.lioxa.ustc.suckserver.routine.Context;
import org.lioxa.ustc.suckserver.routine.ExceptionHandler;
import org.lioxa.ustc.suckserver.routine.Routine;
import org.lioxa.ustc.suckserver.template.TemplateException;
import org.lioxa.ustc.suckserver.template.TemplateNode;
import org.lioxa.ustc.suckserver.template.TemplateReader;
import org.lioxa.ustc.suckserver.utils.TableInfo;

/**
 * The {@link CrawlerContext} is the global context of all
 * {@link CrawlerRoutine}s. <br/>
 * It provides some important functions include:
 * <ul>
 * <li>Create routine tree based on template tree.</li>
 * <li>Store all temporary variables.</li>
 * <li>Store all table information.</li>
 * <li>Log to database.</li>
 * <li>Control overall crawl routine.</li>
 * </ul>
 *
 * @author xi
 * @since Nov 16, 2015
 */
public class CrawlerContext implements Context {

	/**
	 * Package that contains crawler routine implementations.
	 */
	public static final String PKG_NAME;

	static {
		PKG_NAME = CrawlerContext.class.getPackage().getName() + ".impl.";
	}

	//
	//
	//
	// The runnable task object.

	RunnableTask runnableTask;

	public RunnableTask getRunnableTask() {
		return this.runnableTask;
	}

	//
	//
	//
	// Exception handler.
	// Log exception messages and determine whether the routine should continue.
	// We don't use the single mode because "exception handler" depends on the
	// specific "log writer".

	ExceptionHandler exceptionHandler;

	//
	//
	//
	// Constructors.

	public CrawlerContext(RunnableTask runnableTask) {
		this.runnableTask = runnableTask;
		this.exceptionHandler = new CrawlerExceptionHandler();
	}

	//
	//
	//
	// Tree creation.
	// Create routines from XML in 2 ways:
	// 1) create from template string directly
	// 2) create from InputStream

	/**
	 * Create a {@link CrawlerRoutine} tree from a XML formatted template
	 * string.
	 *
	 * @param template
	 *            The template string.
	 * @return The root of the {@link CrawlerRoutine} tree.
	 * @throws RoutineTreeException
	 */
	public CrawlerRoutine createRoutineTree(String template)
			throws RoutineTreeException {
		TemplateReader reader = new TemplateReader();
		TemplateNode root;
		try {
			root = reader.read(template);
		} catch (TemplateException e) {
			String err = "Failed to create template tree.";
			throw new RoutineTreeException(err, e);
		}
		return this.createRoutineTreeFromDOM(root);
	}

	/**
	 * Create a {@link CrawlerRoutine} tree from an input stream.
	 *
	 * @param is
	 *            The input stream.
	 * @return The root of the {@link CrawlerRoutine} tree.
	 * @throws RoutineTreeException
	 */
	public CrawlerRoutine createRoutineTree(InputStream is)
			throws RoutineTreeException {
		TemplateReader reader = new TemplateReader();
		TemplateNode root;
		try {
			root = reader.read(is);
		} catch (TemplateException e) {
			String err = "Failed to create template tree.";
			throw new RoutineTreeException(err, e);
		}
		return this.createRoutineTreeFromDOM(root);
	}

	/**
	 * Create a {@link CrawlerRoutine} tree from a {@link TemplateNode} tree
	 * (DOM tree).
	 *
	 * @param root
	 *            The root of the DOM tree.
	 * @return The root of the {@link CrawlerRoutine} tree.
	 * @throws RoutineTreeException
	 */
	CrawlerRoutine createRoutineTreeFromDOM(TemplateNode root)
			throws RoutineTreeException {
		//
		// Get name, parameters and sub routines.
		String name = root.getName();
		Map<String, String> params = root.getParams();
		List<TemplateNode> children = root.getChildren();
		List<Routine<CrawlerContext>> subRoutines = new ArrayList<>(
				children.size());
		for (TemplateNode node : children) {
			CrawlerRoutine subRoutine = this.createRoutineTreeFromDOM(node);
			subRoutines.add(subRoutine);
		}
		// Create new Routine.
		CrawlerRoutine routine = this.createRoutine(name);
		routine.init(this, params, subRoutines);
		routine.getExceptionHandlers().add(this.exceptionHandler);
		routine.setLineNumber(root.getLineNumber());
		routine.setColumnNumber(root.getColumnNumber());
		return routine;
	}

	/**
	 * Create a {@link CrawlerRoutine} from a routine name.
	 *
	 * @param name
	 *            The routine name.
	 * @return The new {@link CrawlerRoutine}.
	 * @throws RoutineTreeException
	 */
	CrawlerRoutine createRoutine(String name) throws RoutineTreeException {
		//
		// Convert node name to class name.
		String[] words = name.split("\\-");
		StringBuilder sb = new StringBuilder();
		for (String word : words) {
			if (word.length() == 0) {
				continue;
			}
			sb.append(word);
			sb.setCharAt(sb.length() - word.length(),
					Character.toUpperCase(word.charAt(0)));
		}
		name = sb.toString();
		//
		// Create instance.
		Object instance;
		try {
			Class<?> clazz = Class.forName(PKG_NAME + name);
			instance = clazz.newInstance();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			//
			// No class related to the given name.
			// The class cannot be construct.
			String err = String
					.format("Failed to create routine \"%s\".", name);
			throw new RoutineTreeException(err, e);
		}
		if (!(instance instanceof CrawlerRoutine)) {
			//
			// The class is not a subclass of CrawlerRoutine.
			String err = String
					.format("Failed to create routine \"%s\". Not a subclass of CrawlerRoutine",
							name);
			throw new RoutineTreeException(err);
		}
		return (CrawlerRoutine) instance;
	}

	//
	//
	//
	// Variable.

	Map<String, Object> vars = new HashMap<>();

	@Override
	public Map<String, Object> getVars() {
		return this.vars;
	}

	//
	//
	//
	// Tables.

	Map<String, TableInfo> tables = new HashMap<>();

	/**
	 * Get tables of current task.
	 *
	 * @return The map from table name to {@link TableInfo}.
	 */
	public Map<String, TableInfo> getTables() {
		return this.tables;
	}

	//
	//
	//
	// Thread status.
	// If it is not alive, the crawler will stop.

	boolean stopReq = false;

	/**
	 * Is the crawler thread is stopping ?
	 *
	 * @return True if stopping or false if not.
	 */
	public synchronized boolean isStopReq() {
		// System.out.println(this.stopReq);
		return this.stopReq;
	}

	/**
	 * Set the stopping flag for the crawler thread. <br/>
	 * If this flag is set, when the current routine is complete, it will not
	 * continue to execute the next one.
	 *
	 * @param stopReq
	 *            The flag.
	 */
	public synchronized void setStopReq(boolean stopReq) {
		this.stopReq = stopReq;
	}

	private Browser browserDriver;

	public Browser getBrowserDriver() {
		return browserDriver;
	}

	public void setBrowserDriver(Browser browserDriver) {
		this.browserDriver = browserDriver;
	}

	private String icBase64 = "";
    
	public String getIcBase64() {
		return icBase64;
	}

	public void setIcBase64(String icBase64) {
		this.icBase64 = icBase64;
	}

	private String icvalue = "";

	public String getIcvalue() {
		return icvalue;
	}

	public void setIcvalue(String icvalue) {
		this.icvalue = icvalue;
	}

}
