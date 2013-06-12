package helpers;

import java.io.IOException;

import org.java_websocket.WebSocket;

public class CommandHandlerImplWebSocket extends CommandHandler<WebSocket> {

	public CommandHandlerImplWebSocket(WebSocket sock) {
		super(sock);
	}

	@Override
	public void send(String cmd, String params) throws IOException {
		String msg = cmd +":"+params;
		s.send(msg);
		
	}

	@Override
	public String receive() throws IOException {
		// not used, use onMessage instead
		return null;
	}

}
