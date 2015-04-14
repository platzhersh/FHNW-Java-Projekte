package latch;

import java.util.concurrent.CountDownLatch;

public class Restaurant {
		
		public final CountDownLatch cookingSignal;
		public final CountDownLatch dishesSignal; 
		public final int nrGuests;
		
		public Restaurant(int nrOfGuests) {
			nrGuests = nrOfGuests;
			cookingSignal = new CountDownLatch(1);
			dishesSignal = new CountDownLatch(nrGuests);
		}
		
	public static void main(String[] args) {
		
		Restaurant r = new Restaurant(2);
		
		new Cook(r.cookingSignal).start();
		
		for(int i = 0; i < r.nrGuests; i++) {
			new Guest(r.cookingSignal, r.dishesSignal).start();
		}
		
		new DishWasher(r.dishesSignal).start();
		
		new Guest(r.cookingSignal, r.dishesSignal).start();
	}
	
	
	static class Cook extends Thread {
		CountDownLatch cooking;
		public Cook(CountDownLatch cdl) { cooking = cdl; }
		
		@Override
		public void run() {
			System.out.println("Start Cooking..");
			try {
				sleep(5000);
			} catch (InterruptedException e) {}
			System.out.println("Meal is ready");
			/* count down cookingSignal */
			cooking.countDown();
		}
	}
	
	
	static class Guest extends Thread {
		CountDownLatch cooking, dishes;
		public Guest(CountDownLatch cdl1, CountDownLatch cdl2) { cooking = cdl1; dishes = cdl2; }
		
		@Override
		public void run() {
			try {
				sleep(1000);
				System.out.println("Entering restaurant and placing order.");
				/* waiting until meal is ready */
				cooking.await();
				
				System.out.println("Enjoying meal.");
				sleep(5000);
				
				System.out.println("Meal was excellent!");
				dishes.countDown();
				
			} catch (InterruptedException e) {}
		}
	}
	
	
	static class DishWasher extends Thread {
		CountDownLatch dishes;
		public DishWasher(CountDownLatch cdl) { dishes = cdl; }
		
		@Override
		public void run() {
			try {
				
				System.out.println("Waiting for dirty dishes.");
				dishes.await();
				
				System.out.println("Washing dishes.");
				sleep(0);
			} catch (InterruptedException e) {}
		}
	}
}
