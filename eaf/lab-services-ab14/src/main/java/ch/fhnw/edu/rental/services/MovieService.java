package ch.fhnw.edu.rental.services;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.PriceCategory;

@Transactional
public interface MovieService {
	public Movie getMovieById(Long id) throws RentalServiceException;
	
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public List<Movie> getAllMovies() throws RentalServiceException;
	
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public List<Movie> getMoviesByTitle(String title) throws RentalServiceException;
	
	public void saveOrUpdateMovie(Movie movie) throws RentalServiceException;
	
	public void deleteMovie(Movie movie) throws RentalServiceException;
	
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public List<PriceCategory> getAllPriceCategories() throws RentalServiceException;
}
