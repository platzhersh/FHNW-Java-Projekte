package ch.fhnw.edu.basic.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

import org.apache.log4j.Logger;

public class ParameterListener implements ServletContextAttributeListener {
	
	private Logger logger = Logger.getLogger(this.getClass());

	public void attributeAdded(ServletContextAttributeEvent scab) {
		String pw = scab.getServletContext().getInitParameter("DBUSER");
		logger.info("ServletContextAttribute set: DBUSER = "+pw);
		
	}

	public void attributeRemoved(ServletContextAttributeEvent scab) {
		// TODO Auto-generated method stub
		
	}

	public void attributeReplaced(ServletContextAttributeEvent scab) {
		// TODO Auto-generated method stub
		
	}
	
	

}
