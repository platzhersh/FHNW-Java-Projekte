package ch.fhnw.vesys.jaxws.client;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import ch.fhnw.vesys.jaxws.service.HelloWorld;

public class HelloWorldClient {

	public static void main(String[] args) throws Exception {
		
		URL url = new URL("http://localhost:9999/ws/hello?wsdl");
		
		QName qname = new QName("http://service.jaxws.vesys.fhnw.ch/","HelloWorldImplService");
		
		Service service = Service.create(url, qname);
		
		HelloWorld hello = service.getPort(HelloWorld.class);
		
		System.out.println(hello.getHelloWorldAsString("platzh1rsch"));

	}

}
