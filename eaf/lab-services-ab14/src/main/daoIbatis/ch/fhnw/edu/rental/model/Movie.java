package ch.fhnw.edu.rental.model;

import java.util.Date;

public class Movie {
	private Long id;
	
	private String title;
	
	private boolean rented;
	
	private Date releaseDate;
	
	private PriceCategory priceCategory;

	public Movie() {
		// public default constructor required by Ibatis
	}

	public Movie(String title, Date releaseDate, PriceCategory priceCategory) throws NullPointerException {
		if ((title == null) || (releaseDate == null) || (priceCategory == null)) {
			throw new NullPointerException("not all input parameters are set!");
		}
		this.title = title;
		this.releaseDate = releaseDate;
		this.priceCategory = priceCategory;
		this.rented = false;
	}
	
	public PriceCategory getPriceCategory() {
		return priceCategory;
	}

	public void setPriceCategory(PriceCategory priceCategory) {
		this.priceCategory = priceCategory;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) { // TODO was protected, changed due to iBATIS
		if(this.title != null) throw new IllegalStateException();
		this.title = title;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) { // TODO was protected, changed due to iBATIS
		if(this.releaseDate != null) throw new IllegalStateException();
		this.releaseDate = releaseDate;
	}

	public boolean isRented() {
		return rented;
	}

	public void setRented(boolean rented) { // TODO allenfalls löschen und über Assoziationen prüfen
		this.rented = rented;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((releaseDate == null) ? 0 : releaseDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		final Movie other = (Movie) obj;
		if (id != null && id.equals(other.getId())) 
			return true;
		if (releaseDate == null) {
			if (other.getReleaseDate() != null)
				return false;
		} else if (!releaseDate.equals(other.getReleaseDate()))
			return false;
		if (title == null) {
			if (other.getTitle() != null)
				return false;
		} else if (!title.equals(other.getTitle()))
			return false;
		return true;
	}


}
