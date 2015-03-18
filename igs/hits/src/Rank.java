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
	// Schwellwert
	static double e = 0.0000001;

	
	// HITS
	// msgs = Root set
	private static Collection<Person> doHITS(Collection<Message> msgs) {
		
		// node = Person
		// edge = Message
		
		// 1. Sampling -> build graph
		// get all recipients and senders and put them into Collection
		// question: Ist das bereits automatisch das Base Set? (erweitertes Root Set)
		// -> alle "from" Personen -> Root Set
		// -> hinzufügen aller "to" Personen -> Erweiterung zum Base Set 
		//		(in dieser Aufgabe nur in eine Richtung, normalerweise in beide)
		
		// HashMap<Email, Person>
		HashMap<String, Person> root = new HashMap();
		
		// Aufbau des Graphen mit allen Verbindungen
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
				Person recipient = root.get(recipient_email);
				
				// add recipients to writesTo of sender
				sender.writesTo.add(recipient);
				recipient.receivesFrom.add(sender);
				
			}
			
		}
		
		// 2. Iteration
		// iterate as long as error is not small enough
	
		int k = 0;
		boolean go_on = true;
		double err = 0;
		
		//while (k < 4) {
		do {
			// increase iteration count
			k++;
			//System.out.println("Iteration " + k);
			
			// reset error for this iteration
			err = 0;
			
			// do magic (calculate hub score and authority score)
			
			// sums over all the authority and hub scores of all the people (used to normalize later on)
			double iter_hsum = 0;
			double iter_asum = 0;
			
						
			// calculate auth scores
			for (Person p : root.values()) {
				
				p.authority_prev = p.authority;
				
				// calculate authority score
				double asum = 0;
				for (Person from : p.receivesFrom) {
					asum += from.hub;
				}				
				// normalise?
				//p.authority = Math.sqrt(asum * asum);
				p.authority = asum;
				iter_asum += p.authority;
								
			}
			
			// calculate hub scores
			for (Person p : root.values()) {

				p.hub_prev = p.hub; 
				
				// calculate hub score
				double hsum = 0;
				for (Person to : p.writesTo) {
					hsum += to.authority;
				}
				// normalise?
				// p.hub = Math.sqrt(hsum * hsum);
				p.hub = hsum;
				iter_hsum += p.hub;
				
			}
			
			// normieren (theoretisch nicht unbedingt nötig, aber Zahlen werden extrem gross sonst)		
			for (Person p : root.values()) {
				p.hub /= iter_hsum;
				p.authority /= iter_asum;
				if (k > 1) {
					err += Math.pow(p.hub_prev - p.hub, 2);
					err += Math.pow(p.authority_prev - p.authority, 2);
				}
			}
			
			//err /= root.size();
			err = Math.sqrt(err/(2*root.size()));
			if (k > 1 && err < e) {
				go_on = false;
			}
			
		} while (go_on);
		
		System.out.println(k + " Iterations");
		return root.values();
		
	}

	// used to calculate the difference between two values
	public static double rootMeanSquare(double now, double before) {
		return Math.sqrt(Math.pow((now - before), 2));
	}
	
	public static double simpleDiff(double now, double before) {
		return Math.abs(now - before);
	}

	public static double hnorm(Collection<Person> before, Collection<Person> current) {
		
		Object[] curr_array = current.toArray();
		Object[] bef_array = before.toArray();
		
		// TODO: check same size
		double hsum = 0;
		
		for (int i = 0; i < curr_array.length; i++) {
			hsum += Math.pow((((Person)curr_array[i]).hub - ((Person)bef_array[i]).hub), 2);
		}
		return Math.sqrt(hsum);
	}
	
	public static double anorm(Collection<Person> before, Collection<Person> current) {
		
		Object[] curr_array = current.toArray();
		Object[] bef_array = before.toArray();
		
		// TODO: check same size
		double asum = 0;
		
		for (int i = 0; i < curr_array.length; i++) {
			asum += Math.pow((((Person)curr_array[i]).hub - ((Person)bef_array[i]).hub), 2);
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
