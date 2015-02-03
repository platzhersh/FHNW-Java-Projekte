package ch.fhnw.edu.rental.model;

import java.util.Calendar;
import java.util.Date;


public class Rental {
	private Long id;
	
	private Movie movie;
	private User user;
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

	protected void setMovie(Movie movie) {
		this.movie = movie;
	}

	protected void setUser(User user) {
		this.user = user;
	}

	protected void setRentalDate(Date rentalDate) {
		this.rentalDate = rentalDate;
	}

	protected void setRentalDays(int rentalDays) {
		this.rentalDays = rentalDays;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rental other = (Rental) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
