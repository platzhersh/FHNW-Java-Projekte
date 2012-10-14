package ch.fhnw.christian.glatthard.algd2;


/* INCOMPLETE due to lazyness */

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
		
		Stack<String> s1 = new Stack<String>();
		Stack<String> s2 = new Stack<String>();
		
		String s1Line = "      _________________________________";
		String s1Line2 = "     /";
		String s2Line = "____/__________________________________";
		String s2Line2 = "    \\";
		String s3Line = "     \\_________________________________";
		
		System.out.println("-----------------------------------------------");
		System.out.println("");
		System.out.println("Das folgende Programm sortiert die Züge auf S1 und S2 mithilfe von S3.");
		System.out.println("Alle A kommen auf S1.");
		System.out.println("Alle A kommen auf S2.");
		System.out.println("");
		System.out.println(s1Line+"    S1");
		System.out.println(s1Line2);
		System.out.println(s2Line+"    S2");
		System.out.println(s2Line2);
		System.out.println(s3Line+"    S3");
		System.out.println("");
		
		
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    System.out.println("Geben Sie eine beliebige Abfolge von A und B ein für S1. (max 8)");
		    String s1Train = bufferRead.readLine();
		    System.out.println(s1Train);
		    
		    System.out.println("Geben Sie eine beliebige Abfolge von A und B ein für S2. (max 8)");
		    String s2Train = bufferRead.readLine();
		    System.out.println(s2Train);
		    
		    
		    s1Line = s1Line.substring(0,s1Line.length() - s1Train.length())+s1Train;
		    s2Line = s2Line.substring(0,s2Line.length() - s1Train.length())+s2Train;
		    
		    splitTrain(s1Train, s1);
		    splitTrain(s2Train, s2);

		    System.out.println(s1Line);
		    System.out.println(s1Line2);
		    System.out.println(s2Line);
		    System.out.println(s2Line2);
		    System.out.println(s3Line);
		    
		    
		    
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
	}

	
	static void splitTrain(String train, Stack<String> line) {
		train =  new StringBuffer(train).reverse().toString();
		
		for (int i = 0; i < train.length(); i++) {
			line.push(train.substring(i, i+1));
		}
		
	}
	
	/*
	static void moveToS3(Stack<String> train) {
		for (int i = 0; i < )
	}*/
}
