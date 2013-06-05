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
import java.util.Random;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * The Supplier synchronously receives the order from the Vendor and
 * randomly responds with either the number ordered, or some lower
 * quantity. 
 */
public class Supplier implements Runnable {
	private ConnectionFactory factory;
	private final String item;
	private final String queue;
	
	public Supplier(String item, String queue, ConnectionFactory factory) {
		this.item = item;
		this.queue = queue;
		this.factory = factory;
	}

	public void run() {
		try {
			Connection connection = factory.createConnection();
			
			Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
			Queue orderQueue = session.createQueue(queue);
			MessageConsumer consumer = session.createConsumer(orderQueue);
			
			connection.start();
			
			while (true) {
				Message message = consumer.receive();
				MessageProducer producer = session.createProducer(message.getJMSReplyTo());
				MapMessage orderMessage;
				if (message instanceof MapMessage) {
					orderMessage = (MapMessage) message;
				} else {
					// End of Stream
					producer.send(session.createMessage());
					session.commit();
					producer.close();
					break;
				}
				
				int quantity = orderMessage.getInt("Quantity");
				log("Vendor ordered %d %s", quantity, orderMessage.getString("Item"));
				
				MapMessage outMessage = session.createMapMessage();
				outMessage.setInt("VendorOrderNumber", orderMessage.getInt("VendorOrderNumber"));
				outMessage.setString("Item", item);
				
				// delivers the requested amount of items or less, depending on stock
				quantity = Math.min(
						orderMessage.getInt("Quantity"),
						new Random().nextInt(orderMessage.getInt("Quantity") * 10)
				);
				outMessage.setInt("Quantity", quantity);
				
				producer.send(outMessage);
				log("Sent %d %s(s)", quantity, item);
//				if(System.currentTimeMillis() % 2 == 0) {
//					session.commit();
//					log("committed transaction");
//				}
//				else{
//					session.rollback();
//					log("rollbacked transaction");
//				}
				session.commit();
				producer.close();
			}
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	private void log(String format, Object... args) {
		System.out.printf(item + " Supplier: " + format + "\n", args);
	}
	
	public static void main(String[] args) throws Exception {
		Hashtable<String, String> properties = new Hashtable<String, String>();
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");
		Context jndiContext = new InitialContext(properties);
		ConnectionFactory factory = (ConnectionFactory) jndiContext	.lookup("ConnectionFactory");

		String item = args[0];
		String queue;
		if ("HardDrive".equals(item)) {
			queue = "StorageOrderQueue";
		} else if ("Monitor".equals(item)) {
			queue = "MonitorOrderQueue";
		} else {
			throw new IllegalArgumentException("Item must be either HardDrive or Monitor");
		}
		
		Supplier s = new Supplier(item, queue, factory);
		new Thread(s, "Supplier " + item).start();
		System.out.println(item + " Supplier started");
	}
}