package ch.fhnw.depa;

/***
 * 
 * Null Command
 * 
 * uses Null Object pattern
 * 
 * 
 * @author Chregi Glatthard
 *
 */
public class NullTask extends AbstractTask {

	
	@Override
	public void execute() {
		System.out.println("Task queue empty");
	}

}
