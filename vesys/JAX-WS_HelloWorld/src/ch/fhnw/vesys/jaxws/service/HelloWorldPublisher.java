package ch.fhnw.vesys.jaxws.service;

import javax.xml.ws.Endpoint;

import ch.fhnw.vesys.jaxws.service.HelloWorldImpl;

//Endpoint publisher
public class HelloWorldPublisher {
	
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:9999/ws/hello", new HelloWorldImpl());
	}

}
