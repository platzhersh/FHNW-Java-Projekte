package server.jaxws;

import javax.xml.ws.Endpoint;

import server.MyBank;

public class WebservicesPublisher {

	
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:9999/bank/jaxws", new WebservicesImpl(new MyBank()));
		System.out.println("Endpoint published: http://localhost:9999/bank/jaxws");
	}

}
