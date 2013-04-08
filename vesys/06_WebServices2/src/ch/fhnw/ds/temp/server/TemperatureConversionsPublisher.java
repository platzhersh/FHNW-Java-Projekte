package ch.fhnw.ds.temp.server;

import javax.xml.ws.Endpoint;

public class TemperatureConversionsPublisher {
	public static void main(String[] args){
		Endpoint.publish("http://localhost:19876/temp", new TemperatureConversionsImpl());
		System.out.println("service published, wsdl file is available at");
		System.out.println("http://localhost:19876/temp?wsdl");
	}

}
