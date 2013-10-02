package ch.fhnw.edu.basic.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;


public class BasicFilter implements Filter {
	
	private Logger logger = Logger.getLogger(this.getClass());

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		logger.info("doFilter() called, before chain.doFilter()");
		chain.doFilter(request, response);
		logger.info("doFilter() called, after chain.doFilter()");
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
