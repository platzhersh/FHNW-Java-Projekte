import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**********************************************************************
 * Übung 9
 * 
 * Implementieren Sie den HITS-Algorithmus.
 * 
 * Das Programm soll die wichtigsten Personen zu einem Suchbegriff ausgeben.
 * zum beispiel:
 * 
 * Top 10 Authorities on "money":
 * 0.949344	kenneth.lay@enron.com
 * 0.077169	andrew.lewis@enron.com
 * 0.067523	jeff.dasovich@enron.com
 * 0.064308	richard.shapiro@enron.com
 * 0.057073	sara.shackleton@enron.com
 * 0.054662	pmims@enron.com
 * 0.046623	louise.kitchen@enron.com
 * 0.044212	vince.kaminski@enron.com
 * 0.043408	steven.kean@enron.com
 * 0.040192	john.lavorato@enron.com
 * Top 10 Hubs for "money":
 * 0.026763	karen.denne@enron.com
 * 0.023059	steven.kean@enron.com
 * 0.022429	jeff.dasovich@enron.com
 * 0.021360	mark.palmer@enron.com
 * 0.020182	alan.comnes@enron.com
 * 0.019603	susan.mara@enron.com
 * 0.019157	diane.bazelides@enron.com
 * 0.019040	janet.dietrich@enron.com
 * 0.018828	j.kean@enron.com
 * 0.018476	enronsato@hotmail.com
 */


/*
 * Framework extensions by: Michael Niggli
 * */
public class Rank {
	
	static HashSet<Person> people;
	
	// Adjazenzmatrix for connections
	static boolean[][] connections;
	
	static List<Message> messages = new LinkedList<Message>();
	static Set<String> recipients = new TreeSet<String>();
	static Set<String> senders = new TreeSet<String>();
	
	// HITS
	// TODO: number of people needed!
	private static Collection<Person> doHITS(Collection<Message> msgs) {
		
		// node = Person
		// edge = Message
		
		// 1. Sampling -> create Root set of people and directly enhance to Base Set
		// get all recipients and senders and put them into Collection
		// question: Ist das bereits automatisch das Base Set? (erweitertes Root Set)
		
		// HashMap<Email, Person>
		HashMap<String, Person> root = new HashMap();
		
		for (Message m : msgs) {
			
			String from_email = m.from;
			
			// put sender to root set as new Person if not already in there
			root.putIfAbsent(from_email, new Person(from_email));
			
			// get Person from root set
			Person sender = root.get(from_email);
			
			// add recipients to root set, and add to writesTo of sender
			for (String recipient_email : m.to) {
				
				// add recipient to root set as new Person if not already in there
				root.putIfAbsent(recipient_email, new Person(recipient_email));
				
				// add recipients to writesTo of sender
				sender.writesTo.add(root.get(recipient_email));
				
			}
			
		}
		
		// 2. Enhance Root Set
		// add all direct neighbors of root set to root set
		// already did that in 1.?
			
		
		// 3. Iteration
		// iterate as long as error is not small enough
		
		double error = 0.0001;
		double hbefore = 1, abefore = 1;
		double hnow, anow;
		double hdiff = 1, adiff = 1;
		int k = 1;
		
		
		
		while ((hdiff > error) || (adiff > error)) {		
			
			hbefore = hdist(root.values());
			abefore = adist(root.values());
			
			
			// do magic (calculate hub score and authority score)
			
			
			for (Person p : root.values()) {
				
				// calculate hub score
				double hsum = 0;
				for (Person to : p.writesTo) {
					hsum += to.authority;
				}
				p.hub = hsum;
				
				// calculate authority score
				double asum = 0;
				for (Person from : p.writesTo) {
					asum += from.hub;
				}
				p.authority = asum;
				
			}
			
			// TODO: normieren
			
			
			System.out.println("magic");
//			System.out.println("Error: " + error);
//			System.out.println("hdiff: " + hdiff);
//			System.out.println("adiff: " + adiff);
			
			hnow = hdist(root.values());
			anow = adist(root.values());

			// TODO: this is WROOONG! substract first, then calculate vector distance
			hdiff = Math.abs(hnow - hbefore);
			adiff = Math.abs(anow - abefore);
			
//			System.out.println("Error: " + error);
//			System.out.println("hdiff: " + hdiff);
//			System.out.println("adiff: " + adiff);
			
			k++;
		}
		
		return people;
		
	}
	

