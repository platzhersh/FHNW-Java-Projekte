package ch.fhnw.ds.rmi.calculator;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import ch.fhnw.ds.rmi.calculator2.Calculator;


public class FileRefClient {

    public static void main(String[] args) throws Exception {
      	// System.setSecurityManager(new java.rmi.RMISecurityManager());
		
		System.out.println("reading proxy");
		File f = new File(FileRefServer.refFilename);
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
		Calculator c = (Calculator)in.readObject();
		System.out.println(c);

		System.out.println("number crunching:");
        System.out.println("4-3 = " + c.sub(4, 3) );
        System.out.println("4+5 = " + c.add(4, 5) );
        System.out.println("3*6 = " + c.mul(3, 6) );
        System.out.println("9/3 = " + c.div(9, 3) );
        
        try {Thread.sleep(10000);}catch(Exception e){}
        in.close();
    }
}


