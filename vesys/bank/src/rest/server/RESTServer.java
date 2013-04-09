package rest.server;

import java.io.IOException;

import rest.service.RESTBank;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.ApplicationAdapter;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;

public class RESTServer {

		public static void main(String[] args) throws IOException {
		final String baseUri = "http://localhost:9998/";
		ResourceConfig rc = new ApplicationAdapter(
		new RESTBank());
		System.out.println("Starting grizzly...");
		HttpServer httpServer =
		GrizzlyServerFactory.createHttpServer(baseUri, rc);
		System.in.read();
		httpServer.stop();
		}

}
