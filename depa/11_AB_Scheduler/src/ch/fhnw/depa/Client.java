package ch.fhnw.depa;

/***
 * 
 * Client
 * 
 * Creates and registers Commands and also tells the Scheduler to run them
 * 
 * 
 * @author Chregi Glatthard
 *
 */
public class Client {

	public static void main(String[] args) {
		HelloWorldTask t1 = new HelloWorldTask();
		Scheduler s = new Scheduler();
		
		s.runTask();
		
		s.registerTask(t1);
		s.runTask();
		
		t1.setAborted();
		s.runTask();
	}

}
