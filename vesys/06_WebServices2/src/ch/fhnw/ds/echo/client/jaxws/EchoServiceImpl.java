
package ch.fhnw.ds.echo.client.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "EchoServiceImpl", targetNamespace = "http://server.echo.ds.fhnw.ch/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface EchoServiceImpl {


    /**
     * 
     * @param name
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "sayHello", targetNamespace = "http://server.echo.ds.fhnw.ch/", className = "ch.fhnw.ds.echo.client.jaxws.SayHello")
    @ResponseWrapper(localName = "sayHelloResponse", targetNamespace = "http://server.echo.ds.fhnw.ch/", className = "ch.fhnw.ds.echo.client.jaxws.SayHelloResponse")
    @Action(input = "http://server.echo.ds.fhnw.ch/EchoServiceImpl/sayHelloRequest", output = "http://server.echo.ds.fhnw.ch/EchoServiceImpl/sayHelloResponse")
    public String sayHello(
        @WebParam(name = "name", targetNamespace = "")
        String name);

}
