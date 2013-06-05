package ch.fhnw.ds.jms.topic.durable;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.QueueSession;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JmsMsgUnsubscribe {

	static String user = "";
	static String password = "";
	
	public static void main(String[] args) throws Exception{
	    Hashtable<String,String> properties = new Hashtable<String,String>();
	    properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
	    properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");
	    properties.put("topic.TOPIC", "durable/Example");
	    	
	    Context context = new InitialContext(properties);
	    
	    TopicConnectionFactory factory = (TopicConnectionFactory) context.lookup("ConnectionFactory");
	    TopicConnection connection = factory.createTopicConnection(user, password);
	    connection.setClientID(args[0]);
		connection.start();
		TopicSession session = connection.createTopicSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		try {
			session.unsubscribe(args[0]);
		} catch(JMSException e) {
			System.out.println(e.getMessage());
		}
		connection.close();
	}
	
}

