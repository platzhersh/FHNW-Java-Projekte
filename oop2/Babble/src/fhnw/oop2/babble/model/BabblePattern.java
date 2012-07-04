package fhnw.oop2.babble.model;

import java.util.ArrayList;
import java.util.Random;


public class BabblePattern {
	
	//public String pattern = null;
	public ArrayList<Character> nextPattern = new ArrayList<Character>();
	Random index = new Random();
	
	
	// Constructor
	public BabblePattern() {
		
	}

	// --------------- Getter & Setter
	/**
	 * @return randomly returns one of the possible next Characters
	 */
	public Character getNext() {
		
		int max = nextPattern.size();
		//if (max != 0) {
			int rand = index.nextInt(max);
			return nextPattern.get(rand);
		//} else {
		//	return nextPattern.get(0);
		//}
		
	}
	
	/***
	 * @return BabblePattern's ArrayList of possible following Patterns
	 */
	public ArrayList<Character> getPossibilities() {
		
		ArrayList<Character> possibilities = new ArrayList<Character>();
		for (char pattern : nextPattern) {
			possibilities.add(pattern);
		}
		
		return possibilities;
	}
	
	/***
	 * 
	 * @param add b to BabblePattern's internal ArrayList
	 */
	public void addNext(Character b) {	
		nextPattern.add(b);
	}
	
	
	
}
