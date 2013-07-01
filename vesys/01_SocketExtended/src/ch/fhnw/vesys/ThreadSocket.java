package ch.fhnw.vesys;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class ThreadSocket implements Runnable {

	ServerSocket srv;
	List<Socket> s;

	public ThreadSocket(int port) {
		try {
			srv = new ServerSocket(port);
			System.out.println("ThreadSocket created on " + srv);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread t = new Thread( new SocketHandler(srv.accept()));
				System.out.println("Connection established on "+ srv);
				t.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
