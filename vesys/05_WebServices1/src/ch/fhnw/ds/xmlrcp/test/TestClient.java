package ch.fhnw.ds.xmlrcp.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.common.XmlRpcInvocationException;

// This client will 
public class TestClient {
	public static void main (String [] args) throws Exception {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL("http://localhost:8888/xmlrpc"));
		
		config.setEnabledForExtensions(true);
		config.setEnabledForExceptions(true);
		
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		
		// Struct
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("one", new Integer(3));
		m.put("two", new Boolean(true));
		m.put("three", new Double(3));
		m.put("four", new byte[]{0xC,0xA,0xF, 0xE}); // base64
		m.put("five", new X(33));

		// DateTime
		Object d = new Date();		// DateTime
		
		// Array
		Object a = new Object[]{"hallo", 44};
		
		// X
		Object x = new X(13);
		
		List<Object> params = new ArrayList<Object>();
		params.add("tex");

		Object result = client.execute("Test.echo", params );
		System.out.println("The result is: "+result.toString());
	}
}

class X implements java.io.Serializable {
	X(int x) {
		this.x = x;
	}

	int x;
	int getX() { return x; }
	void setX(int x) { this.x = x; }
}
