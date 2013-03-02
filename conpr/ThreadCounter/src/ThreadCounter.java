
public class ThreadCounter implements Runnable {

	private String name;
	private static int i = 0;
	
	public ThreadCounter(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
	while (i <= 20)
		synchronized(getClass()) {
			System.out.println(this.name +": "+ i++);
		} 
	}

}
