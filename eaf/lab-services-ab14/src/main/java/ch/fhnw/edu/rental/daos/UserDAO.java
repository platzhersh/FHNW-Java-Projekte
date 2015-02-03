package ch.fhnw.edu.rental.daos;

import java.util.List;

import ch.fhnw.edu.rental.model.User;

public interface UserDAO extends DAO<User> {
	public List<User> getByName(String name);
}
