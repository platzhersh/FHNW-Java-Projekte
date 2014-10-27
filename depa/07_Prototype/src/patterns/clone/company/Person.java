package patterns.clone.company;

public class Person implements Cloneable {
	private int age;
	private String name;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Person(int age, String name) {
		this.age = age;
		this.name = name;
	}

	public boolean equals(Object o) {
		if (o instanceof Person) {
			Person p = (Person) o;
			return (p.age == age) && (p.name.equals(name));
		}
		return false;
	}
	
	public Person clone() {
		try {
			return (Person) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
