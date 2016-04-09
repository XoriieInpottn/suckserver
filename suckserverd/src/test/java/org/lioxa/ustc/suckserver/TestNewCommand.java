package org.lioxa.ustc.suckserver;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.lioxa.ustc.suckserver.common.vo.Log;
import org.lioxa.ustc.suckserver.log.Loggers;

public class TestNewCommand {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String template = FileUtils.readFileToString(new File("./task.xml")); ;
		TaskController controller = new TaskController();
		Date lastTime = null;
		long tid = 0;
		System.out.println("**************");
		try {
			tid = controller.createTask(template, true).getId();
		} catch(Exception e){
			System.err.println(e.getMessage());
		}
        System.out.println(tid);
        for (int i = 0; i < 20; i++) {
        	try {
        		Thread.sleep(2000);
        	} catch (InterruptedException e) {
        }
        List<Log> logs;
//        Loggers loggers = new Loggers();
        try {
            logs = Loggers.getDefault().readLogs(tid, lastTime, 0);
        } catch (Exception e) {
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
	       // srv.stopTask(tid);
	    	controller.stopTask(tid);
	    } catch (Exception e) {
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
