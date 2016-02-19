package org.lioxa.ustc.suckserver.action;

import java.rmi.RemoteException;
import java.util.Map;

import org.lioxa.ustc.suckserver.utils.AbstractJSONAction;
import org.lioxa.ustc.suckserver.utils.ActionError;
import org.lioxa.ustc.suckserver.utils.Utils;

/**
 *
 * @author xi
 * @since Feb 13, 2016
 */
public class GetTasksAction extends AbstractJSONAction {

    private static final long serialVersionUID = -2659918338774457878L;

    int start;
    int length;

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public void execute(Map<String, Object> result) {
        Object[] tasks;
        try {
            tasks = Utils.getSuckService().getTasks(this.start, this.length);
        } catch (RemoteException e) {
            Throwable e1 = e;
            while (e1 != null && e1 instanceof RemoteException) {
                e1 = e1.getCause();
            }
            throw new ActionError("Failed to get tasks.", e1);
        }
        result.put("data", tasks[0]);
        result.put("totalCount", tasks[1]);
    }

}
