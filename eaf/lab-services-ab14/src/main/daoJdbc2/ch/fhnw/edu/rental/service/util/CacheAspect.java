package ch.fhnw.edu.rental.service.util;

import java.lang.reflect.Method;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import ch.fhnw.edu.rental.daos.MovieDAO;
import ch.fhnw.edu.rental.daos.PriceCategoryDAO;
import ch.fhnw.edu.rental.daos.RentalDAO;
import ch.fhnw.edu.rental.daos.UserDAO;

@Aspect
public class CacheAspect {
	private RentalDAO rentalDAO;
	private PriceCategoryDAO priceCategoryDAO;
	private MovieDAO movieDAO;
	private UserDAO  userDAO;
	
	public void setMovieDAO(MovieDAO movieDAO) {
		this.movieDAO = movieDAO;
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	public void setRentalDAO(RentalDAO rentalDAO) {
		this.rentalDAO = rentalDAO;
	}
	public void setPriceCategoryDAO(PriceCategoryDAO priceCategoryDAO) {
		this.priceCategoryDAO = priceCategoryDAO;
	}
	
	static CacheAspect instance = null;
	public static void open() { instance.enterService(); }
	public static void close() { instance.exitService(); }
	CacheAspect(){ instance = this; }

	
	private int level = 0;
	
	@Before("execution(* *..*Service.*(..))")
	public void enterService(){
		level++;
	}

	@After("execution(* *..*Service.*(..))")
	public void exitService() {
		level--;
		if (level == 0) {
			cleanup();
		}
	}

	private void cleanup(){
		clearCache(movieDAO);
		clearCache(rentalDAO);
		clearCache(userDAO);
		clearCache(priceCategoryDAO);
	}

	private void clearCache(Object c){
		try {
			Method m = c.getClass().getDeclaredMethod("clearCache");
			m.invoke(c);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
