package ch.fhnw.edu.rental.facade;

import org.dozer.DozerConverter;
import org.springframework.beans.factory.annotation.Autowired;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.services.MovieService;

public class MovieConverter extends DozerConverter<Movie, Long>  {
	@Autowired
	private MovieService movieService;

	public MovieConverter() {
		super(Movie.class, Long.class);
	}

	@Override
	public Movie convertFrom(Long id, Movie movie) {
		return movieService.getMovieById(id);
	}

	@Override
	public Long convertTo(Movie movie, Long id) {
		return movie.getId();
	}
	
//	@Override
//	public Object convert(Object destination, Object source,
//			Class<?> destinationClass, Class<?> sourceClass) {
//		if (source == null) {
//			return null;
//		} else if (source instanceof Movie) {
//			return ((Movie) source).getId();
//		} else if (source instanceof Long) {
//			throw new IllegalArgumentException();
//			// bzw. DAO.getById(source);
//		} else {
//			throw new IllegalArgumentException();
//		}
//	}

}
