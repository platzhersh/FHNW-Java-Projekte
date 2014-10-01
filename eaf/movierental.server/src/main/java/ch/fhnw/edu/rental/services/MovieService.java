package ch.fhnw.edu.rental.services;

import java.util.List;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.service.RentalServiceException;

public interface MovieService {
	public Movie getMovieById(Long id) throws RentalServiceException;
	
	public List<Movie> getAllMovies() throws RentalServiceException;
	
	public List<Movie> getMoviesByTitle(String title) throws RentalServiceException;
	
	public void saveOrUpdateMovie(Movie movie) throws RentalServiceException;
	
	public void deleteMovie(Movie movie) throws RentalServiceException;
	
	public List<PriceCategory> getAllPriceCategories() throws RentalServiceException;
}
