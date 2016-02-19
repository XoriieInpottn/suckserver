package org.lioxa.ustc.suckserver.action;

import java.rmi.RemoteException;
import java.util.Map;

import org.lioxa.ustc.suckserver.common.SuckService;
import org.lioxa.ustc.suckserver.utils.AbstractJSONAction;
import org.lioxa.ustc.suckserver.utils.ActionError;
import org.lioxa.ustc.suckserver.utils.Utils;

/**
 *
 * @author xi
 * @since Dec 28, 2015
 */
public class StopTaskAction extends AbstractJSONAction {

    private static final long serialVersionUID = 2174333884949925845L;

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
            SuckService srv = Utils.getSuckService();
            srv.stopTask(this.tid);
        } catch (RuntimeException | RemoteException e) {
            throw new ActionError(e.getMessage(), e);
        }
    }

}
