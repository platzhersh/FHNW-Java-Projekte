import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;


public class Person implements Comparable<Person> {
	public Person(String email) {
		this.email = email;
	}
	public Person() {
		
	}
	public int compareTo(Person o) {return email.compareTo(o.email);}
	public String email;
	public Set<Person> writesTo = new TreeSet<Person>();
	public Set<Person> receivesFrom = new TreeSet<Person>();
	
	
	public double pagerank_prev = 1.0, pagerank = 1.0;
	
	
	public Set<String> keywords = new TreeSet<String>();;
	
	/** A comparator that compares the hub value of two Persons, establishing a descending order */
	static public class CompareHubs implements Comparator<Person> {
		public int compare(Person o1, Person o2) {
			return Double.compare(o2.pagerank_prev, o1.pagerank_prev);
		}
	}
	/** A comparator that compares the authority value of two Persons, establishing a descending order */
	static public class CompareAuthorities implements Comparator<Person> {
		public int compare(Person o1, Person o2) {
			return Double.compare(o2.pagerank, o1.pagerank);
		}
	}
	
	/* A predicate for a cheap filtering */
	public static class KeyWordsContains implements SimpleFilter.Predicate<Person> {
		public final String needle;
		public KeyWordsContains(String needle) { this.needle = needle; }
		public boolean apply(Person p) { 
			
			return p.keywords.contains(needle); 
			}
	}
}

