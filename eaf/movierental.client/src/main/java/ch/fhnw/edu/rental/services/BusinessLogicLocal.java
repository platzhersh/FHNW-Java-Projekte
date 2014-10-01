package ch.fhnw.edu.rental.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;

public class BusinessLogicLocal implements BusinessLogic {
	private static ApplicationContext context;

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RentalService rentalService;


	public BusinessLogicLocal() throws Exception {
        context = new ClassPathXmlApplicationContext(new String[]{"applicationLocal.xml", "business.xml", "datasource.xml"});

		movieService = (MovieService)context.getBean("movieService");
		userService = (UserService)context.getBean("userService");
		rentalService = (RentalService)context.getBean("rentalService");
	}

	public String getUserLastName(Long id){
		return getUser(id).getLastName();
	}
	public String getUserFirstName(Long id){
		return getUser(id).getFirstName();
	}
	public int getUserRentalsSize(Long id){
		return getUser(id).getRentals().size();
	}
	
	public String getMovieTitle(Long id){
		return getMovie(id).getTitle();
	}
	public String getMoviePriceCategory(Long id){
		return getMovie(id).getPriceCategory().toString();
	}
	public Date getMovieReleaseDate(Long id){
		return getMovie(id).getReleaseDate();
	}
	public boolean getMovieIsRented(Long id){
		return getMovie(id).isRented();
	}
	
	
	private User getUser(Long id){
		return userService.getUserById(id);
	}
	
	private Movie getMovie(Long id){
		return movieService.getMovieById(id);
	}
	
	private Rental getRental(Long id){
		return rentalService.getRentalById(id);
	}
	
	
	public void removeRental(Long rentalId){
		rentalService.deleteRental(getRental(rentalId));
	}
	
	public void deleteUser(Long userId) {
		userService.deleteUser(getUser(userId));
	}

	public void updateUser(Long userId, String lastName, String firstName) {
		// only called when user is updated
		User user = new User(lastName, firstName);
		user.setId(userId);
		userService.saveOrUpdateUser(user);
	}

	public Long createUser(String lastName, String firstName) {
		User user = new User(lastName, firstName);
		userService.saveOrUpdateUser(user);
		return user.getId();
	}
	
	public void deleteMovie(Long movieId) {
		movieService.deleteMovie(getMovie(movieId));
	}

	public Long createMovie(String movieTitle, Date date, String category) {
		Movie movie = null;
		for(PriceCategory pc : movieService.getAllPriceCategories()){
			if(pc.toString().equals(category)){
				movie = new Movie(movieTitle, date, pc);
			}
		}
		movieService.saveOrUpdateMovie(movie);
		return movie.getId();
	}

	public void updateMovie(Long movieId, String movieTitle, Date date,	String category) {
		// only called when movie is updated
		Movie movie = null;
		Movie orig = movieService.getMovieById(movieId);
		for(PriceCategory pc : movieService.getAllPriceCategories()){
			if(pc.toString().equals(category)){
				movie = new Movie(movieTitle, date, pc);
			}
		}
		movie.setId(movieId);
		movie.setRented(orig.isRented());
		movieService.saveOrUpdateMovie(movie);
	}

	public void createRental(Long movieId, Long userId, Integer rentalDays) {
		Movie movie = getMovie(movieId);
		User user = getUser(userId);
		userService.rentMovie(user, movie, rentalDays);
	}
	
	public void visitUsers(UserVisitor visitor) {
		for(User u : userService.getAllUsers()){
			visitor.visit(u.getId(), u.getLastName(), u.getFirstName());
		}
	}

	public void visitMovies(MovieVisitor visitor) {
		for(Movie m : movieService.getAllMovies()){
			visitor.visit(m.getId(), m.getTitle(), m.getReleaseDate(), m.isRented(), m.getPriceCategory().toString());
		}
	}

	public void visitRentals(RentalVisitor visitor) {
		for(Rental r : rentalService.getAllRentals()){
			User user = r.getUser();
			Movie movie = r.getMovie();
			
			Date now = Calendar.getInstance().getTime();
			int remainingDays = r.calcRemainingDaysOfRental(now);
						
			visitor.visit(r.getId(), r.getRentalDays(), r.getRentalDate(), user.getLastName(), user.getFirstName(), movie.getTitle(), remainingDays, r.getRentalFee());
		}
	}

	public void visitRentalsOfUser(Long userId, RentalVisitor visitor) {
		User user = getUser(userId);
		for(Rental r : user.getRentals()){
			Date now = Calendar.getInstance().getTime();
			int remainingDays = r.calcRemainingDaysOfRental(now);
			visitor.visit(r.getId(), r.getRentalDays(), r.getRentalDate(), user.getLastName(), user.getFirstName(), r.getMovie().getTitle(), remainingDays, r.getRentalFee());
		}
	}

	
}
