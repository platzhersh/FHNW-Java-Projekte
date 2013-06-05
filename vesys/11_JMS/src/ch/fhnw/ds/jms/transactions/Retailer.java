package ch.fhnw.ds.jms.transactions;
/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
import java.util.Hashtable;
import java.util.UUID;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * The Retailer orders computers from the Vendor by sending a message via
 * the VendorOrderQueue. It then syncronously receives the reponse message
 * and reports if the order was successful or not.
 */
public class Retailer implements Runnable, MessageListener {
	private final String queue;
	private final Connection connection;
	
	public Retailer(String queue, ConnectionFactory factory) throws JMSException {
		this.queue = queue;
		this.connection = factory.createConnection();
	}
	
	public void run() {
		try {
			// The Retailer's session is non-trasacted.
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination vendorOrderQueue = session.createQueue(queue);
			TemporaryQueue retailerConfirmQueue = session.createTemporaryQueue();
			
			MessageProducer producer = session.createProducer(vendorOrderQueue);
			MessageConsumer replyConsumer = session.createConsumer(retailerConfirmQueue);

			connection.start();
			
			replyConsumer.setMessageListener(this);

			for (int i = 0; i < 5; i++) {
				MapMessage message = session.createMapMessage();
				message.setString("Item", "Computer");
				message.setString("OrderId", UUID.randomUUID().toString());
				// int quantity = (int)(Math.random() * 4) + 1;
				int quantity = i+1;
				message.setInt("Quantity", quantity);
				message.setJMSReplyTo(retailerConfirmQueue);
				producer.send(message);
				log("Ordered %d computers, ID=%s", quantity, message.getString("OrderId"));
				
				// sender waits synchronous for a response
//				MapMessage reply = (MapMessage) replyConsumer.receive();
//				if (reply.getBoolean("OrderAccepted")) {
//					System.out.println("Retailer: Order Filled");
//				} else {
//					System.out.println("Retailer: Order Not Filled");
//				}
			}
			
			// Send a non-MapMessage to signal the end
			Message close = session.createMessage();
			close.setJMSReplyTo(retailerConfirmQueue);
			producer.send(close);
			
			//replyConsumer.close();
			//connection.close();
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onMessage(Message m) {
		try {
			if (m instanceof MapMessage) {
				MapMessage reply = (MapMessage) m;
				String id = reply.getString("OrderId");
				if (reply.getBoolean("OrderAccepted")) {
					log("Order %s Filled", id);
				} else {
					log("Order %s Not Filled", id);
				}
			} else {
				connection.close();
			}
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void log(String format, Object... args) {
		int threadId = System.identityHashCode(Thread.currentThread());
		System.out.printf("Retailer: ["+threadId+"] " + format + "\n", args);
	}

	public static void main(String[] args) throws Exception {
		Hashtable<String, String> properties = new Hashtable<String, String>();
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");
		Context jndiContext = new InitialContext(properties);
		
		ConnectionFactory factory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");
		Retailer r = new Retailer("VendorOrderQueue", factory);
		new Thread(r, "Retailer").start();
	}

}