package ch.fhnw.ds.rmi.calculator;

public class CalculatorImpl extends java.rmi.server.UnicastRemoteObject
		implements Calculator {

	public CalculatorImpl() throws java.rmi.RemoteException {
	}

	public CalculatorImpl(int port) throws java.rmi.RemoteException {
		super(port);
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
