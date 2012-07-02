import java.util.*;

import java.io.*;

public class Person implements Comparable<Person>{
	
	private String lastName;
	private String firstName; 
	private int personNr;
	
	public Person(int PersonNr, String LastName, String FirstName)
	{	
		this.personNr=PersonNr;
		this.lastName=LastName;
		this.firstName=FirstName;
	}

	public String toString() 
	{
		return this.personNr + " - " + this.firstName + " " + this.lastName;
	}
	
	public int compareTo(Person p) 
	{
		if (this.personNr > p.personNr) 
		{
			return 1;
		} 
		else if (this.personNr < p.personNr) 
		{
			return -1;
		} 
		else 
		{
			return 0;
		}
	}

}
