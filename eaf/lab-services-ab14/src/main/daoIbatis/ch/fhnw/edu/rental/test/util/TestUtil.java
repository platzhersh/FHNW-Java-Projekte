package ch.fhnw.edu.rental.test.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;


public class TestUtil {

	private static String[] DEFAULT_CONTEXT = {"business.xml", "datasource.xml"};
	
	static ApplicationContext context = null;
	
	public static ApplicationContext getSpringContext() throws Exception {
		context = new ClassPathXmlApplicationContext(DEFAULT_CONTEXT);
		DbInitializer dbinit = (DbInitializer)context.getBean("dbinit");
		dbinit.resetData(context);
		return context;
	}
	
	public static ApplicationContext getSpringContext(String[] contexts) throws Exception {
		context = new ClassPathXmlApplicationContext(contexts);
		DbInitializer dbinit = (DbInitializer)context.getBean("dbinit");
		dbinit.resetData(context);
		return context;
	}
	
	public static void setSupportForLazyLoading() {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			TransactionSynchronizationManager.bindResource(getEntityManagerFactory(), new EntityManagerHolder(em));
		} catch (PersistenceException ex) {
			throw new DataAccessResourceFailureException("Could not create JPA EntityManager", ex);
		}
	}

	public static void closeSupportForLazyLoading() {
		EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager
				.unbindResource(getEntityManagerFactory());
		emHolder.getEntityManager().close();
	}

	private static EntityManagerFactory getEntityManagerFactory() {
		EntityManagerFactory emf = (EntityManagerFactory) context.getBean("entityManagerFactory");
		return emf;
	}	
}
