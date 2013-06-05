package ch.fhnw.ds.jms.topic.durable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Hashtable;

import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JmsMsgPublisher {
	
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
	    TopicSession session = connection.createTopicSession(false, QueueSession.AUTO_ACKNOWLEDGE);
	    
	    Topic topic = (Topic) context.lookup("TOPIC");
		TopicPublisher publisher = session.createPublisher(topic);
		TextMessage request = session.createTextMessage();

		connection.start();
		System.out.println("Topic publisher started...");
		
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		String line = r.readLine();
		while(line != null && !"".equals(line.trim())) {
			request.setText("[" + new Date() + "] "+line);
			publisher.send(request);
			line = r.readLine();
		}
		
		connection.close();
		
	}
	
}
