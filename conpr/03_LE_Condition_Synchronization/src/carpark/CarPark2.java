package carpark;


public class CarPark2 implements CarPark {
	private int places;
	
	public CarPark2(int places){
		this.places = places;
	}
	
	private synchronized boolean isFull(){
		return places == 0;
	}
	private synchronized void decPlaces(){
		places--;
		System.out.println("places: "+places);
	}
	private synchronized void incPlaces(){
		places++;
	}

	@Override
	public void enter() {
		while(isFull()) {} // busy waiting
		log("enter carpark");
		decPlaces();
	}

	@Override
	public synchronized void exit() {
		log("exit carpark");
		incPlaces();
	}

	public static void main(String[] args){
		CarPark cp = new CarPark2(2);
		Car c1 = new Car("car1", cp); c1.start();
		Car c2 = new Car("car2", cp); c2.start();
		Car c3 = new Car("car3", cp); c3.start();
		Car c4 = new Car("car4", cp); c4.start();
	}
	
	private void log(String msg){
		System.out.println(Thread.currentThread().getName() + " " + msg);
	}
}
