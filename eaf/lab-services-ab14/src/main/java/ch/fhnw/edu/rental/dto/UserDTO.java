package ch.fhnw.edu.rental.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String lastName;
	private String firstName;
	private String email;
	private Long[] rentalIds;
	
	public UserDTO(Long id, String lastName, String firstName, String email, Long[] rentalIds) {
		if(lastName == null) throw new IllegalArgumentException("last name must not bei null");
		if(firstName == null) throw new IllegalArgumentException("first name must not bei null");
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		if(rentalIds == null){
			this.rentalIds = new Long[0];
		}
		else {
			this.rentalIds = rentalIds;
		}
	}
	
	public Long getId() {
		return id;
	}
	public String getLastName() {
		return lastName;
	}
	public String getFirstName() {
		return firstName;
	}

	public String getEmail() {
		return email;
	}

	public Long[] getRentalIds() {
		//return rentalIds.clone();	// Changed due to Dozer
		return rentalIds;
	}


	////////////////////
	// REQUIRED BY DOZER
	////////////////////
	public UserDTO(){}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRentalIds(Long[] rentalIds) {
		this.rentalIds = rentalIds;
	}
	
}
