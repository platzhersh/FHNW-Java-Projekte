package patterns.clone.company;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Company implements Cloneable{
	private String name;
	private ArrayList<Person> employees;

	public Company(String name, ArrayList<Person> employees) {
		this.name = name;
		this.employees = employees;
	}

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		name = newName;
	}
	
	public int getSize() {
		return employees.size();
	}
	
	public void addEmployee(Person p) {
		this.employees.add(p);
	}

	public boolean equals(Object o) {
		if (o instanceof Company) {
			Company c = (Company) o;
			return name.equals(c.name) 
					&& employees.equals(c.employees);
		} else {
			return false;
		}
	}

	// Copy Constructor
	public Company(Company c) {
		this.name = c.name;
		//employees = (ArrayList<Person>) c.employees.stream().map(p -> p.clone()).collect(Collectors.toList());
		employees = (ArrayList<Person>) c.employees.stream().map(Person::clone).collect(Collectors.toList());
	}

	public Company clone() {
		try {
			Company c = (Company) super.clone();
			c.employees = new ArrayList<Person>();
			for (Person p : this.employees) {
				c.employees.add(p.clone());
			}
			return c;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
