package server.websocket;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;


public class WSServer extends WebSocketServer {
	
	

	public static void main (String[] args) throws UnknownHostException {
		WSServer ws = new WSServer();
		ws.start();
		System.out.println("WebSocketServer started (ws://localhost:2222)");
	}
	
	public WSServer() {
		super(new InetSocketAddress("localhost",2222));
		// TODO Auto-generated constructor stub
	}

	public WSServer(InetSocketAddress address, int decoders) {
		super(address, decoders);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void onClose(WebSocket arg0, int arg1, String arg2, boolean arg3) {
		// TODO Auto-generated method stub
		System.out.println("Client disconnected."+arg1+" "+arg2);
	}

	@Override
	public void onError(WebSocket arg0, Exception arg1) {
		// TODO Auto-generated method stub
		System.out.println("Error in WebSocketServer: "+arg1.toString());
	}

	@Override
	public void onMessage(WebSocket arg0, String arg1) {
		// TODO Auto-generated method stub
		System.out.println("Message received: "+arg1);
	}

	@Override
	public void onOpen(WebSocket arg0, ClientHandshake arg1) {
		// TODO Auto-generated method stub
		System.out.println("Client connected.");
	}

}
