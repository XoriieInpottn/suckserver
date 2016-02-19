package org.lioxa.ustc.suckserver.utils;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.lioxa.ustc.suckserver.common.SuckService;

/**
 *
 * @author xi
 * @since Jul 13, 2014
 */
public class Utils {

    static SessionFactory sessionFactory = null;

    public static synchronized Session getDBSession() {
        if (sessionFactory == null) {
            final Configuration config = new Configuration().configure();
            final StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
            builder.applySettings(config.getProperties());
            final StandardServiceRegistry stdServiceReg = builder.build();
            sessionFactory = config.buildSessionFactory(stdServiceReg);
        }
        return sessionFactory.openSession();
    }

    public static synchronized SuckService getSuckService() {
        try {
            return (SuckService) Naming.lookup("rmi://localhost:38324/SuckService");
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            String msg = String.format("Failed to get SuckService: %s", e.getMessage());
            throw new RuntimeException(msg, e);
        }
    }

}
