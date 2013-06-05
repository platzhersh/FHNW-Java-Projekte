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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

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
 * The Vendor synchronously, and in a single transaction, receives the
 * order from VendorOrderQueue and sends messages to the two Suppliers via
 * MonitorOrderQueue and StorageOrderQueue.
 * The responses are received asynchronously; when both responses come
 * back, the order confirmation message is sent back to the Retailer.
 */
public class Vendor implements Runnable, MessageListener {
	private ConnectionFactory factory;
	private final String vendorOrderQueueName;
	private final String monitorOrderQueueName;
	private final String storageOrderQueueName;
	
	public Vendor(String vendorOrderQueueName, String monitorOrderQueueName, String storageOrderQueueName, ConnectionFactory factory) {
		this.vendorOrderQueueName = vendorOrderQueueName;
		this.monitorOrderQueueName = monitorOrderQueueName;
		this.storageOrderQueueName = storageOrderQueueName;
		this.factory = factory;
	}

	private	Session asyncSession;
	private final CountDownLatch latch = new CountDownLatch(2);
	
	public void run() {
		Session session = null;
		Destination orderQueue;
		Destination monitorOrderQueue;
		Destination storageOrderQueue;
		TemporaryQueue vendorConfirmQueue;
		MessageConsumer orderConsumer = null;
		MessageProducer monitorProducer = null;
		MessageProducer storageProducer = null;

		try {
			Connection connection = factory.createConnection();

			session = connection.createSession(true, Session.SESSION_TRANSACTED);
			orderQueue = session.createQueue(vendorOrderQueueName);
			monitorOrderQueue = session.createQueue(monitorOrderQueueName);
			storageOrderQueue = session.createQueue(storageOrderQueueName);
			orderConsumer = session.createConsumer(orderQueue);
			monitorProducer = session.createProducer(monitorOrderQueue);
			storageProducer = session.createProducer(storageOrderQueue);
			
			asyncSession = connection.createSession(true, Session.SESSION_TRANSACTED);
			vendorConfirmQueue = asyncSession.createTemporaryQueue();
			MessageConsumer confirmConsumer = asyncSession.createConsumer(vendorConfirmQueue);
			confirmConsumer.setMessageListener(this);
			
			connection.start();
		
			Message closeMessage = null;
			
			while (true) {
				try {
					Message inMessage = orderConsumer.receive();
					MapMessage message;
					if (inMessage instanceof MapMessage) {
						message = (MapMessage) inMessage;
					} else {
						// end of stream
						closeMessage = inMessage;
						Message outMessage = session.createMessage();
						outMessage.setJMSReplyTo(vendorConfirmQueue);
						monitorProducer.send(outMessage);
						storageProducer.send(outMessage);
						session.commit();
						break;
					}
					
					Order order = new Order(message);
					
					MapMessage orderMessage = session.createMapMessage();
					orderMessage.setJMSReplyTo(vendorConfirmQueue);
					orderMessage.setInt("VendorOrderNumber", order.getOrderNumber());
					int quantity = message.getInt("Quantity");
					String id = message.getString("OrderId");
					log("Retailer ordered %d %s in Order %s", quantity, message.getString("Item"), id);
					
					orderMessage.setInt("Quantity", quantity);
					orderMessage.setString("Item", "Monitor");
					monitorProducer.send(orderMessage);
					log("ordered %d Monitor(s)", quantity);
					
					orderMessage.setString("Item", "HardDrive");
					storageProducer.send(orderMessage);
					log("ordered %d Hard Drive(s)", quantity);

					// Randomly throw an exception in here to simulate a Database error
					// and trigger a rollback of the transaction
					if (new Random().nextInt(3) == 0) {
						order.remove();
						throw new JMSException("Simulated Database Error.");
					}
					session.commit();
					log("Committed Transaction 1");
				} catch (JMSException e) {
					log("JMSException Occured: " + e.getMessage());
					e.printStackTrace();
					session.rollback();
					log("Rolled Back Transaction.");
				}
			}

			// wait until the two acknowledge messages arrive
			while(true) {
				try {
					latch.await();
					break;
				} catch (InterruptedException e) {
				}
			}
			
			MessageProducer p = asyncSession.createProducer(closeMessage.getJMSReplyTo());
			p.send(asyncSession.createMessage());
			asyncSession.commit();
			
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	public void onMessage(Message message) {
		if (!(message instanceof MapMessage)) {
			latch.countDown();
			try {
				asyncSession.commit();
			} catch (JMSException e) {
				e.printStackTrace();
			}
			return;
		}
		
		try {
			MapMessage componentMessage = (MapMessage) message;
			
			int orderNumber = componentMessage.getInt("VendorOrderNumber");
			Order order = Order.getOrder(orderNumber);
			order.processSubOrder(componentMessage);
			asyncSession.commit();
			
			if (! "Pending".equals(order.getStatus())) {
				log("Completed processing for order %d of retailer order %s", orderNumber, order.getRetailerOrderId());
				
				MessageProducer replyProducer = asyncSession.createProducer(order.getMessage().getJMSReplyTo());
				MapMessage replyMessage = asyncSession.createMapMessage();
				replyMessage.setString("OrderId", order.getMessage().getString("OrderId"));
				if ("Fulfilled".equals(order.getStatus())) {
					replyMessage.setBoolean("OrderAccepted", true);
					log("sent %d computer", order.quantity);
				} else {
					replyMessage.setBoolean("OrderAccepted", false);
					log("unable to send %d computer", order.quantity);
				}
				replyProducer.send(replyMessage);
				asyncSession.commit();
				log("committed transaction 2");
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	private void log(String format, Object... args) {
		int threadId = System.identityHashCode(Thread.currentThread());
		System.out.printf("Vendor: ["+threadId+"] " + format + "\n", args);
	}

	public static class Order {
		private static Map<Integer, Order> pendingOrders = new HashMap<Integer, Order>();
		private static int nextOrderNumber = 1;

		private int orderNumber;
		private int quantity;
		private String retailerOrderId;
		private MapMessage monitor = null;
		private MapMessage storage = null;
		private MapMessage message;
		private String status;
		
		public Order(MapMessage message) {
			this.orderNumber = nextOrderNumber++;
			this.message = message;
			try {
				this.quantity = message.getInt("Quantity");
				this.retailerOrderId = message.getString("OrderId");
			} catch (JMSException e) {
				throw new RuntimeException(e);
			}
			status = "Pending";
			pendingOrders.put(orderNumber, this);
		}
		
		public void remove() { pendingOrders.remove(orderNumber); }
		
		public Object getStatus() { return status; }
		public MapMessage getMessage() { return message; }
		public int getOrderNumber() { return orderNumber; }
		public String getRetailerOrderId() { return retailerOrderId; }
		
		public static Order getOrder(int number) {
			return pendingOrders.get(number);
		}
		
		public void processSubOrder(MapMessage message) {
			String itemName = null;
			try {
				itemName = message.getString("Item");
			} catch (JMSException e) {
				e.printStackTrace();
			}
			
			if ("Monitor".equals(itemName)) {
				monitor = message;
			} else if ("HardDrive".equals(itemName)) {
				storage = message;
			}
			
			if (null != monitor && null != storage) {
				// Received both messages
				try {
					if (quantity > monitor.getInt("Quantity")) {
						status = "Cancelled";
					} else if (quantity > storage.getInt("Quantity")) {
						status = "Cancelled";
					} else {
						status = "Fulfilled";
					}
				} catch (JMSException e) {
					e.printStackTrace();
					status = "Cancelled";
				}
			}
		}		
	}

	public static void main(String[] args) throws Exception {
		Hashtable<String, String> properties = new Hashtable<String, String>();
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");

		Context jndiContext = new InitialContext(properties);
		ConnectionFactory factory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");

		Vendor v = new Vendor("VendorOrderQueue", "MonitorOrderQueue", "StorageOrderQueue", factory);
		new Thread(v, "Vendor").start();
	}	
}