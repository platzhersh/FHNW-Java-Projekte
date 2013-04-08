package ch.fhnw.ds.jaxws.server;

import java.util.Date;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class HelloServiceImpl implements HelloService {

   @Override
   public String sayHello(@WebParam(name = "name") String name) {
	   if("ex".equals(name)) throw new IllegalStateException("ex not expected");
      return "Hello " + name + " from SOAP at " + new Date();
   }
}
