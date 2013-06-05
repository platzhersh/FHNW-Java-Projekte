package ch.fhnw.ds.rmi.export;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class CounterImpl implements Counter, Serializable {
    private int value;

    public int reset() {
        value = 0;
        System.out.println("---> Counter was reset <---");
        return value;
    }
    
    public int getValue() {
    	return value;
    }

    public int increment() {
    	// comment is only printed if increment is called on this instance
        value++;
        System.out.println("---> Counter was incremented, " + "new value: "
                           + value + " <---");
        return value;
    }

    public Counter migrateBack() throws RemoteException {
        UnicastRemoteObject.unexportObject(this, true);
        return this;
    }
    
    public Counter migrateTo(String host) throws RemoteException {
        try {
            Migrator migrator = (Migrator) Naming.lookup("rmi://" + host + "/Migrator");
            try {
            	UnicastRemoteObject.unexportObject(this, true);
            } catch(Exception  e){
            	System.out.println("Exception: "  + e.getMessage());
            }
            return migrator.migrate(this);
        }
        catch(MalformedURLException  e) { throw new RuntimeException(e); }
        catch(NotBoundException e) { throw new RuntimeException(e); }
    }

    // these method logs whenever the object is serialized
	private void writeObject(ObjectOutputStream stream) throws IOException {
		System.err.println("CounterImpl: writeObject called");
		stream.defaultWriteObject();
	}

	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException { 
		System.err.println("CounterImpl: readObject called");
		stream.defaultReadObject();
	}
}