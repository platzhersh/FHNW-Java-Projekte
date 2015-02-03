package ch.fhnw.edu.rental.test.util;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class MemoryDbInitializer implements DbInitializer {

	@Autowired
	private ApplicationContext context;

	@Override
	public void resetData() {
		String[] daos = new String[]{"priceCategoryDAO", "userDAO", "movieDAO", "rentalDAO"};
			// order in which the DAOs are initialized is relevant
		for(String bean : daos){
			Object dao = context.getBean(bean);
			Method method;
			try {
				method = dao.getClass().getDeclaredMethod("initData");
				method.setAccessible(true);
				method.invoke(dao);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void initData() {
		resetData();
	}

}
