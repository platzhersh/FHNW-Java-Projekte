package jms;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;

public class JMSServer {
	
	/**
	 * @param args
	 * @throws NamingException 
	 */
	public static void main(String[] args) throws NamingException {
		Hashtable<String,String> properties = new Hashtable<>();
		properties.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
				properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");
				properties.put("queue.BANK", "BANK");
				properties.put("topic.BANK.LISTENER", "bank.BANK.LISTENER");
				Context context = new InitialContext(properties);
				QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
				Queue queue = (Queue) context.lookup("BANK");

	}

}
