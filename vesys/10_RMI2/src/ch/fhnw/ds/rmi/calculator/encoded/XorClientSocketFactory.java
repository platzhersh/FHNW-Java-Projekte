package ch.fhnw.ds.rmi.calculator.encoded;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

public class XorClientSocketFactory
	implements RMIClientSocketFactory, Serializable {

	private final byte pattern;

	public XorClientSocketFactory(byte pattern) {
		this.pattern = pattern;
	}

	public Socket createSocket(String host, int port) throws IOException {
		return new XorSocket(host, port, pattern);
	}

	@Override
	public int hashCode() {return (int) pattern;}

	@Override
	public boolean equals(Object obj) {
		return obj != null && getClass() == obj.getClass()
				&& pattern == ((XorClientSocketFactory) obj).pattern;
	}

}
