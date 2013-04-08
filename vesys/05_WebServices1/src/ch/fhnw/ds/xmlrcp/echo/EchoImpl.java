package ch.fhnw.ds.xmlrcp.echo;

import java.util.Date;

public class EchoImpl {//implements Echo {

	public String getEcho(String name) {
		if(name.equals("ex")) throw new RuntimeException("argument must not be `ex`");
		return "[XML-RPC] Hello "+name+", welcome to XML-RPC";
	}

	public String getEchoWithDate(String name) {
		return String.format("[XML-RPC: %tT] Hello %s, welcome to XML-RPC", new Date(), name);
	}
	
}
