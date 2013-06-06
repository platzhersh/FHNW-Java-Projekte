package ch.fhnw.ds.ws.java_websocket.echo;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

public class EchoClient {

	public static void main(String[] args) throws Exception {
		final URI url = new URI("ws://echo.websocket.org/");
		//final URI url = new URI("ws://localhost:8888/examples/websocket/echoStream");
		//final URI url = new URI("ws://localhost/");
		//final URI url = new URI("ws://localhost:2222/");
		
		final WebSocketClient c = new WebSocketClient(url, new Draft_17()) {
			@Override
			public void onOpen(ServerHandshake handshakedata) {
				System.out.println("onOpen "+Thread.currentThread());
				send("Hello");
				//send(new byte[]{'h', 'e', 'l', 'o'});
			}

			@Override
			public void onMessage(String message) {
				System.out.println("onMessage "+message + " "+Thread.currentThread());
				close();
			}

			@Override
			public void onError(Exception ex) {
				System.out.println("onError "+Thread.currentThread());
			}

			@Override
			public void onClose(int code, String reason, boolean remote) {
				System.out.println("onClose "+Thread.currentThread());
			}
		};
		c.connect();
		System.out.println(Thread.currentThread());
	}
	
}
