package rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import server.MyBank;
import server.Server;

import bank.Bank;


public class RMIServer {

		public static void main(String args[]) throws Exception {
			// try to create rmi registry within this process
			try {
				System.out.println(Server.class.getName()+".main: creating registry");
				LocateRegistry.createRegistry(1099);
			}
			catch (RemoteException e) {
				System.out.println(">> registry could not be exported");
				System.out.println(">> probably another registry already runs on 1099");
			}

			Bank b = new MyBank();					
			
			Naming.rebind("rmi://localhost:1099/CalculatorService", c);
			System.out.println("Calculator server started...");
		}
	}
