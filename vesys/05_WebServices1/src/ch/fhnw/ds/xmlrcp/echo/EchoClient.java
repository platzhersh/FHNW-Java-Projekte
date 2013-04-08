package ch.fhnw.ds.xmlrcp.echo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class EchoClient {
	public static void main (String [] args) throws Exception {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL("http://localhost:8888/xmlrpc/"));
		
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		
		List<Object> params = new ArrayList<Object>();
		params.add("ex");
		
		Object result = client.execute("Echo.getEcho", params );
//		Object result = client.execute("ch.fhnw.ds.xmlrcp.echo.Echo.getEcho", params );
//		Object result = client.execute("Echo.getEchoWithDate", params );

		System.out.println("The result is: "+result.toString());
	}
}
