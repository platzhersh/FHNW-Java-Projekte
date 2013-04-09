package ch.fhnw.ds.rest.msg.server;

import java.io.IOException;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

public class Server1 {

	public static void main(String[] args) throws IOException {

		final String baseUri = "http://localhost:9998/";

		// @Singleton annotations will be respected 
		ResourceConfig rc = new PackagesResourceConfig("ch.fhnw.ds.rest.msg.resources");
		
		System.out.println("Starting grizzly...");
		HttpServer httpServer = GrizzlyServerFactory.createHttpServer(baseUri, rc);

		System.out.println(String.format("Jersey app started with WADL available at "
								+ "%sapplication.wadl\nTry out %smsg\nHit enter to stop it...",
								baseUri, baseUri));

		System.in.read();
		httpServer.stop();
	}
	
}
