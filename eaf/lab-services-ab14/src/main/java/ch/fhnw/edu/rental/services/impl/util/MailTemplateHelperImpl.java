package ch.fhnw.edu.rental.services.impl.util;

import java.util.Map;

import org.springframework.stereotype.Component;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.User;

@Component(value = "javaMailTemplateHelper")
public class MailTemplateHelperImpl implements MailTemplateHelper {

	@Override
	public String mergeTemplate(Map<String, Object> model, String template) {
		User user = (User) model.get("user");
		String username = user.getLastName() + " " + user.getFirstName();
		Movie movie = (Movie) model.get("movie");
		return String.format(template, username, movie.getTitle(), model.get("price"));
	}

}
