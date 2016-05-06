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
public class SendIdentifyCodeAction extends AbstractJSONAction {

	private static final long serialVersionUID = 6251233884949925845L;

	long tid;

	public long getTid() {
		return tid;
	}

	public void setTid(long tid) {
		this.tid = tid;
	}

	String icValue;

	public String getIcValue() {
		return icValue;
	}

	public void setIcValue(String icValue) {
		this.icValue = icValue;
	}

	@Override
	public void execute(Map<String, Object> result) {
		try {
			SuckService srv = Utils.getSuckService();
			srv.sendIC(tid, icValue);
		} catch (RuntimeException | RemoteException e) {
			throw new ActionError(e.getMessage(), e);
		}
	}

}
