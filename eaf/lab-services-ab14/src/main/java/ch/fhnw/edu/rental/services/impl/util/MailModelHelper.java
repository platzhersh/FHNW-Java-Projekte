package ch.fhnw.edu.rental.services.impl.util;

import java.util.Map;

import ch.fhnw.edu.rental.model.Rental;

public interface MailModelHelper {
	/**
	 * Create Model according to the MVC Design Pattern, based on given entity {@link Rental}.
	 * 
	 * @param rental the entity
	 *  
	 * @return model as <key,value> map.
	 */
	public Map<String, Object> fillModel(Rental rental);
}
