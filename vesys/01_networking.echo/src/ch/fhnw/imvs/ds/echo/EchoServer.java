package ch.fhnw.imvs.ds.echo;

/*
 * Copyright (c) 2009-2011 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	public static void main(String args[]) throws IOException {
		int port = 1234;
		try (ServerSocket server = new ServerSocket(port)) {
			System.out.println("Startet Echo Server on port " + port);
			while (true) {
				Socket s = server.accept();
				Thread t = new Thread(new EchoHandler(s));
				t.start();
			}
		}
	}

	static class EchoHandler implements Runnable {
		private Socket s;

		private EchoHandler(Socket s) {
			this.s = s;
		}

		public void run() {
			System.out.println("connection from " + s);
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(s
						.getInputStream()));
				PrintWriter out = new PrintWriter(s.getOutputStream(), true);

				String input = in.readLine();
				while (input != null) {
					out.println(input);
					input = in.readLine();
				}
				System.out.println("done serving " + s);
			} catch (IOException e) {
				System.err.println(e);
				throw new RuntimeException(e);
			} finally {
				try {
					s.close();
				} catch (IOException e) {
					System.err.println(e);
				}
			}
		}
	}
}
