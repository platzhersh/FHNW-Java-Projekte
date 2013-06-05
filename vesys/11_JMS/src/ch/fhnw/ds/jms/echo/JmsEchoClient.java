package ch.fhnw.ds.jms.echo;

import java.util.Date;
import java.util.Hashtable;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JmsEchoClient {
	static String user = "";
	static String password = "";
	
	public static void main(String[] args) throws Exception{
	    Hashtable<String,String> properties = new Hashtable<String,String>();
		// OpenJMS
//	    properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.exolab.jms.jndi.InitialContextFactory");
//	    properties.put(Context.PROVIDER_URL, "tcp://localhost:3035/");
	    // ActiveMQ
	    properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
	    properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");
	    properties.put("queue.ECHO", "example.ECHO");
	    Context context = new InitialContext(properties);
	    
	    QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
	    Queue queue = (Queue) context.lookup("ECHO");
	    
	    final QueueConnection connection = factory.createQueueConnection(user, password);
	    QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
	    
	    QueueSender sender = session.createSender(queue);
	    
	    TemporaryQueue tempQueue = session.createTemporaryQueue();
	    QueueReceiver receiver = session.createReceiver(tempQueue);
		connection.start();
		
		TextMessage request = session.createTextMessage();
		request.setText("Hello World [" + new Date() + "]");		
		request.setJMSReplyTo(tempQueue);
		sender.send(request);
		
		TextMessage response = (TextMessage) receiver.receive();
		System.out.println("Response: " + response.getText());
		connection.close();
		
//		receiver.setMessageListener(new MessageListener(){
//			public void onMessage(Message m) {
//				try {
//					System.out.println("Response: " + ((TextMessage)m).getText());
//					connection.close();
//				} catch (JMSException e) {
//					e.printStackTrace();
//				}
//			}
//			
//		});
		
	}
	
}
