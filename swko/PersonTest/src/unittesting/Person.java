package unittesting;

import java.util.Calendar;

public class Person {
	private String name;
	private int zip;
	private Calendar birthdate;

	/**
	 * A person is distinguished by his/her name, postal code and birth date.
	 * @param name Name of person, must not be null nor empty nor longer than 30 characters.
	 * @param zip valid Swiss postal code (1000 <= zip <= 9999).
	 * @param birthdate a birth date, must not be null, must not be in the future.
	 * @throws java.lang.IllegalArgumentException
	 */
	public Person(String name, int zip, Calendar birthdate) {
		if (name == null || name.length() <= 0 || name.length() > 30) {
			throw new IllegalArgumentException("illegal name");
		}
		Calendar now = Calendar.getInstance();
		if (birthdate == null || birthdate.after(now)) {
			throw new IllegalArgumentException("illegal birthdate");
		}

		this.name = name;
		this.zip = zip;
		this.birthdate = birthdate;
	}
		
	/**
	 * @return the person's name. Never null nor empty.
	 */
	public String getName() { return name; }
	
	/**
	 * @return the postal code of the persons residence, valid Swiss postal code.
	 */
	public int getZip() { return zip; }
	
	/**
	 * @return the person's birth date, never null, not in the future.
	 */
	public Calendar getBirthdate() { return birthdate; }
	
	/**
	 * @return the person's age, never negative
	 */
	public int getAge() {
		Calendar today = Calendar.getInstance();
		//to correctly calculate age when birthday has not been yet celebrated
		int factor = 0; 

		// check if birthday has been celebrated this year
		if(today.get(Calendar.DAY_OF_YEAR) < birthdate.get(Calendar.DAY_OF_YEAR)) {
			factor = -1; //birthday not celebrated
		}
		return today.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR) + factor;
	}
}
