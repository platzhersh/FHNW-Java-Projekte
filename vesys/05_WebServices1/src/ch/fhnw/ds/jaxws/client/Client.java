package ch.fhnw.ds.jaxws.client;

import ch.fhnw.ds.jaxws.client.jaxws.HelloServiceImpl;
import ch.fhnw.ds.jaxws.client.jaxws.HelloServiceImplService;

public class Client {

	public static void main(String[] args) throws Exception {
		HelloServiceImplService service = new HelloServiceImplService();
		HelloServiceImpl port = service.getHelloServiceImplPort();
		
		String result = port.sayHello("Dominik");
		System.out.println(result);
	}
}

