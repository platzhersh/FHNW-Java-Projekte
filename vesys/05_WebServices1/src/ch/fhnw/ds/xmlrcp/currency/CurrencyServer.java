package ch.fhnw.ds.xmlrcp.currency;

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.webserver.WebServer;

public class CurrencyServer {
	private static final int port = 8080;

	public static void main(String[] args) throws Exception {
		WebServer server = new WebServer(port);

		XmlRpcServer xmlRpcServer = server.getXmlRpcServer();

		PropertyHandlerMapping phm = new PropertyHandlerMapping();
		phm.addHandler("Currency", ch.fhnw.ds.xmlrcp.currency.CurrencyImpl.class);
		xmlRpcServer.setHandlerMapping(phm);
		
		server.start();
		System.out.println("Server started on port " + port);
	}
	
	
}
