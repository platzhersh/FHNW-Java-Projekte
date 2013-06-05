package ch.fhnw.ds.rmi.export;

import java.rmi.*;

public interface Counter extends Remote {
    public int reset() throws RemoteException;
    public int getValue() throws RemoteException;
    public int increment() throws RemoteException;
    
    public Counter migrateBack() throws RemoteException;
    public Counter migrateTo(String host) throws RemoteException;
}