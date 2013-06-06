package ch.fhnw.ds.ws.tomcat.echo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;


public class EchoMessage extends WebSocketServlet {

    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol,
            HttpServletRequest request) {
        return new EchoMessageInbound();
    }

    private static final class EchoMessageInbound extends MessageInbound {

    	// Simply echo the data to back to the client.
        @Override
        protected void onBinaryMessage(ByteBuffer message) throws IOException {
            getWsOutbound().writeBinaryMessage(message);
        }

        @Override
        protected void onTextMessage(CharBuffer message) throws IOException {
            getWsOutbound().writeTextMessage(message);
        }
    }	
}