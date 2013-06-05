package ch.fhnw.ds.rmi.calculator.encoded;

import ch.fhnw.ds.rmi.calculator.Calculator;

public class CalculatorImpl  extends java.rmi.server.UnicastRemoteObject
    implements Calculator {
    	
    public CalculatorImpl(int port) throws java.rmi.RemoteException {
    	this(port, (byte)0xAC);
    }

    public CalculatorImpl(int port, byte pat) throws java.rmi.RemoteException { 
    	super(port, new XorClientSocketFactory(pat), new XorServerSocketFactory(pat));
    }

    public long add(long a, long b) {
        return a + b;
    }

    public long sub(long a, long b) {
        return a - b;
    }

    public long mul(long a, long b) {
        return a * b;
    }

    public long div(long a, long b) {
        return a / b;
    }
}