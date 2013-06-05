package ch.fhnw.ds.rmi.quotes;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public abstract class AbstractQuoteListener extends UnicastRemoteObject implements QuoteListener {	
	public AbstractQuoteListener() throws RemoteException {}	
	public abstract void update(String q);
}