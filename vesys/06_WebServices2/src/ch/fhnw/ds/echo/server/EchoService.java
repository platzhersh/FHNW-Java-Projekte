package ch.fhnw.ds.echo.server;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface EchoService {
	String sayHello(@WebParam(name = "name") String name);
}
