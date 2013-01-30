package ch.fhnw.edu.rental;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * MRS Logger.
 * 
 * @author Michael Kuenzli <michael.kuenzli@students.fhnw.ch>
 */
public final class Logging {
    
    /**
     * Private default constructor to prevent creating instances of this class.
     */
    private Logging() {
        logger = Logger.getRootLogger();
        Properties props = new Properties();
        try {
            props.load(this.getClass().getResourceAsStream("log4j.xml"));
        } catch (Exception e) {
            System.out.println("could not load log4j-configuration: " + e.getMessage());
        }
        PropertyConfigurator.configure(props);
    } 

    private static Logger logger;

    /**
     * Returns instance of logger.
     * 
     * @return Logger instance
     */
    public static Logger getLogger() {
        if (logger == null) {
            new Logging();
        }
        return logger;
    }

}
