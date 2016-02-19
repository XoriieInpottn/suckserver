package org.lioxa.ustc.suckserver.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author xi
 * @since Feb 7, 2015
 */
public abstract class AbstractJSONAction extends ActionSupport {

    private static final long serialVersionUID = 8226606152028076872L;

    public static final String DONE = "done";

    public abstract void execute(final Map<String, Object> result);

    @Override
    public String execute() {
        this.result = new HashMap<String, Object>();
        try {
            this.execute(this.result);
            this.result.put("status", "success");
        } catch (ActionError e) {
            this.result.put("status", "error");
            this.result.put("errorInfo", e.getMessage());
        } catch (Exception e) {
            this.result.put("status", "exception");
            this.result.put("exception", e.getClass().getName());
            this.result.put("exceptionMessage", e.getMessage());
        }
        return DONE;
    }

    private Map<String, Object> result;

    public Map<String, Object> getResult() {
        return this.result;
    }

    public void setResult(final Map<String, Object> result) {
        this.result = result;
    }

    //
    // HTTP Session

    private final HttpSession httpSession = ServletActionContext.getRequest().getSession();

    public Object getSessionAttribute(final String key) {
        return this.httpSession.getAttribute(key);
    }

    public void setSessionAttribute(final String key, final Object value) {
        this.httpSession.setAttribute(key, value);
    }

    public void removeAttribute(final String key) {
        this.httpSession.removeAttribute(key);
    }
}
