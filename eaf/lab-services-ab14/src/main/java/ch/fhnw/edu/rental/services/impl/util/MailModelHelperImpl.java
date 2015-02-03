package ch.fhnw.edu.rental.services.impl.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import ch.fhnw.edu.rental.model.Rental;

@Component(value = "mailModelHelper")
public class MailModelHelperImpl implements MailModelHelper {

	@Override
	public Map<String, Object> fillModel(Rental rental) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", rental.getUser());
		model.put("movie", rental.getMovie());
		return model;
	}

}
