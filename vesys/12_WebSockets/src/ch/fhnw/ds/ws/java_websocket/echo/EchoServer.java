package ch.fhnw.ds.ws.java_websocket.echo;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class EchoServer extends WebSocketServer {

	public static void main(String[] args) {
		WebSocketServer server = new EchoServer(80);
		server.start();
	}
	
	public EchoServer( int port ) {
		super( new InetSocketAddress(port) );
	}

	@Override
	public void onOpen( WebSocket conn, ClientHandshake handshake ) {
		System.out.println( "new connection to " + conn.getRemoteSocketAddress() );
	}

	@Override
	public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
		System.out.println( "closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason );
	}

	@Override
	public void onMessage( WebSocket conn, String message ) {
		System.out.println( "received message form " + conn.getRemoteSocketAddress() + ": " + message );
		conn.send("echo "+message);
	}

	@Override
	public void onError( WebSocket conn, Exception ex ) {
		System.out.println( "an error occured on connection " + conn + ":" + ex );
	}
	
	public void onMessage(WebSocket conn, ByteBuffer buf) {
		System.out.println("onMessage");
	}

}