package org.lioxa.ustc.suckserver.action;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.lioxa.ustc.suckserver.utils.AbstractJSONAction;
import org.lioxa.ustc.suckserver.utils.ActionError;

/**
 *
 * @author xi
 * @since Dec 27, 2015
 */
public class GetRoutinesAction extends AbstractJSONAction {

    private static final long serialVersionUID = -550882441598874528L;

    @Override
    public void execute(Map<String, Object> result) {
        File file = new File("/tmp/suckservers-routines.json");
        String content;
        try {
            content = FileUtils.readFileToString(file);
        } catch (IOException e) {
            throw new ActionError("Suckserver service may not running.");
        }
        // JSONArray routines = new JSONArray(content);
        result.put("routines", content);
    }

}
