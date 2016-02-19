package org.lioxa.ustc.suckserver.common.vo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author xi
 * @since Dec 27, 2015
 */
public class Task implements Serializable {

    private static final long serialVersionUID = -3786450675361037087L;

    public static final int TYPE_DEPLOY = 0;
    public static final int TYPE_TEST = 1;

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_COMPLETE = 1;
    public static final int STATUS_STOP = 2;
    public static final int STATUS_ERROR = -1;

    long id;
    String name;
    int type;
    int status;
    Date startTime;
    Date endTime;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
