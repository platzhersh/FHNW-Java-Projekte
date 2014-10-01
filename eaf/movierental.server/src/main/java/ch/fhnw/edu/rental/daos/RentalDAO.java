package ch.fhnw.edu.rental.daos;

import java.util.List;

import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;

public interface RentalDAO extends DAO<Rental> {
	public List<Rental> getRentalsByUser(User user);
}
