package ch.fhnw.ds.rmi.export;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
	public static void main(String args[]) throws Exception {
		LocateRegistry.createRegistry(1099);
		Naming.rebind("Migrator", new MigratorImpl());
		System.out.println("Migration Server started");
		System.out.println(Naming.lookup("Migrator"));
	}
}