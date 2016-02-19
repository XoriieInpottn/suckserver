package org.lioxa.ustc.suckserver;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import org.lioxa.ustc.suckserver.common.SuckService;
import org.lioxa.ustc.suckserver.common.vo.Task;
import org.lioxa.ustc.suckserver.common.vo.TaskStat;

/**
 *
 * @author xi
 * @since Dec 27, 2015
 */
public class TestGetTasks {

    public static void main(String[] args) {
        SuckService srv;
        try {
            srv = (SuckService) Naming.lookup("rmi://localhost:38324/SuckService");
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            System.err.println(getMessages(e));
            return;
        }
        Object[] tuple;
        try {
            tuple = srv.getTasks(0, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
            return;
        }
        @SuppressWarnings("unchecked")
        List<Object[]> lst = (List<Object[]>) tuple[0];
        long totalCount = (long) tuple[1];
        System.out.printf("total count: %d\n", totalCount);
        for (Object[] tuple1 : lst) {
            Task task = (Task) tuple1[0];
            TaskStat taskStat = (TaskStat) tuple1[1];
            System.out.printf("task name: %s\n", task.getName());
            System.out.printf("status: %s\n", task.getStatus());
            System.out.printf("start time: %s\n", task.getStartTime());
            System.out.printf("end time: %s\n", task.getEndTime());
            if (taskStat != null) {
                System.out.printf("success: %d\n", taskStat.getSuccessCount());
                System.out.printf("error: %d\n", taskStat.getErrorCount());
            }
        }
    }

    static String getMessages(Throwable e) {
        StringBuilder sb = new StringBuilder();
        for (int lvl = 0; e != null; e = e.getCause(), lvl++) {
            String msg = e.getMessage();
            if (msg == null) {
                continue;
            }
            for (int i = 0; i < lvl; i++) {
                sb.append("    ");
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
