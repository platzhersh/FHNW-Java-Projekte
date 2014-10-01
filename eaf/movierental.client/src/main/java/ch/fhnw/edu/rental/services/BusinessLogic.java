package ch.fhnw.edu.rental.services;

import java.util.Date;

public interface BusinessLogic {
	
	// Users
	public void deleteUser(Long userId);
	public void updateUser(Long userId, String lastName, String firstName);
	public Long createUser(String lastName, String firstName);

	public String getUserLastName(Long id);
	public String getUserFirstName(Long id);
	public int getUserRentalsSize(Long id);
	
	// Movies
	public Long createMovie(String movieTitle, Date date, String category);
	public void updateMovie(Long movieId, String movieTitle, Date date,	String category);
	public void deleteMovie(Long movieId);

	public String getMovieTitle(Long id);
	public String getMoviePriceCategory(Long id);
	public Date getMovieReleaseDate(Long id);
	public boolean getMovieIsRented(Long id);
	
	// Rentals
	public void createRental(Long movieId, Long userId, Integer rentalDays);
	public void removeRental(Long rentalId);


	public interface UserVisitor {
		public void visit(Long id, String lastName, String firstName);
	}

	public void visitUsers(UserVisitor visitor);
	
	public interface MovieVisitor {
		public void visit(Long id, String title, Date releaseDate, boolean isRented, String priceCategory);
	}

	public void visitMovies(MovieVisitor visitor);

	public interface RentalVisitor {
		public void visit(Long id, int rentalDays, Date rentalDate, String lastName, String firstName, String movieTitle, int remainingDays, double rentalFee);
	}

	public void visitRentals(RentalVisitor visitor);
	public void visitRentalsOfUser(Long userId, RentalVisitor visitor);

}
