package ch.fhnw.edu.rental.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "rentals")
@NamedQueries({
	@NamedQuery(name="rental.all", query="SELECT r FROM Rental r"),
	@NamedQuery(name="rental.byUser", query="SELECT r FROM Rental r WHERE r.user = :user")
})
@SuppressWarnings("serial")
public class Rental implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "rental_id")
	private Long id;
	// FIXME movie must not be loaded lazy, otherwise visitor in BusinessLogicLocal has problems
	@OneToOne//(fetch=FetchType.LAZY)
	@JoinColumn(name = "movie_id", nullable = false)
	private Movie movie;
	
	// FIXME user must not be loaded lazy, otherwise visitor in BusinessLogicLocal has problems
	@ManyToOne//(fetch=FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(name = "rental_rentalDate")
	private Date rentalDate;
	
	@Column(name = "rental_rentalDays")
	private int rentalDays;
	
	Rental() { 
		// JPA requires that a default constructor is available
	}
	
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
}