	public static double hnorm(Person[] current, Person[] before) {
		
		// TODO: check same size
		double hsum = 0;
		
		for (int i = 0; i < current.length; i++) {
			hsum += Math.pow((current[i].hub - before[i].hub), 2);
		}
		return Math.sqrt(hsum);
	}
	
	public static double anorm(Person[] current, Person[] before) {
		
		// TODO: check same size
		double asum = 0;
		
		for (int i = 0; i < current.length; i++) {
			asum += Math.pow((current[i].authority - before[i].authority), 2);
		}
		return Math.sqrt(asum);
	}
	
	@Deprecated
	public static double hdist(Collection<Person> pers) {
		double hsum = 0;
		for (Person p : pers) {
			hsum += p.hub * p.hub;
		}
		return Math.sqrt(hsum);
	}
	
	@Deprecated
	public static double adist(Collection<Person> pers) {
		double asum = 0;
		for (Person p : pers) {
			asum += p.authority * p.authority;
		}
		return Math.sqrt(asum);
	}
	
	public static void main(String[] _args) throws Exception {
		
		
		
		BufferedReader br = new BufferedReader(new FileReader("mails.txt"),65536);
		for (;;) {
			Message msg = new Message();
			// read from
			String from = br.readLine();
			if (from == null || "".equals(from))
				break;
			from = from.substring(6);
			msg.from = from;

			// read to
			String to = br.readLine();
			if (to == null || "".equals(to))
				break;
			to = to.substring(4);
			msg.to = to.split(" ");

			// read subject
			String subject = br.readLine();
			if (subject == null || "".equals(subject))
				break;
			subject = subject.substring(9);
			msg.subject = subject;

			// read message body
			StringBuilder body = new StringBuilder();
			String line;
			while (!(line = br.readLine()).equals("###"))
				body.append(line);

			msg.body = body.toString();
			
			messages.add(msg);
			senders.add(msg.from);
			recipients.addAll(Arrays.asList(msg.to));
		
		}
		br.close();
				
		System.out.printf("Parsed %d messages\n from %d senders\n to %d recipients\n",messages.size(),senders.size(),recipients.size());
		

		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Please enter a search term (or \"QUIT\" to quit):");
			String keyword = null;
			try {
				keyword = scanner.next();
			} catch (NoSuchElementException nsee) {
				
			}
			
			if(keyword.equals("QUIT"))System.exit(0); 
			
			// process data
			// ...
			Collection<Message> filtered = SimpleFilter.filter(messages,new Message.BodyOrSubjectContains(keyword));
			
			System.out.printf("%d Mails left from %d with Keyword \"%s\"\n",filtered.size(),messages.size(),keyword);
			
			Collection<Person> graded = doHITS(filtered);
			Person[] people = (Person[])graded.toArray(new Person[0]);
	
			// print results
			// ...
			System.out.printf("Top 10 Authorities on \"%s\":\n",keyword);
			Arrays.sort(people, new Person.CompareAuthorities());
			for (int i = 0; i < Math.min(10,people.length); ++i) {
				System.out.printf("%10f\t%s\n",people[i].authority,people[i].email);
			}
			System.out.printf("Top 10 Hubs for \"%s\":\n",keyword);
			Arrays.sort(people, new Person.CompareHubs());
			for (int i = 0; i < Math.min(10,people.length); ++i) {
				System.out.printf("%10f\t%s\n",people[i].hub,people[i].email);
			}
		}
	}
	



}
