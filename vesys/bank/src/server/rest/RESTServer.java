package server.rest;

import java.io.IOException;

import server.rest.service.BankApplication;

import org.glassfish.grizzly.http.server.HttpServer;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.ApplicationAdapter;
import com.sun.jersey.api.core.ResourceConfig;


public class RESTServer {

		public static void main(String[] args) throws IOException {
			final String baseUri = "http://localhost:9998/";
			ResourceConfig rc = new ApplicationAdapter(new BankApplication());
			
			System.out.println("Starting grizzly.");
			System.out.println("Listening on "+baseUri+".");
			HttpServer httpServer =	GrizzlyServerFactory.createHttpServer(baseUri, rc);
			
			System.in.read();
			httpServer.stop();
		}

}
