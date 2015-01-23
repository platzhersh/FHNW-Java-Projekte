package ch.fhnw.depa;

/***
 * 
 * Invoker
 * 
 * Holds a task reference which it can remove, override or call
 * 
 * 
 * @author Chregi Glatthard
 *
 */
public class Scheduler {
	
	AbstractTask task;
	
	public Scheduler() {
		empty();
	}
	
	public void empty() {
		task = new NullTask();
	}
	
	public void registerTask(AbstractTask st) {
		task = st;
	}
	
	public void runTask() {
		if (task.ready) task.execute();
	}

}
