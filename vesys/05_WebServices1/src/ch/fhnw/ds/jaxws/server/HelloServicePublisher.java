package ch.fhnw.ds.jaxws.server;

import javax.xml.ws.Endpoint;

public class HelloServicePublisher {
	public static void main(String[] args){
		String url = "http://127.0.0.1:9876/hs";
		Endpoint.publish(url, new HelloServiceImpl());
		System.out.println("service published");
		System.out.println("WSDL available at "+url+"?wsdl");
	}

}
