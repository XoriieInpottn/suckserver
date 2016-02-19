package org.lioxa.ustc.suckserver.log;

/**
 * Loggers is used to create singleton LogWriter instance.
 *
 * @author xi
 * @since Jan 28, 2016
 */
public class Loggers {

    static Logger logger = null;

    /**
     * Get the default logger.
     *
     * @return The default logger.
     */
    public synchronized static Logger getDefault() {
        if (logger == null) {
            logger = new DBLogger();
        }
        return logger;
    }

}
