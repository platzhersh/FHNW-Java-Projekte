package ch.fhnw.edu.rental.daos.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ch.fhnw.edu.rental.daos.MovieDAO;
import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.Movie_;

public class JpaMovieDAO extends AbstractManagedDAO<Movie> implements MovieDAO {
	
	public JpaMovieDAO() {
		super(Movie.class);
	}

	@Override
	public List<Movie> getByTitle(String title) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
		
		// SELECT m FROM Movie m
		Root<Movie> m = cq.from(Movie.class);
		cq.select(m);
		
		// WHERE m.title = :title
		cq.where(cb.equal(m.get(Movie_.title), title));
		
		return em.createQuery(cq).getResultList();
	}

}
