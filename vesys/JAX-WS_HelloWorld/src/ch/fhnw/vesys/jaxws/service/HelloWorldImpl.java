package ch.fhnw.vesys.jaxws.service;

import javax.jws.WebService;

// Service Implementation
@WebService(endpointInterface = "ch.fhnw.vesys.jaxws.service.HelloWorld")
public class HelloWorldImpl implements HelloWorld{
		
	@Override
	public String getHelloWorldAsString(String name) {
		return "Hello World JAX-WS " + name;
	}
}
