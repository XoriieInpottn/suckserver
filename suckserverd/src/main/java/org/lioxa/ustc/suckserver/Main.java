package org.lioxa.ustc.suckserver;

import java.io.File;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

/**
 *
 * @author xi
 * @since Nov 20, 2015
 */
public class Main {

    /**
     * The default path of the configuration file.<br/>
     * In a *nux system, the default path is ./suckserverd.xml.
     */
    public static final String CONF_FILE = "suckserverd.xml";
    public static final CompositeConfiguration conf = new CompositeConfiguration();

    public static void main(String[] args) {
        try {
            parseCommandLine(buildOptions(), args);
            loadConfiguration(new File(CONF_FILE));
            //
            // start daemon
            String host = conf.getString("listen-host");
            int port = conf.getInt("listen-port");
            String url = String.format("rmi://%s:%d/SuckService", host, port);
            LocateRegistry.createRegistry(port);
            Naming.rebind(url, new SuckServiceImpl());
            System.out.printf("Listening %s ...\n", url);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    static Options buildOptions() {
        Options opts = new Options();
        opts.addOption("p", "port", true, "Port that suckserverd will listen.");
        opts.addOption("h", "help", false, "Show this help.");
        return opts;
    }

    static void parseCommandLine(Options opts, String[] args) throws ParseException {
        //
        // parse command line
        CommandLine cmd;
        try {
            cmd = new DefaultParser().parse(opts, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
            return;
        }
        //
        // print help information
        if (cmd.hasOption('h')) {
            new HelpFormatter().printHelp("suckserverd", opts, true);
            System.exit(0);
            return;
        }
        //
        // add configuration
        PropertiesConfiguration conf1 = new PropertiesConfiguration();
        conf.addConfiguration(conf1);
        //
        // set port
        String portStr = cmd.getOptionValue('p');
        if (portStr != null) {
            try {
                conf1.setProperty("listen-port", Integer.parseInt(portStr));
            } catch (NumberFormatException e) {
                System.err.printf("Invalid port \"%s\".", portStr);
                System.exit(-1);
                return;
            }
        }
    }

    static void loadConfiguration(File file) {
        if (file.exists()) {
            try {
                conf.addConfiguration(new XMLConfiguration(file));
            } catch (ConfigurationException e) {
                System.err.println(e.getMessage());
                System.exit(-1);
                return;
            }
        }
        PropertiesConfiguration conf1 = new PropertiesConfiguration();
        conf.addConfiguration(conf1);
        conf1.setProperty("db.type", "postgresql");
        conf1.setProperty("db.host", "localhost");
        conf1.setProperty("db.name", "suckserver");
        conf1.setProperty("db.user", "postgres");
        conf1.setProperty("db.passwd", "postgres");
        conf1.setProperty("db.init-pool-size", "2");
        conf1.setProperty("db.min-pool-size", "1");
        conf1.setProperty("db.max-pool-size", "10");
        conf1.setProperty("db.timeout", "300");
        conf1.setProperty("db.max-statements", "50");
        conf1.setProperty("db.idle-test-period", "3000");
        conf1.setProperty("retry-count", 10);
        conf1.setProperty("retry-delay", 3000);
        conf1.setProperty("listen-host", "localhost");
        conf1.setProperty("listen-port", 38324);
    }

}
