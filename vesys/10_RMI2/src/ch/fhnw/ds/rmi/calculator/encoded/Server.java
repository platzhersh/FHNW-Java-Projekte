package ch.fhnw.ds.rmi.calculator.encoded;

// -Djava.rmi.server.codebase=http://localhost:8080/

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import ch.fhnw.ds.rmi.calculator.Calculator;

public class Server {
	public static void main(String args[]) {

		try {
			System.out.println(Server.class.getName()+".main: creating registry");
			LocateRegistry.createRegistry(1099);
		}
		catch (RemoteException e) {
			System.out.println(">> registry could not be exported");
			System.out.println(">> probably another registry already runs on 1099");
		}
 
		try {
			Calculator c = new CalculatorImpl(7777);
			Naming.rebind("rmi://localhost:1099/CalculatorService", c);
		}
		catch (Exception e) {
			System.out.println("Trouble: " + e);
		}
		System.out.println("Calculator server started...");
	}
}
