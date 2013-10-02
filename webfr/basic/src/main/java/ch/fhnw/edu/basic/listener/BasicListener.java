package ch.fhnw.edu.basic.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;


public class BasicListener implements ServletContextListener {
	
	private Logger logger = Logger.getLogger(this.getClass());

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("ServletContext initialized");
	}

}
