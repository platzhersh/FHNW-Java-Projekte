package ch.fhnw.ds.rmi.quotes;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface QuoteListener extends Remote {
	void update(String q) throws RemoteException;
}