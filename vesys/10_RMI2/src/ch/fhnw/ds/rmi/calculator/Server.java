package ch.fhnw.ds.rmi.calculator;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {
	public static void main(String args[]) throws Exception {
		try {
			System.out.println(Server.class.getName()+".main: creating registry");
			LocateRegistry.createRegistry(1099);
		}
		catch (RemoteException e) {
			System.out.println(">> registry could not be exported");
			System.out.println(">> probably another registry already runs on 1099");
		}

		Calculator c = new CalculatorImpl(
					7777
		);
		Naming.rebind("rmi://localhost:1099/CalculatorService", c);
		System.out.println("Calculator server started...");
	}
}
