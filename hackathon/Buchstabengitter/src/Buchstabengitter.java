import java.io.*;
import java.util.*;

public class Buchstabengitter
{
	
  public static String reverseString(String s) {
	  StringBuilder sb = new StringBuilder();
	  for (int i = s.length()-1; i >= 0; i--) {
		  sb.append(s.charAt(i));
	  }
	  return sb.toString();
  }
	
  public static void main(String[] args) throws Exception
  {
    Scanner in = new Scanner(new File("search.in"));
    PrintWriter out=new PrintWriter("search.out");
    
    int b;	// Breite
    int h;	// Höhe
    
    int wc1;	// Anzahl zu findende Wörter
    int wc2;	// Anzahl gefundener Wörter
    
    // Get dimension
    b = in.nextInt();
    h = in.nextInt();
    
    System.out.println(b);
    System.out.println(h);
    
    // initialize Array
    String[] buchstabengitter = new String[h+1];
    
    for (int i = 0; i <= h; i++) {
    		buchstabengitter[i] = in.nextLine();
    		System.out.println(buchstabengitter[i]);
    }
    
    wc1 = in.nextInt();
    System.out.println(wc1);
    
    // Wörter Array
    String[] words = new String[2*(wc1+1)];
    
    int i = 0;
    String s;
    while (in.hasNextLine()) {
    	s = in.nextLine();
    	System.out.println(s);
    	System.out.println(reverseString(s));
    	words[i++] = s;
		words[i++] = reverseString(s);
    }

    // Wörter suchen
    
    
    //out.println("solution");
    
    out.close();
  }
}