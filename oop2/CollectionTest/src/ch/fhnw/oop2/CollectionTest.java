package ch.fhnw.oop2;

import java.util.Scanner;
import java.util.Vector;
import java.util.Enumeration;

public class CollectionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Vector<String> input = new Vector<String>();

		Scanner scanner = new Scanner(System.in);
		
		String t = "";
		
		/* Save every line of text typed in by the user until he writes "quit" */
		while ( t.compareTo("quit") != 0) {
			
			System.out.println("Type whatever comes to your mind. To end, type \"quit\"");
			t = scanner.nextLine();
			input.add(t);
			
		}
		
		scanner.close();
		
		
		/* Print all the Strings stored with an Enumeration */
		Enumeration<String> lines = input.elements();
		
		while (lines.hasMoreElements()) {
			System.out.println(lines.nextElement());
		}
		
		/* Print all the String stored in the vector */
		for (int i = 0; i < input.size(); i++) {
			System.out.println(input.get(i));
		}

	}

}
