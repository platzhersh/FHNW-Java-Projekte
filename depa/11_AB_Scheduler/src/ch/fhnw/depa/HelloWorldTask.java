package ch.fhnw.depa;

/***
 * 
 * ConcreteCommand
 * 
 * Concrete command that can be registered in the scheduler
 * 
 * 
 * @author Chregi Glatthard
 *
 */
public class HelloWorldTask extends AbstractTask {

	@Override
	public void execute() {
		System.out.println("Hello World");
	}

	
	
}
