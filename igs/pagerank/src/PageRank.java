import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


/**********************************************************************
 * Übung 10
 * 
 * Implementieren Sie den PageRank-Algorithmus.
 * 
 * Das Programm soll zuerst die wichtigsten Personen (offline) ausgeben.
 * Anschliesend können die Personen mit einem Suchterm gefiltert werden.
 * 
 */

public class PageRank {

	// TODO: pageRank
	protected static Collection<Person> doPageRank(Collection<Message> msgs) {
		
		
		// TODO: Pagerank im authority Feld speichern
		// TODO: Abbruchkriterium implementieren
		// TODO: keywords Liste jeder Person mit allen Wörtern aus den verfassten Mails
		// ACHTUNG: Zeit & Speicherintensiv
		// TODO: mit HITS Lösung vergleichen
		// TODO: Zyklen auflösen mit Dämpfungsfaktor
		// TODO: Rank sink verhindern (Dangling Node)
		
		// Laufzeitkomplexität
		
		HashMap<String, Person> root = new HashMap();
		
		// Aufbau des Graphen mit allen Verbindungen
		for (Message m : msgs) {
			
			String from_email = m.from;
			
			// put sender to root set as new Person if not already in there
			root.putIfAbsent(from_email, new Person(from_email));
			
			// get Person from root set
			Person sender = root.get(from_email);
			
			
			// add keywords to writer of Email
			// remove all special characters apart from space, also remove numbers because there seem to be some ASCII problems
			// http://www.kriblog.com/j2se/lang/how-to-remove-all-the-special-characters-from-a-string-in-java.html
			String subj = m.subject.replaceAll("[^a-zA-Z ]", "");
			for (String keyword : subj.split(" ")) {
				sender.keywords.add(keyword.trim());
			}
			
			String body = m.body.replaceAll("[^a-zA-Z ]", "");
			for (String keyword : body.split(" ")) {
				sender.keywords.add(keyword.trim());
			}
			
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
		
		Person p = root.get("justin.rostant@enron.com");
				
		return root.values();

	}
	
	public static void main(String[] _args) throws Exception {
		List<Message> messages = new LinkedList<Message>();
		Set<String> recipients = new TreeSet<String>(), senders = new TreeSet<String>();
		
		readMessages(messages, recipients, senders);
		System.out.printf("read %s Messages\n",messages.size());
				
		Collection<Person> graded = doPageRank(messages);
		
		messages=null;
		
		Person[] reference = new Person[0];
		Person[] people = (Person[])graded.toArray(reference);
		Arrays.sort(people,new Person.CompareAuthorities());
		System.out.println("Top 10 PageRanks:");
		System.out.println("-=-=-=-=-=-=-=-=-");
		System.out.println();
		for (int i = 0; i < 10; ++i) {
			System.out.printf("%10f\t%s\n",people[i].authority,people[i].email);
		}
		
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Please enter a search term (or \"QUIT\" to quit):");
			String keyword = null;
			try {
				keyword = scanner.next();
			} catch (NoSuchElementException nsee) {}
			
			if(keyword.equals("QUIT"))System.exit(0); 
							
						
			// print results
			Person[] filteredPeople = (Person[])SimpleFilter.filter(graded,new Person.KeyWordsContains(keyword)).toArray(reference);
			
			System.out.printf("%d Persons left from %d with Keyword \"%s\"\n",filteredPeople.length,graded.size(),keyword);
			
			Arrays.sort(filteredPeople,new Person.CompareAuthorities());
			System.out.printf("Top 10 PageRanks for Term: \"%s\"\n",keyword);
			System.out.println("-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-");
			System.out.println();
			for (int i = 0; i < Math.min(10,filteredPeople.length); ++i) {
				System.out.printf("%10f\t%s\n",filteredPeople[i].authority,filteredPeople[i].email);
			}		
		}
	}

	private static void readMessages(List<Message> messages, Set<String> recipients, Set<String> senders) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("mails.txt"),
				65536);
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
			// process message
			// ..
		}
		br.close();
	}

	
	
	
}
