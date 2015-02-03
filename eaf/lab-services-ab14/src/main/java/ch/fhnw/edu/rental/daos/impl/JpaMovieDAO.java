package ch.fhnw.edu.rental.daos.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import ch.fhnw.edu.rental.daos.MovieDAO;
import ch.fhnw.edu.rental.model.Movie;

@Component
public class JpaMovieDAO extends AbstractManagedDAO<Movie> implements MovieDAO {

	@Override
	public Movie getById(Long id) {
		return em.find(Movie.class, id);
	}

	@Override
	public List<Movie> getByTitle(String title) {
// without named query
//		List<Movie> movies = em.createQuery("from Movie m where m.title = :title")
//			.setParameter("title", title)
//			.getResultList();
		TypedQuery<Movie> q = em.createNamedQuery("movie.byTitle", Movie.class);
		q.setParameter("title", title);
		return q.getResultList();
	}

	@Override
	public void delete(Movie movie) {
		em.remove(movie);
	}

	@Override
	public List<Movie> getAll() {
		TypedQuery<Movie> q = em.createNamedQuery("movie.all", Movie.class);
		List<Movie> movies = q.getResultList();
		return movies;
	}

	@Override
	public void saveOrUpdate(Movie movie) {
		if (movie.getId() != null) {
			em.merge(movie);
		} else {
			em.persist(movie);
		}
	}

}
