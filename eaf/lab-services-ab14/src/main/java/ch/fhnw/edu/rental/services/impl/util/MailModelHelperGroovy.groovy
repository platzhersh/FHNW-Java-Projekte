package ch.fhnw.edu.rental.services.impl.util

import ch.fhnw.edu.rental.model.Rental

class MailModelHelperGroovy implements MailModelHelper {

	
	public Map<String, Object> fillModel(Rental rental) {
		Map<String, Object> model = new HashMap<String, Object>()
		model.put("user", rental.getUser())
		model.put("movie", rental.getMovie())
		return model
	}

}
