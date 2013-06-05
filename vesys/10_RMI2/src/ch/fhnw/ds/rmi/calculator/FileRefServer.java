package ch.fhnw.ds.rmi.calculator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.rmi.Remote;
import java.rmi.server.RemoteObject;

import ch.fhnw.ds.rmi.calculator2.Calculator;
import ch.fhnw.ds.rmi.calculator2.CalculatorImpl;

public class FileRefServer {

	public final static String refFilename = "Calc.dat";

	public static void main(String args[]) throws Exception {
		Remote x;
		Calculator c = new CalculatorImpl(6666);
		x = RemoteObject.toStub(c);	// takes dynamic proxy or generated stub
		
//		//Variant:
//		Calculator c2 = new Calculator(){
//			public long add(long a, long b) { return 10+a+b; }
//			public long div(long a, long b) { return 10+a/b; }
//			public long mul(long a, long b) { return 10+a*b; }
//			public long sub(long a, long b) { return 10+a-b; }
//		};
//		// x = UnicastRemoteObject.exportObject(c); // requires that CalculatorImpl_Stub is present
//		x = UnicastRemoteObject.exportObject(c2, 7777);
//
		File f = new File(refFilename);
		ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream(f));
		out.writeObject(x);
		out.close();
		System.out.println(x);
		System.out.println("Stub saved in file "+refFilename);

		System.out.println("Calculator server started...");

/* 		If the reference is cleared, then it is removed from the RMI object table as no
 * 		remote reference is using it. */
//		c = null;
//		System.gc();

		synchronized (x) {
			x.wait();
		}

	}
}

// -Djava.rmi.server.logCalls=true