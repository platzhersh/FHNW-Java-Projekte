package ch.fhnw.edu.rental.services.impl.util

import ch.fhnw.edu.rental.model.Rental

class MailModelHelperGroovy implements MailModelHelper {

	
	public Map<String, Object> fillModel(Rental rental) {
		def model= [
			'user' : rental.getUser(),
			'movie' : rental.getMovie()]
        return model;
	}

}
