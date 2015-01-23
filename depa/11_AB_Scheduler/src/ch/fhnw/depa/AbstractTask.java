package ch.fhnw.depa;

/***
 * 
 * Command
 * 
 * Specification of the Command interface
 * 
 * 
 * @author Chregi Glatthard
 *
 */
public abstract class AbstractTask {
	
	boolean ready = true;
	
	public abstract void execute();
	
	public void setReady() {
		ready = true;
	}
	
	public void setAborted() {
		ready = false;
	}

}
