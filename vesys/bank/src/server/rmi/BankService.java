package server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface BankService extends Remote {

	public Set<String> getAccounts() throws RemoteException;
	
}
