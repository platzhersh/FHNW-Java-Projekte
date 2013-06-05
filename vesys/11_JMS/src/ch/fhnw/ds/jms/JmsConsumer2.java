package ch.fhnw.ds.jms;

import java.util.Hashtable;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.activemq.ActiveMQConnection;

public class JmsConsumer2 {

	static void startService(String user, String password) throws Exception {
	    Hashtable<String,String> properties = new Hashtable<String,String>();
//		// OpenJMS
//	    properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.exolab.jms.jndi.InitialContextFactory");
//	    properties.put(Context.PROVIDER_URL, "tcp://localhost:3035/");
	    // ActiveMQ
	    properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
	    properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");
	    // properties.put("queue.[jndiName]=[physicalName]
	    properties.put("queue.Q", "test.QUEUE");


	    Context context = new InitialContext(properties);
	    
	    QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
	    Queue queue = (Queue) context.lookup("Q");
	    
	    QueueConnection connection = factory.createQueueConnection(user, password);
	    QueueSession session = connection.createQueueSession(false, QueueSession.CLIENT_ACKNOWLEDGE);
	   
		QueueReceiver receiver = session.createReceiver(queue);
		connection.start();
		System.out.println("JMS consumer is running...");
		
		int counter = 0;
		TextMessage message = (TextMessage)receiver.receive(1000);
		while(message != null){
			counter++;
			System.out.println("consumed message " + message.getText());
			System.out.println("\tgetJMSCorrelationID = " + message.getJMSCorrelationID());
			System.out.println("\tgetJMSDeliveryMode = " + message.getJMSDeliveryMode());
			System.out.println("\tgetJMSDestination = " + message.getJMSDestination());
			System.out.println("\tgetJMSExpiration = " + message.getJMSExpiration());
			System.out.println("\tgetJMSMessageID = " + message.getJMSMessageID());
			System.out.println("\tgetJMSPriority = " + message.getJMSPriority());
			System.out.println("\tgetJMSRedelivered = " + message.getJMSRedelivered());
			System.out.println("\tgetJMSReplyTo = " + message.getJMSReplyTo());
			System.out.println("\tgetJMSTimestamp = " + message.getJMSTimestamp());
			System.out.println("\tgetJMSType = " + message.getJMSType());

			message = (TextMessage)receiver.receive(1000);
		}
		System.out.println(counter + " messages read");
		System.out.println("no further message available");
		
		connection.stop();
		connection.close();
	}
	
	public static void main(String[] args) throws Exception{
		startService(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD);
	}
}
