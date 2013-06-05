package ch.fhnw.ds.jms.echo;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JmsEchoServer {

	static String user = "admin";
	static String password = "openjms";
	
	public static void main(String[] args) throws Exception{
	    Hashtable<String,String> properties = new Hashtable<String,String>();
		// OpenJMS
//	    properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.exolab.jms.jndi.InitialContextFactory");
//	    properties.put(Context.PROVIDER_URL, "tcp://localhost:3035/");
	    // ActiveMQ
	    properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
	    properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");
	    // register some queues in JNDI using the form
	    // queue.[jndiName] = [physicalName]
	    properties.put("queue.ECHO", "example.ECHO");
	    	
	    Context context = new InitialContext(properties);
	    
	    QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
	    Queue queue = (Queue) context.lookup("ECHO");
	    
	    QueueConnection connection = factory.createQueueConnection(user, password);
	    QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
	    
		QueueReceiver receiver = session.createReceiver(queue);
		connection.start();
		System.out.println("Echo service is running...");
		
		while(true){
			TextMessage request = (TextMessage)receiver.receive();
			Queue replyQueue = (Queue) request.getJMSReplyTo();
			TextMessage response = session.createTextMessage();
			response.setText("Echo: "+request.getText());
			System.out.println("Handle: "+request.getText());
			if(replyQueue != null){
				try {
					session.createSender(replyQueue).send(response);
				}
				catch(JMSException e){
					System.out.println(">> Error: " + e.getMessage());
				}
			}
		}
		
	}
	
}
