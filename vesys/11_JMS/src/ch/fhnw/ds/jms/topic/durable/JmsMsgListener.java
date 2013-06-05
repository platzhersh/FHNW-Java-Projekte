package ch.fhnw.ds.jms.topic.durable;

import java.util.Hashtable;

import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JmsMsgListener {

	static String user = "";
	static String password = "";
	
	public static void main(String[] args) throws Exception{
	    Hashtable<String,String> properties = new Hashtable<String,String>();
	    properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
	    properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");
	    properties.put("topic.TOPIC", "durable/Example");
	    	
	    Context context = new InitialContext(properties);
	    
	    TopicConnectionFactory factory = (TopicConnectionFactory) context.lookup("ConnectionFactory");
	    Topic topic = (Topic) context.lookup("TOPIC");
	    
	    System.out.println("topic.getTopicName() = " + topic.getTopicName());
	    System.out.println("topic.toString() = " + topic.toString());
	    
	    TopicConnection connection = factory.createTopicConnection(user, password);
	    connection.setClientID(args[0]);
		TopicSession session = connection.createTopicSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		TopicSubscriber subscriber = session.createDurableSubscriber(topic, args[0]);

		connection.start();
		
		System.out.println("Topic subscriber is listening...");
		while(true){
			TextMessage request = (TextMessage)subscriber.receive();	// messageListener AND receive exclude each other
			System.out.println("Handle: "+request.getText());
		}
	}
	
}


