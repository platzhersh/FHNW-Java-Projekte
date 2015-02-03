package ch.fhnw.edu.rental.dto;

import java.io.Serializable;
import java.util.Date;

public class RentalDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Date rentalDate;
	private int rentalDays;
	private int remainingDays;
	private Long userId;
	private Long movieId;
	private double rentalFee;
	
	public RentalDTO(Long id, Date rentalDate, int rentalDays, int remainingDays, 
			Long userId, Long movieId, double rentalFee) {
		this.id = id;
		this.rentalDate = rentalDate;
		this.rentalDays = rentalDays;
		this.remainingDays = remainingDays;
		this.userId = userId;
		this.movieId = movieId;
		this.rentalFee = rentalFee;
	}
	
	public Long getId() {
		return id;
	}

	public Date getRentalDate() {
		return rentalDate;
	}

	public int getRemainingDays() {
		return remainingDays;
	}

	public int getRentalDays() {
		return rentalDays;
	}

	public double getRentalFee() {
		return rentalFee;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getMovieId() {
		return movieId;
	}

	////////////////////
	// REQUIRED BY DOZER
	////////////////////
	public RentalDTO(){}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRentalDate(Date rentalDate) {
		this.rentalDate = rentalDate;
	}

	public void setRentalDays(int rentalDays) {
		this.rentalDays = rentalDays;
	}

	public void setRemainingDays(int remainingDays) {
		this.remainingDays = remainingDays;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public void setRentalFee(double rentalFee) {
		this.rentalFee = rentalFee;
	}
	
}
