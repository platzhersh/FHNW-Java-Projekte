package ch.fhnw.edu.rental.test.util;

import org.springframework.context.ApplicationContext;

public interface DbInitializer {
	public void resetData(ApplicationContext context) throws Exception;
}
