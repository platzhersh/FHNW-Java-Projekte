package carpark;


public class CarPark1 implements CarPark {
	private int places;
	
	public CarPark1(int places){
		this.places = places;
	}

	@Override
	public synchronized void enter() {
		while(places == 0) {} // busy waiting
		log("enter carpark");
		places --;
	}

	@Override
	public synchronized void exit() {
		log("exit carpark");
		places ++;
	}

	public static void main(String[] args){
		CarPark cp = new CarPark1(2);
		Car c1 = new Car("car1", cp); c1.start();
		Car c2 = new Car("car2", cp); c2.start();
		Car c3 = new Car("car3", cp); c3.start();
		Car c4 = new Car("car4", cp); c4.start();
	}
	
	private void log(String msg){
		System.out.println(Thread.currentThread().getName() + " " + msg);
	}
}
