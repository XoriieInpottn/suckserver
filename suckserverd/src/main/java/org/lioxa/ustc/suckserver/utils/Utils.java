package org.lioxa.ustc.suckserver.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.lioxa.ustc.suckserver.Main;
import org.lioxa.ustc.suckserver.common.vo.Log;
import org.lioxa.ustc.suckserver.common.vo.Task;

/**
 *
 * @author xi
 * @since Dec 23, 2015
 */
public class Utils {

    public static Object toObject(Class<?> clazz, String value) {
        if (String.class.isAssignableFrom(clazz)) {
            return value;
        } else if (Boolean.class.isAssignableFrom(clazz) || boolean.class.isAssignableFrom(clazz)) {
            return Boolean.parseBoolean(value);
        } else if (Byte.class.isAssignableFrom(clazz) || byte.class.isAssignableFrom(clazz)) {
            return Byte.parseByte(value);
        } else if (Short.class.isAssignableFrom(clazz) || short.class.isAssignableFrom(clazz)) {
            return Short.parseShort(value);
        } else if (Integer.class.isAssignableFrom(clazz) || int.class.isAssignableFrom(clazz)) {
            return Integer.parseInt(value);
        } else if (Long.class.isAssignableFrom(clazz) || long.class.isAssignableFrom(clazz)) {
            return Long.parseLong(value);
        } else if (Float.class.isAssignableFrom(clazz) || float.class.isAssignableFrom(clazz)) {
            return Float.parseFloat(value);
        } else if (Double.class.isAssignableFrom(clazz) || double.class.isAssignableFrom(clazz)) {
            return Double.parseDouble(value);
        } else {
            return value;
        }
    }

    static SessionFactory sessionFactory;

    public synchronized static Session getDBSession() {
        if (sessionFactory == null) {
            Configuration conf = new Configuration();
            //
            // driver and URL
            String dbType = Main.conf.getString("db.type");
            String dbHost = Main.conf.getString("db.host");
            String dbPort = Main.conf.getString("db.port");
            String dbName = Main.conf.getString("db.name");
            String dbDriver;
            String dbURL;
            String dbDialect;
            switch (Main.conf.getString("db.type")) {
            case "postgresql":
                dbDriver = "org.postgresql.Driver";
                if (dbPort == null) {
                    dbPort = "5432";
                }
                dbURL = String.format("jdbc:%s://%s:%s/%s", dbType, dbHost, dbPort, dbName);
                dbDialect = "org.hibernate.dialect.PostgreSQL9Dialect";
                break;
            case "mysql":
                dbDriver = "com.mysql.jdbc.Driver";
                if (dbPort == null) {
                    dbPort = "3306";
                }
                dbURL = String.format("jdbc:%s://%s:%s/%s", dbType, dbHost, dbPort, dbName);
                dbDialect = "org.hibernate.dialect.MySQL5Dialect";
                break;
            default:
                String err = String.format("FATAL: Unsupported database type \"%s\".", dbType);
                throw new RuntimeException(err);
            }
            //
            // set properties
            conf.setProperty("hibernate.connection.driver_class", dbDriver);
            conf.setProperty("hibernate.connection.url", dbURL);
            conf.setProperty("hibernate.dialect", dbDialect);
            conf.setProperty("hibernate.connection.username", Main.conf.getString("db.user"));
            conf.setProperty("hibernate.connection.password", Main.conf.getString("db.passwd"));
            conf.setProperty("hibernate.c3p0.min_size", Main.conf.getString("db.min-pool-size"));
            conf.setProperty("hibernate.c3p0.max_size", Main.conf.getString("db.max-pool-size"));
            conf.setProperty("hibernate.c3p0.timeout", Main.conf.getString("db.timeout"));
            conf.setProperty("hibernate.c3p0.max_statements", Main.conf.getString("db.max-statements"));
            conf.setProperty("hibernate.c3p0.idle_test_period", Main.conf.getString("db.idle-test-period"));
            conf.setProperty("hibernate.current_session_context_class", "thread");
            conf.setProperty("hibernate.hbm2ddl.auto", "update");
            conf.setProperty("hibernate.show_sql", "false");
            conf.addClass(Task.class);
            conf.addClass(Log.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
            builder.applySettings(conf.getProperties());
            sessionFactory = conf.buildSessionFactory(builder.build());
        }
        return sessionFactory.openSession();
    }

}
