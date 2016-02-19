package org.lioxa.ustc.suckserver.routine;

import java.util.Map;

/**
 * The {@link Context} stores the context information for its {@link Routine}s.<br/>
 * The most important context information is variables which is a map from
 * string to object.
 *
 * @author xi
 * @since Nov 26, 2015
 */
public interface Context {

    /**
     * Get variable map.
     *
     * @return The variable map from string to object.
     */
    Map<String, Object> getVars();

}
