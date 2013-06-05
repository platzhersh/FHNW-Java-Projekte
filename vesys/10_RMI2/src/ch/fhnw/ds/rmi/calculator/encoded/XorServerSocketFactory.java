package ch.fhnw.ds.rmi.calculator.encoded;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;

public class XorServerSocketFactory implements RMIServerSocketFactory {

	private final byte pattern;

	public XorServerSocketFactory(byte pattern) {
		this.pattern = pattern;
	}

	public ServerSocket createServerSocket(int port) throws IOException {
		return new XorServerSocket(port, pattern);
	}

	@Override
	public int hashCode() {
		return (int) pattern;
	}

	@Override
	public boolean equals(Object obj) {
		return (
			getClass() == obj.getClass()
				&& pattern == ((XorServerSocketFactory) obj).pattern);
	}

}
