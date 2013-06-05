package ch.fhnw.ds.rmi.quotes;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface QuoteServer extends Remote {
	void addQuote(String quote) throws RemoteException;
	void addQuoteListener(QuoteListener c) throws RemoteException;
}