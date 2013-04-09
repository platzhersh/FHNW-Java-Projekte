package ch.fhnw.ds.rest.msg.resources;

/*
 Resource /msg
 curl -X GET -i http://localhost:9998/msg
 curl -X GET -i -H "Accept: text/plain"         http://localhost:9998/msg
 curl -X GET -i -H "Accept: application/xml"    http://localhost:9998/msg
 curl -X GET -i -H "Accept: application/json"   http://localhost:9998/msg

 curl -X DELETE   http://localhost:9998/msg

 curl -X PUT -i -H "Content-Type: application/json" --data "{\"text\":\"JSON message\"}" http://localhost:9998/msg
 curl -X PUT -i -H "Content-Type: application/xml"  --data "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><msg><text>XML message</text></msg>" http://localhost:9998/msg
 curl -X PUT -i -H "Content-Type: text/plain" --data "msg=Hallo TextPlain" http://localhost:9998/msg
 curl -X PUT -i     --data msg=Hallo http://localhost:9998/msg
 
 curl -X POST -i -H "Content-Type: application/json" --data "{\"text\":\"json\"}" http://localhost:9998/msg


 Resource /msg/{id}
 curl -X GET -i http://localhost:9998/msg/Dominik
 curl -X GET -i http://localhost:9998/msg/Dominik/headers

 */

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import ch.fhnw.ds.rest.msg.data.Msg;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/msg")
public class MsgResource {
	private String msg = "Hello, world!";

	public MsgResource() {
		System.out.println("MsgResource() called");
	}

	// GET on /msg
	// ===========

	@GET
	@Produces("text/plain")
	public String getPlain() {
		return msg + "\n";
	}

	@GET
	@Produces("text/html")
	public String getHtml() {
		StringBuffer buf = new StringBuffer();
		buf.append("<html><body><h1>Message Text</h1>" + msg + "<br>");
		buf.append("<form method=\"POST\" action=\"/msg\">");
		buf.append("<p>Text: <input name=\"msg\" type=\"text\" size=20/>");
		buf.append("<input type=\"submit\" value=\"Submit\" />");
		buf.append("</form>");
		buf.append("</body></html>");
		return buf.toString();
	}

	@GET
	@Produces("text/xml")
	public String getSimpleXml() {
		return "<string>" + msg + "</string>";
	}

	@GET
	@Produces( { "application/json", "application/xml" })
	public Msg getXml() {
		return new Msg(msg);
	}

	// PUT on /msg
	// ===========

	@PUT
	@Consumes("text/plain")
	public void setTextPlain(String new_msg) {
		msg = new_msg;
	}

	@PUT
	@Consumes( { "application/json", "application/xml" })
	public void setTextXml(Msg message) {
		msg = message.getText();
	}

	@PUT
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/xml")
	public String setTextForm(@FormParam("msg") String new_msg) {
		msg = new_msg;

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		XMLEncoder enc = new XMLEncoder(stream);
		enc.writeObject(new_msg);
		enc.close();
		return new String(stream.toByteArray()) + "\n";
	}

	// POST on /msg (used for forms)
	// =============================

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/html")
	public String doPost(@FormParam("msg") String new_msg) {
		msg = new_msg;
		return getHtml();
	}

	@POST
	@Consumes( { "application/xml", "application/json" })
	public Response createNewMessage(@Context UriInfo uriInfo, Msg message) {
		URI location = uriInfo.getAbsolutePathBuilder().path(message.getText())
				.build();
		return Response.created(location).build();
	}

	// DELETE on /msg
	// ==============

	@DELETE
	@Produces("text/plain")
	public String onDelete() {
		msg = null;
		return "Message deleted.\n";
	}

	// GET on /msg/{id} and on /msg/{id}/headers
	// =========================================

	@GET
	@Produces("text/plain")
	@Path("{id}")
	public String readDetailsInfo(@PathParam("id") String path) {
		return msg + ": " + path + "\n";
	}

	@GET
	@Produces("text/plain")
	@Path("{id}/headers")
	public String readDetailHeaders(@PathParam("id") String path,
			@Context HttpHeaders headers) {
		StringBuffer buf = new StringBuffer();
		buf.append("Headers of request " + path + "\n\n");
		MultivaluedMap<String, String> map = headers.getRequestHeaders();
		for (String key : map.keySet()) {
			buf.append(key + ": " + map.getFirst(key) + "\n");
		}
		return buf.toString();
	}

}
