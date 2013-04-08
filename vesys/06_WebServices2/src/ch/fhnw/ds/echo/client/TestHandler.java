package ch.fhnw.ds.echo.client;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

class TestHandler implements SOAPHandler<SOAPMessageContext> {
	public Set<QName> getHeaders() {
		return null;
	}

	public boolean handleMessage(SOAPMessageContext context) {
		System.out.print("TestHandler.handleMessage: ");
		printMessage(context);
		return true;
	}

	public boolean handleFault(SOAPMessageContext context) {
		System.out.println("TestHandler.handleFault:");
		printMessage(context);
		return true;
	}

	private void printMessage(SOAPMessageContext context) {
		System.out.println("Outbound: "
				+ context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY));
		try {
			context.getMessage().writeTo(System.err);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void close(MessageContext context) {
	}
}
