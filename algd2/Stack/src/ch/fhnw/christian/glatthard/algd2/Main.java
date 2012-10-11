package ch.fhnw.christian.glatthard.algd2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(1);
		System.out.println(stack.top());
		stack.push(2);
		System.out.println(stack.top());
		stack.push(3);
		System.out.println(stack.top());
		
		System.out.println(stack.isEmpty());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.isEmpty());
		
		// Sackbahnhof
		System.out.println("-----------------------------------------------");
		System.out.println("");
		System.out.println("Das folgende Programm sortiert die Züge auf S1 und S2 mithilfe von S3.");
		System.out.println("Alle A kommen auf S1.");
		System.out.println("Alle A kommen auf S2.");
		System.out.println("");
		System.out.println("      _________________________________    S1");
		System.out.println("     /");
		System.out.println("____/__________________________________    S2");
		System.out.println("    \\");
		System.out.println("     \\_________________________________    S3");
		System.out.println("");
		
		
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    System.out.println("Geben Sie eine beliebige Abfolge von A und B ein für S1. (max 8)");
		    String s1 = bufferRead.readLine();
		    System.out.println(s1);
		    
		    System.out.println("Geben Sie eine beliebige Abfolge von A und B ein für S2. (max 8)");
		    String s2 = bufferRead.readLine();
		    System.out.println(s2);
		    
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}

}
