package ch.fhnw.ds.rest.msg.client;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class Client1 {

	public static void main(String[] args) {
		 Client c = Client.create();
		 WebResource r = c.resource("http://localhost:9998/msg");

		 String response1 = r.accept(
			        MediaType.TEXT_PLAIN).
			        get(String.class);
		 System.out.println(MediaType.TEXT_PLAIN);
		 System.out.println(response1);
		 
		 String response2 = r.accept(
			        MediaType.APPLICATION_XML).
			        get(String.class);
		 System.out.println(MediaType.APPLICATION_XML);
		 System.out.println(response2);
		 System.out.println();
		 
		 String response3 = r.accept(
			        MediaType.APPLICATION_JSON).
			        get(String.class);
		 System.out.println(MediaType.APPLICATION_JSON);
		 System.out.println(response3);

	}

}
