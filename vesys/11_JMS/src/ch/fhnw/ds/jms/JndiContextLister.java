package ch.fhnw.ds.jms;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;

public class JndiContextLister {
	static String user = "";
	static String password = "";
	
	public static void main(String[] args) throws Exception{
	    Hashtable<String,String> properties = new Hashtable<String,String>();
	    properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
	    properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");
	    properties.put("topic.APPL", "APPL");
	    properties.put("topic.CSCO", "CSCO");
	
	    Context context = new InitialContext(properties);
	    
	    System.out.println("context entries");
	    NamingEnumeration<NameClassPair> it = context.list("");
	    while(it.hasMoreElements()){
	    	System.out.println(it.next());
	    }
	    System.out.println();
	}	    
	
}

