package ch.fhnw.edu.rental.dto;

import java.io.Serializable;
import java.util.Date;

public class MovieDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String title;
	
	private boolean rented;
	
	public void setRented(boolean rented) {
		this.rented = rented;
	}

	private Date releaseDate;
	
	private String priceCategory;
	
	public MovieDTO(Long id, String title, boolean rented, Date releaseDate, String priceCategory) {
		this.id = id;
		this.title = title;
		this.rented = rented;
		this.releaseDate = releaseDate;
		this.priceCategory = priceCategory;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public boolean isRented() {
		return rented;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public String getPriceCategory() {
		return priceCategory;
	}

	
	////////////////////
	// REQUIRED BY DOZER
	////////////////////
	public MovieDTO(){}
	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setPriceCategory(String priceCategory) {
		this.priceCategory = priceCategory;
	}
	
	
	
}
