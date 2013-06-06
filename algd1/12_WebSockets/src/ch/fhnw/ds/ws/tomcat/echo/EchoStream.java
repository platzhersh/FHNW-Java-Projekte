package ch.fhnw.ds.ws.tomcat.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;


public class EchoStream extends WebSocketServlet {

    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol,
            HttpServletRequest request) {
        return new EchoStreamInbound();
    }

    private static final class EchoStreamInbound extends StreamInbound {

        @Override
        protected void onBinaryData(InputStream is) throws IOException {
            // Simply echo the data to back to the client.
            WsOutbound outbound = getWsOutbound();

            int i = is.read();
            while (i != -1) {
                outbound.writeBinaryData(i);
                i = is.read();
            }

            outbound.flush();
        }

        @Override
        protected void onTextData(Reader r) throws IOException {
            // Simply echo the data to back to the client.
            WsOutbound outbound = getWsOutbound();

            int c = r.read();
            while (c != -1) {
                outbound.writeTextData((char) c);
                c = r.read();
            }

            outbound.flush();
        }
    }
}