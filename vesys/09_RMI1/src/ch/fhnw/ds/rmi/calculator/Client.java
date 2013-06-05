package ch.fhnw.ds.rmi.calculator;

import java.rmi.Naming;

public class Client {
	
	public static void main(String[] args) throws Exception {
		//System.setSecurityManager(new java.rmi.RMISecurityManager());
		
		String host = "localhost";
		if(args.length > 0){ host = args[0]; }

		System.out.println("connecting to rmi://"+host+"/CalculatorService");	
		Calculator c = (Calculator)Naming.lookup(
				//"rmi://"+host+"/CalculatorService");
				"rmi:/CalculatorService");
		System.out.println( "4-3=" + c.sub(4, 3) );
        System.out.println( "4+5=" + c.add(4, 5) );
        System.out.println( "3*6=" + c.mul(3, 6) );
        System.out.println( "9/3=" + c.div(9, 3) );
		
		System.in.read();
	}
}