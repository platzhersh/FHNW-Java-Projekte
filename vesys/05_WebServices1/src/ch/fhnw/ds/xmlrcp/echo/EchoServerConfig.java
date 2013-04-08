package ch.fhnw.ds.xmlrcp.echo;

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

public class EchoServerConfig {
	private static final int port = 8080;

	public static void main(String[] args) throws Exception {
		WebServer server = new WebServer(port);

		XmlRpcServer xmlRpcServer = server.getXmlRpcServer();

		PropertyHandlerMapping phm = new PropertyHandlerMapping();
		phm.load(Thread.currentThread().getContextClassLoader(),  "ch/fhnw/ds/xmlrcp/echo/xmlrpc.properties");
		xmlRpcServer.setHandlerMapping(phm);

		XmlRpcServerConfigImpl config = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
		config.setEnabledForExtensions(true);
		config.setEnabledForExceptions(true);
		config.setContentLengthOptional(true);

		server.start();
		System.out.println("Server started on port " + port);
	}
}
