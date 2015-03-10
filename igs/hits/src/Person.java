import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;


public class Person implements Comparable<Person> {
	
	public int compareTo(Person o) {return email.compareTo(o.email);}
	public String email;
	public Set<Person> writesTo = new TreeSet<Person>();
	public Set<Person> receivesFrom = new TreeSet<Person>();
	public double hub = 1.0, authority = 1.0;
	
	
	public Person(String email) {
		this.email = email;
	}
	
	/** A comparator that compares the hub value of two Persons, establishing a descending order */
	static public class CompareHubs implements Comparator<Person> {
		public int compare(Person o1, Person o2) {
			return Double.compare(o2.hub, o1.hub);
		}
	}
	/** A comparator that compares the authority value of two Persons, establishing a descending order */
	static public class CompareAuthorities implements Comparator<Person> {
		public int compare(Person o1, Person o2) {
			return Double.compare(o2.authority, o1.authority);
		}
	}
}

