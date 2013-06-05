package ch.fhnw.ds.rmi.calculator;

import java.rmi.Naming;

public class Client {
	
	public static void main(String[] args) throws Exception {
		String host = "localhost";
		if(args.length > 0){ host = args[0]; }

		System.out.println("connecting to rmi://"+host+"/CalculatorService");	
		Calculator c = (Calculator)Naming.lookup("rmi:/CalculatorService");
		
        System.out.println( "4+5=" + c.add(4, 5) );
		
		System.in.read();
	}
}