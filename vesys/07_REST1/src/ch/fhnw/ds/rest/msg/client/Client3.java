package ch.fhnw.ds.rest.msg.client;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

public class Client3 {

	public static void main(String[] args) {
		 Client c = Client.create();
		 WebResource r = c.resource("http://localhost:9998/msg");
		 
		 Form f = new Form();
		 f.add("msg", "Hello from Client3");

		 String result = r.accept(MediaType.TEXT_HTML).post(String.class, f);
		 System.out.println(result);
	}

}
