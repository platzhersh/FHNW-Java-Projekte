package ch.fhnw.ds.rmi.export;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MigratorImpl extends UnicastRemoteObject implements Migrator {

	public MigratorImpl() throws RemoteException { 	}

	public Counter migrate(Counter counter) throws RemoteException {
		Object x = UnicastRemoteObject.exportObject(counter, 0);
		return (Counter)x;
//		return counter;
	}
}