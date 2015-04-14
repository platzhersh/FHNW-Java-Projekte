package blockingQueue;

public class OrderProcessing {
	
	public static void main(String[] args) {
		int nCustomers = 10;
		int nValidators = 2;
		int nProcessors = 3;

		for (int i = 0; i < nCustomers; i++) {
			new Customer("" + i /* TODO */).start();
		}

		for (int i = 0; i < nValidators; i++) {
			new OrderValidator(/* TODO */).start();
		}

		for (int i = 0; i < nProcessors; i++) {
			new OrderProcessor(/* TODO */).start();
		}
	}
	
	static class Order {
		public final String customerName;
		public final int itemId;
		public Order(String customerName, int itemId) {
			this.customerName = customerName;
			this.itemId = itemId;
		}
		
		@Override
		public String toString() {
			return "Order: [name = " + customerName + " ], [item = " + itemId +" ]";  
		}
	}
	
	
	static class Customer extends Thread {

		public Customer(String name /*TODO */) {
			super(name);
		}
		
		private Order createOrder() {
			Order o = new Order(getName(), (int) (Math.random()*100));
			System.out.println("Created:   " + o);
			return o;
		}
		
		private void handOverToValidator(Order o) throws InterruptedException {
			//TODO
		}
		
		@Override
		public void run() {
			try {
				while(true) {
					Order o = createOrder();
					handOverToValidator(o);
					Thread.sleep((long) (Math.random()*1000));
				}
			} catch (InterruptedException e) {}
		}
	}
	
	
	static class OrderValidator extends Thread {

		public OrderValidator(/*TODO */) {}
		
		public Order getNextOrder() throws InterruptedException {
			return null; //TODO
		}
		
		public boolean isValid(Order o) {
			return o.itemId < 50;
		}
		
		public void handOverToProcessor(Order o) throws InterruptedException {
			//TODO
		}
		
		@Override
		public void run() {
			try {
				while(true) {
					Order o = getNextOrder();
					if(isValid(o)) {
						handOverToProcessor(o);
					} else {
						System.err.println("Destroyed: " + o);
					}
					Thread.sleep((long) (Math.random()*1000));
				}
			} catch (InterruptedException e) {}
		}
	}
	
	
	static class OrderProcessor extends Thread {

		public OrderProcessor(/*TODO */) {}
		
		public Order getNextOrder() throws InterruptedException {
			return null; //TODO
		}
		
		public void processOrder(Order o) {
			System.out.println("Processed: " + o);
		}
		
		@Override
		public void run() {
			try {
				while(true) {
					Order o = getNextOrder();
					processOrder(o);
					Thread.sleep((long) (Math.random()*1000));
				}
			} catch (InterruptedException e) {}
		}
	}
}
