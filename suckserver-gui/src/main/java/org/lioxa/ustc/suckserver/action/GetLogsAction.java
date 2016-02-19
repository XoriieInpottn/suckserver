package org.lioxa.ustc.suckserver.action;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.lioxa.ustc.suckserver.common.SuckService;
import org.lioxa.ustc.suckserver.common.vo.Log;
import org.lioxa.ustc.suckserver.utils.AbstractJSONAction;
import org.lioxa.ustc.suckserver.utils.ActionError;
import org.lioxa.ustc.suckserver.utils.Utils;

/**
 *
 * @author xi
 * @since Dec 28, 2015
 */
public class GetLogsAction extends AbstractJSONAction {

    private static final long serialVersionUID = -7624117363076795236L;

    long tid;
    Date after;
    int maxCount = 1000;

    public long getTid() {
        return this.tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public Date getAfter() {
        return this.after;
    }

    public void setAfter(Date after) {
        this.after = after;
    }

    @Override
    public void execute(Map<String, Object> result) {
        try {
            SuckService srv = Utils.getSuckService();
            Object[] tuple = srv.getTask(this.tid);
            List<Log> logs = srv.getLogs(this.tid, this.after, this.maxCount);
            result.put("task", tuple[0]);
            result.put("taskStat", tuple[1]);
            result.put("data", logs);
        } catch (RuntimeException | RemoteException e) {
            throw new ActionError(e.getMessage(), e);
        }
    }

}
