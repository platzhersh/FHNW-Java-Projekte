package ch.fhnw.vesys;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="student")
public class Student {

	private String firstName;
	private String lastName;
	
	private int id;
	
	/* Setter */
	
	public void setFirstName(String fName) {
		firstName = fName;
	}
	public void setLastName(String lName) {
		lastName = lName;
	}
	public void setId(int i) {
		id = i;
	}
	
	/* Getter */
	
	public String getFirstName() {
		return this.firstName;
	}
	public String getLastName() {
		return this.lastName;
	}
	public int getId() {
		return this.id;
	}
	
}
