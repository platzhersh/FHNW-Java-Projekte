package ch.fhnw.edu.rental.model;

import java.util.Calendar;
import java.util.Date;

public class Rental {
	private Long id;
	
	private final Movie movie;
	private final User user;
	private Date rentalDate;
	private int rentalDays;
	
	public Rental(User user, Movie movie, int rentalDays) {
		if ((user == null) || (movie == null) || (rentalDays <= 0)) {
			throw new NullPointerException("not all input parameters are set!");
		}
		if (movie.isRented()) {
			throw new IllegalStateException("movie is already rented!");
		}
		this.user = user;
		user.getRentals().add(this);
		this.movie = movie;
		movie.setRented(true);
		this.rentalDays = rentalDays;
		this.rentalDate = Calendar.getInstance().getTime();
	}
	
	public int calcRemainingDaysOfRental(Date date) {
		if (date == null) {
			throw new NullPointerException("given date is not set!");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(rentalDate);
		calendar.add(Calendar.DAY_OF_YEAR, rentalDays);
		int endDay = calendar.get(Calendar.DAY_OF_YEAR);
		int endYear = calendar.get(Calendar.YEAR);
		calendar.setTime(date);
		int max = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
		int actDay = calendar.get(Calendar.DAY_OF_YEAR);
		int actYear = calendar.get(Calendar.YEAR);
		int diffDay = endDay - actDay;
		if (max!=365) {
			return 366*(endYear-actYear) + diffDay;
		} else {
			return 365*(endYear-actYear) + diffDay;
		}
	}

	public double getRentalFee() {
		return movie.getPriceCategory().getCharge(rentalDays);
	}

	public Long getId() {
		return id;
	}

	public Movie getMovie() {
		return movie;
	}

	public User getUser() {
		return user;
	}

	public Date getRentalDate() {
		return rentalDate;
	}

	public int getRentalDays() {
		return rentalDays;
	}

	public void setId(Long id) {
		this.id = id;
	}	

	public void setRentalDate(Date rentalDate) {
		this.rentalDate = rentalDate;
	}

	protected void setRentalDays(int rentalDays) {
		this.rentalDays = rentalDays;
	}
}
