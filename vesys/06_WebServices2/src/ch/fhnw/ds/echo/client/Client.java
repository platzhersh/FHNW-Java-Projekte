package ch.fhnw.ds.echo.client;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

import ch.fhnw.ds.echo.client.jaxws.EchoServiceImpl;
import ch.fhnw.ds.echo.client.jaxws.EchoServiceImplService;

public class Client {

	public static void main(String[] args) throws Exception {
		EchoServiceImplService service = new EchoServiceImplService();
		service.setHandlerResolver(new ClientHandlerResolver());
		EchoServiceImpl port = service.getEchoServiceImplPort();

		String result = port.sayHello("Dominik");
		System.out.println(result);
	}
}

class ClientHandlerResolver implements HandlerResolver {
	public List<Handler> getHandlerChain(PortInfo portInfo) {
		List<Handler> chain = new ArrayList<Handler>();
		chain.add(new TestHandler());
		return chain;
	}
}
