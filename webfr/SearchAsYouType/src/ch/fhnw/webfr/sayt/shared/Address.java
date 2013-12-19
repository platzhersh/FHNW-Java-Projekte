package ch.fhnw.webfr.sayt.shared;

import java.io.Serializable;

/**
 * Data container for a Swiss address.
 * @author Christoph Denzler
 *
 */
public class Address implements Serializable {
	private static final long serialVersionUID = 8111915820326938399L;
	private String lastname;
	private String firstname;
	private String street;
	private int streetnr;
	private int plz;	
	private String city;	
	
	public Address(String firstname, String lastname, String street, int streetnr, int plz, String city) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.street = street;
		this.streetnr = streetnr;
		this.plz = plz;
		this.city = city;		
	}
	public Address () {
		
	}
	
	public String getLastname() {
		return lastname;
	}


	public String getFirstname() {
		return firstname;
	}


	public String getStreet() {
		return street;
	}


	public int getStreetnr() {
		return streetnr;
	}


	public int getPlz() {
		return plz;
	}


	public String getCity() {
		return city;
	}

}
