package org.lioxa.ustc.suckserver.action;

import java.rmi.RemoteException;
import java.util.Map;

import org.lioxa.ustc.suckserver.common.SuckService;
import org.lioxa.ustc.suckserver.utils.AbstractJSONAction;
import org.lioxa.ustc.suckserver.utils.ActionError;
import org.lioxa.ustc.suckserver.utils.Utils;

import com.mysql.jdbc.StringUtils;

/**
 *
 * @author xi
 * @since Dec 27, 2015
 */
public class CreateTaskAction extends AbstractJSONAction {

    private static final long serialVersionUID = -6498661353907289519L;

    String template;
    boolean test;

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public boolean isTest() {
        return this.test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    @Override
    public void execute(Map<String, Object> result) {
        if (StringUtils.isNullOrEmpty(this.template)) {
            throw new ActionError("Template should not be empty.");
        }
        try {
            SuckService srv = Utils.getSuckService();
            long tid = srv.createTask(this.template, this.test);
            result.put("tid", tid);
        } catch (RemoteException | RuntimeException e) {
            throw new ActionError(e.getMessage(), e);
        }
    }

}
