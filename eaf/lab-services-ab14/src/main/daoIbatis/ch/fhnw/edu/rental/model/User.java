package ch.fhnw.edu.rental.model;

import java.util.ArrayList;
import java.util.List;


public class User {
	private Long id;
	
	private String lastName;
	
	private String firstName;
	
	private String email;
	
	private List<Rental> rentals;
	
	private List<Role> roles;
	
	public User() {
		// public default constructor required by Ibatis
	}
	
	public User(String lastName, String firstName) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.rentals = new ArrayList<Rental>();
		this.roles = new ArrayList<Role>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String name) {// changed from protected to public due to ibatis
		this.lastName = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) { // changed from protected to public due to ibatis
		this.firstName = firstName;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	@Deprecated
	public void rentMovie(Movie movie, int days) {// TODO move into service
		if (movie == null) {
			throw new NullPointerException("parameter 'movie' is null!");
		}
		if (days < 1) {
			throw new IllegalArgumentException("parameter 'days' must be > 0");
		}
		Rental rental = new Rental(this, movie, days);
		rentals.add(rental);
		
//		userService.saveOrUpdateUser(this); // FIXME Rental 2
	}
	
	@Deprecated	
	public void removeRentalOfMovie(Movie movie) { // TODO move into service
		Rental rentalToRemove = null;
		if (movie == null) {
			throw new NullPointerException("parameter 'movie' is null!");
		}
		for (Rental rental : rentals) {
			if (rental.getMovie().equals(movie)) {
				rentalToRemove = rental;
				break;
			}
		}
		if (rentalToRemove != null) {
			rentals.remove(rentalToRemove);
//			movie.setRented(false);
		}
	}
	
	public int getCharge() {
		int result = 0;
		for (Rental rental : rentals) {
			result += rental.getMovie().getPriceCategory().getCharge(rental.getRentalDays());
		}
		return result;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
		
}
