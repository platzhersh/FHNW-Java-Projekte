package ch.fhnw.ds.xmlrcp.echo;

import java.net.URL;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.util.ClientFactory;

public class EchoClientProxy {
	public static void main (String [] args) throws Exception {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL("http://localhost:8888/xmlrpc/"));
		config.setEnabledForExtensions(true);
		config.setEnabledForExceptions(true);
		
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
	
		ClientFactory factory = new ClientFactory(client);
		Echo echo = (Echo) factory.newInstance(Echo.class);

		System.out.println("The result is: " + echo.getEcho("World2"));
	}
}
