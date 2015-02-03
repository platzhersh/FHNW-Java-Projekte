package ch.fhnw.edu.rental.daos;

import java.util.List;

import ch.fhnw.edu.rental.model.Movie;

public interface MovieDAO extends DAO<Movie> {
	public List<Movie> getByTitle(String name);
}
