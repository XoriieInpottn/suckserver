package org.lioxa.ustc.suckserver.action;

import java.rmi.RemoteException;
import java.util.Map;

import org.lioxa.ustc.suckserver.utils.AbstractJSONAction;
import org.lioxa.ustc.suckserver.utils.ActionError;
import org.lioxa.ustc.suckserver.utils.Utils;

/**
 *
 * @author xi
 * @since Feb 24, 2016
 */
public class RemoveTaskAction extends AbstractJSONAction {

    private static final long serialVersionUID = -6319569397606439199L;

    long tid;

    public long getTid() {
        return this.tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    @Override
    public void execute(Map<String, Object> result) {
        try {
            Utils.getSuckService().removeTask(this.tid);
        } catch (RuntimeException | RemoteException e) {
            throw new ActionError(e.getMessage(), e);
        }
    }

}
