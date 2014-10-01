package ch.fhnw.edu.rental.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.model.PriceCategoryNewRelease;
import ch.fhnw.edu.rental.model.PriceCategoryRegular;
import ch.fhnw.edu.rental.service.RentalServiceException;
import ch.fhnw.edu.rental.services.MovieService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/business.xml", "/datasource.xml"})
@Transactional
public class MovieServiceTest {
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private MovieService movieService;
	
	private Calendar currentDate;
	
	@Before
	public void setUp() {
		currentDate = Calendar.getInstance();
	    // Zero out the hour, minute, second, and millisecond
	    currentDate.set(Calendar.HOUR_OF_DAY, 0);
	    currentDate.set(Calendar.MINUTE, 0);
	    currentDate.set(Calendar.SECOND, 0);
	    currentDate.set(Calendar.MILLISECOND, 0);
	}
	
	@Test
	public void testChangePriceCategory() {
		Movie movie = movieService.getMovieById(new Long(1));
		assertEquals(movie.getTitle(), "Lord of the Rings");

		PriceCategory priceCategory = movie.getPriceCategory();
		assertTrue(priceCategory instanceof PriceCategoryRegular);

		List<PriceCategory> categories = movieService.getAllPriceCategories();
		assertEquals(categories.size(), 3);

		PriceCategory category = categories.get(2);
		assertTrue(category instanceof PriceCategoryNewRelease);

		movie.setPriceCategory(category);
		movieService.saveOrUpdateMovie(movie);

		Movie updatedMovie = movieService.getMovieById(new Long(1));
		assertEquals(updatedMovie.getTitle(), "Lord of the Rings");

		PriceCategory updatePriceCategory = movie.getPriceCategory();
		assertTrue(updatePriceCategory instanceof PriceCategoryNewRelease);
	}

	@Test
	public void testCreateMovie() {
		List<PriceCategory> categories = movieService.getAllPriceCategories();
		assertEquals(3, categories.size());

		List<Movie> movies = movieService.getAllMovies();
		assertEquals(5, movies.size());

		PriceCategory category = categories.get(2);
		assertTrue(category instanceof PriceCategoryNewRelease);

		Movie movie = new Movie("testMovie", currentDate.getTime(), category);
		movieService.saveOrUpdateMovie(movie);

		movies = movieService.getAllMovies();
		assertEquals(6, movies.size());
	}

	@Test
	public void testCreateMovie2() {
		List<PriceCategory> categories = movieService.getAllPriceCategories();
		assertEquals(3, categories.size());

		PriceCategory category = categories.get(1);
		Movie movie = new Movie("testMovie", currentDate.getTime(), category);

		assertTrue(movie.getId() == null);
		movieService.saveOrUpdateMovie(movie);
		assertTrue(movie.getId() != null);

		Movie movie2 = movieService.getMovieById(movie.getId());
		assertTrue(movie.getReleaseDate().equals(movie2.getReleaseDate()));
		assertTrue(movie.equals(movie2));
	}


	@Test
	public void testDeleteMovie() {
		List<Movie> movies = movieService.getAllMovies();
		assertEquals(5, movies.size());
		
		Movie movie = null;
		for(Movie m : movies){
			if(m.getTitle().equals("Batman")){
				movie = m;
			}
		}
		assertTrue("Batman not found", movie != null);

		movieService.deleteMovie(movie);

		movies = movieService.getAllMovies();
		assertEquals(4, movies.size());
	}
	
	@Test(expected=RentalServiceException.class)
	public void testDeleteRentedMovie() {
		Movie movie = movieService.getMovieById(1L);
		assertTrue(movie.isRented());
		movieService.deleteMovie(movie);
	}

	@Test(expected = RentalServiceException.class)
	public void testDeleteMovieUsedByRental() {
		List<Movie> movies = movieService.getAllMovies();
		assertEquals(5, movies.size());

		Movie movie = movies.get(0);
		assertEquals("Lord of the Rings", movie.getTitle());
		// should throw ServiceException as movie is still rented
		movieService.deleteMovie(movie);
	}
	
	@Test
	public void testGetByTitle() {
		List<Movie> movies = movieService.getAllMovies();
		Movie m = movies.get(0);
		
		movies = movieService.getMoviesByTitle(m.getTitle());
		assertTrue("result must contain movie m", movies.size() > 0);
		assertTrue("result must contain movie m", movies.contains(m));
	}

	@Test
	public void testDeleteAndInsertMovie() {
		List<Movie> movies = movieService.getAllMovies();
		assertEquals(5, movies.size());

		Movie movie = movies.get(4);

		movieService.deleteMovie(movie);

		movies = movieService.getAllMovies();
		assertEquals(4, movies.size());
		
		movieService.saveOrUpdateMovie(movie);

		movies = movieService.getAllMovies();
		assertEquals(5, movies.size());
	}

//	@Test
//	public void changeMovie() {
//		List<Movie> movies = movieService.getAllMovies();
//		assertEquals(5, movies.size());
//		Movie m1 = movies.get(1);
//		Movie m2 = new Movie("####", m1.getReleaseDate(), m1.getPriceCategory());
//		m2.setId(m1.getId());
//		m2.setRented(m1.isRented());
//		movieService.saveOrUpdateMovie(m2);
//		
//		assertEquals(m1.getTitle(), m2.getTitle());
//	}

}
