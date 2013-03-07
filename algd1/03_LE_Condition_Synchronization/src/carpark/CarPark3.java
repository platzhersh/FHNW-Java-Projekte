package carpark;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.jcip.annotations.GuardedBy;


public class CarPark3 implements CarPark {
	private Lock lock = new ReentrantLock();
	
	@GuardedBy("lock")
	private int places;
	
	public CarPark3(int places){
		this.places = places;
	}
	
	private boolean isFull(){
		return places == 0;
	}
	
	private void decPlaces(){
		places--;
		System.out.println("places: "+places);
	}
	
	private void incPlaces(){
		places++;
	}

	@Override
	public void enter() {
		boolean locked = false;
		lock.lock();
		locked = true;
		try {
			while (isFull()) {
				locked = false;
				lock.unlock();
				sleep(10);
				lock.lock(); 
				locked = true;
			}
			log("enter carpark");
			decPlaces();
		} finally {
			if (locked)
				lock.unlock();
		}
	}

	@Override
	public void exit() {
		lock.lock();
		try {
			log("exit carpark");
			incPlaces();
		} finally {
			lock.unlock();
		}
	}

	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args){
		CarPark cp = new CarPark3(2);
		Car c1 = new Car("car1", cp); c1.start();
		Car c2 = new Car("car2", cp); c2.start();
		Car c3 = new Car("car3", cp); c3.start();
		Car c4 = new Car("car4", cp); c4.start();
	}
	
	private void log(String msg){
		System.out.println(Thread.currentThread().getName() + " " + msg);
	}
}
