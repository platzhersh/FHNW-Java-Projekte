package ch.fhnw.algd1.regex;

//URL that generated this code:
//http://txt2re.com/index-java.php3?s=[1.3,-2,3]&3 

import java.util.regex.*;

class Main {
	public static void main(String[] args) {
		String txt = "[1.3,-2,3]";

		
		String re1="(\\[.*?\\])";
		
	    String re2=".*?";	// Non-greedy match on filler
	    String re3="([+-]?\\d*\\.\\d+)(?![-+0-9\\.])";	// Float 1

		Pattern p1 = Pattern.compile(re1, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m1 = p1.matcher(txt);
		if (m1.find()) {
			txt = txt.substring(1, txt.length()-1);
			System.out.println(txt);
			
			Pattern p2 = Pattern.compile(re2+re3, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m2 = p2.matcher(txt);
			while (m2.find()) {
				MatchResult res = m2.toMatchResult();
				System.out.println(res.group(1));
				
			}
		}
	}
}

// -----
// This code is for use with Sun's Java VM - see http://java.sun.com/ for
// downloads.
//
// Paste the code into a new java application or a file called 'Main.java'
//
// Compile and run in Unix using:
// # javac Main.java
// # java Main
//