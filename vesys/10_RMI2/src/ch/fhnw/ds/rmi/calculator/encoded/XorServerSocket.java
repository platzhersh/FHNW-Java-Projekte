package ch.fhnw.ds.rmi.calculator.encoded;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class XorServerSocket extends ServerSocket {
	private final byte pattern;


	public XorServerSocket(int port, byte pattern) throws IOException {
		super(port); this.pattern = pattern;
	}

	@Override
	public Socket accept() throws IOException {
		Socket s = new XorSocket(pattern); implAccept(s); return s;
	}
}