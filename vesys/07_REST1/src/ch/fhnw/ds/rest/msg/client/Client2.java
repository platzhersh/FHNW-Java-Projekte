package ch.fhnw.ds.rest.msg.client;

import javax.ws.rs.core.MediaType;

import ch.fhnw.ds.rest.msg.data.Msg;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class Client2 {

	public static void main(String[] args) {
		 Client c = Client.create();
		 WebResource r = c.resource("http://localhost:9998/msg");
		 
		 Msg msg = new Msg("Hello from Client2");
		 
		 r.type(MediaType.APPLICATION_XML).put(msg);
	}

}
