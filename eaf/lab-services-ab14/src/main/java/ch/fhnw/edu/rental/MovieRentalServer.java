package ch.fhnw.edu.rental;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.fhnw.edu.rental.test.util.DbInitializer;

public class MovieRentalServer {
	private static ApplicationContext context;
	
	public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext(new String[]{"facade.xml", "business.xml", "datasource-annotation.xml"});

		DbInitializer dbinit = (DbInitializer)context.getBean("dbinit");
		try {
			dbinit.resetData(context);
			System.out.println("Database filled with initial values.");
		} catch (Exception e) {
	        System.out.println("Could not initialize database");
			e.printStackTrace();
	        System.exit(-1);
		}

        System.out.println("The MovieRentalServer is running...");
	}

}
