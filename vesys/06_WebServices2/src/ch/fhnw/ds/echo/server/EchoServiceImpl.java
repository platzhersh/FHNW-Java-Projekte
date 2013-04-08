package ch.fhnw.ds.echo.server;

import java.util.Date;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class EchoServiceImpl implements EchoService {

	@Override
	public String sayHello(@WebParam(name = "name") String name) {
		if ("ex".equals(name)) throw new IllegalStateException("ex not expected");
		return "Hello " + name + " from SOAP at " + new Date();
	}
}
