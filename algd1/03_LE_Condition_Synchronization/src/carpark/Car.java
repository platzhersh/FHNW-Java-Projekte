package carpark;


public class Car extends Thread {
	private CarPark carpark;
	
	public Car(String name, CarPark carpark){
		super(name);
		this.carpark = carpark;
	}
	
	public void run(){
		while(true){
			spendSomeTime(10);
			log("try to enter carpark");
			carpark.enter();	// wait until place is available
			spendSomeTime(20);
			log("try to exit carpark");
			carpark.exit();
		}
	}
	
	private void spendSomeTime(int max){
		try {
			sleep( (int)(Math.random() * max * 1000));
		} catch (InterruptedException e) {
		}
	}
	
	private void log(String msg){
		System.out.println(Thread.currentThread().getName() + " " + msg);
	}


}
