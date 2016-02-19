package org.lioxa.ustc.suckserver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.lioxa.ustc.suckserver.common.SuckService;
import org.lioxa.ustc.suckserver.common.vo.Log;

/**
 *
 * @author xi
 * @since Dec 27, 2015
 */
public class TestBasicService {

    public static void main(String[] args) {
        SuckService srv;
        try {
            srv = (SuckService) Naming.lookup("rmi://localhost:38324/SuckService");
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            System.err.println(getMessages(e));
            return;
        }
        String template;
        try {
            template = FileUtils.readFileToString(new File("./templates/task.xml"));
        } catch (IOException e) {
            System.err.println(getMessages(e));
            return;
        }
        Date lastTime = null;
        long tid;
        try {
            tid = srv.createTask(template, true);
        } catch (RemoteException e) {
            System.err.println(getMessages(e));
            return;
        }
        System.out.println(tid);
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            List<Log> logs;
            try {
                logs = srv.getLogs(tid, lastTime, 0);
            } catch (RemoteException e) {
                System.err.println(getMessages(e));
                return;
            }
            if (logs.isEmpty()) {
                continue;
            }
            for (Log log : logs) {
                if (log.getType() == Log.TYPE_INFO) {
                    System.out.println(log.getContent());
                } else {
                    System.err.println(log.getContent());
                }
            }
            lastTime = logs.get(logs.size() - 1).getTime();
        }
        try {
            srv.stopTask(tid);
        } catch (RemoteException e) {
            System.err.println(getMessages(e));
            return;
        }
    }

    static String getMessages(Throwable e) {
        StringBuilder sb = new StringBuilder();
        for (int lvl = 0; e != null; e = e.getCause(), lvl++) {
            if (e instanceof RemoteException) {
                lvl--;
                continue;
            }
            String msg = e.getMessage();
            if (msg == null) {
                continue;
            }
            if (lvl > 0) {
                sb.append("|---");
            }
            for (int i = 1; i < lvl; i++) {
                sb.append("----");
            }
            sb.append(msg);
            sb.append(" Caused by:\n");
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - " Caused by:\n".length(), sb.length());
        }
        return sb.toString();
    }

}
