package server.jaxws;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class DummyClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://localhost:9999/bank/jaxws?wsdl");
		
		QName qname = new QName("http://service.jaxws/","WebservicesImplService");
		
		Service service = Service.create(url, qname);
		
		Webservices websvc = service.getPort(Webservices.class);
		
		System.out.println("connected...");

	}

}
