package ch.fhnw.ds.graph.server;

import javax.xml.ws.Endpoint;

public class GraphServicePublisher {

	public static void main(String[] args) {
		Endpoint.publish("http://127.0.0.1:9877/graph", new GraphServiceImpl());
		System.out.println("service published, wsdl file is available at");
		System.out.println("http://localhost:9877/graph?wsdl");
	}

}
