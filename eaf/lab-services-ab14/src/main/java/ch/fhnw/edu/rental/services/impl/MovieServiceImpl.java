package ch.fhnw.edu.rental.services.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.fhnw.edu.rental.daos.MovieDAO;
import ch.fhnw.edu.rental.daos.PriceCategoryDAO;
import ch.fhnw.edu.rental.daos.impl.ManagedDAO;
import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.services.MovieService;
import ch.fhnw.edu.rental.services.RentalServiceException;

@Service
public class MovieServiceImpl implements MovieService {
	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private MovieDAO movieDAO;
	@Autowired
	private PriceCategoryDAO priceCategoryDAO;

	public Movie getMovieById(Long id) throws RentalServiceException {
		return movieDAO.getById(id);
	}

	public List<Movie> getAllMovies() throws RentalServiceException {
		List<Movie> movies = movieDAO.getAll();
		if (log.isDebugEnabled()) {
			log.debug("getAllMovies() done");
		}
		return movies;
	}

	public List<Movie> getMoviesByTitle(String title) throws RentalServiceException {
		return movieDAO.getByTitle(title);
	}

	public void saveOrUpdateMovie(Movie movie) throws RentalServiceException {
		if (movie == null) {
			throw new RentalServiceException("'movie' parameter is not set!");
		}
		movieDAO.saveOrUpdate(movie);
		if (log.isDebugEnabled()) {
			log.debug("saved or updated movie[" + movie.getId() + "]");
		}
	}

	@SuppressWarnings("unchecked")
	public void deleteMovie(Movie movie) throws RentalServiceException {
		if (movie == null) {
			throw new RentalServiceException("'movie' parameter is not set!");
		}
		if (movie.isRented()) {
			throw new RentalServiceException("movie is still used");
		}

		if(movieDAO instanceof ManagedDAO<?>) {
			movie = ((ManagedDAO<Movie>)movieDAO).manage(movie);
		}

		movieDAO.delete(movie);
		
		if (log.isDebugEnabled()) {
			log.debug("movie[" + movie.getId() + "] deleted");
		}
	}

	public List<PriceCategory> getAllPriceCategories() throws RentalServiceException {
		return priceCategoryDAO.getAll();
	}

}
