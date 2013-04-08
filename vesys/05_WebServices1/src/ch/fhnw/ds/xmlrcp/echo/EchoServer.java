package ch.fhnw.ds.xmlrcp.echo;

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

public class EchoServer {
	private static final int port = 8080;

	public static void main(String[] args) throws Exception {
		WebServer server = new WebServer(port);

		XmlRpcServer xmlRpcServer = server.getXmlRpcServer();

		PropertyHandlerMapping phm = new PropertyHandlerMapping();
		phm.addHandler("Echo", ch.fhnw.ds.xmlrcp.echo.EchoImpl.class);
		phm.addHandler("ch.fhnw.ds.xmlrcp.echo.Echo", ch.fhnw.ds.xmlrcp.echo.EchoImpl.class);
		xmlRpcServer.setHandlerMapping(phm);
		
		XmlRpcServerConfigImpl config = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
		config.setEnabledForExtensions(true);
		config.setEnabledForExceptions(true);
		// config.setContentLengthOptional(false);

		server.start();
		System.out.println("Server started on port " + port);
	}
}
