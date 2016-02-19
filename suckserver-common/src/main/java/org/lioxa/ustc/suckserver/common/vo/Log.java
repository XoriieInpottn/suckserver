package org.lioxa.ustc.suckserver.common.vo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author xi
 * @since Dec 27, 2015
 */
public class Log implements Serializable {

    private static final long serialVersionUID = 7428253621892919988L;

    public static final int TYPE_INFO = 0;
    public static final int TYPE_ERROR = 1;

    long id;
    long tid;
    int type;
    Date time;
    String content;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTid() {
        return this.tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
