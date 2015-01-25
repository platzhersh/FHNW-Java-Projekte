package ch.fhnw.depa;

import ch.fhnw.depa.decorators.QuackableCount;
import ch.fhnw.depa.ducks.Flock;
import ch.fhnw.depa.factory.AbstractDuckFactory;
import ch.fhnw.depa.factory.NotifyCountingDuckFactory;
import ch.fhnw.depa.observe.Observer;
import ch.fhnw.depa.observe.Quackologist;


/***
 * Duck Reunion Project by Head First Design Patterns book
 * 
 * Project combines multiple design patterns:
 * - Decorator
 * - Factory 
 * - Composite
 * - Observer (not completely implemented)
 * @author chregi
 *
 */
public class DuckSimulator {
	
	public static void main(String[] args) {
		DuckSimulator simulator = new DuckSimulator();
		AbstractDuckFactory f = new NotifyCountingDuckFactory();
		simulator.simulate(f);	
	}
	
	void simulate(AbstractDuckFactory f) {
		AbstractQuackable mallardDuck = f.createMallardDuck();
		AbstractQuackable rubberDuck = f.createRubberDuck();
		AbstractQuackable duckCall = f.createDuckCall();
		
		System.out.println("\nDuck Simulator\n");
		
		
		Observer q1 = new Quackologist();
		Flock flockOfDucks = new Flock();
		
		flockOfDucks.add(mallardDuck);
		flockOfDucks.add(rubberDuck);
		flockOfDucks.add(duckCall);
		
		
		flockOfDucks.registerObserver(q1);
		simulate(flockOfDucks);
		
		
		System.out.println("\n"+QuackableCount.getNumberOfQuacks());
	}
	
	void simulate(AbstractQuackable duck) {
		duck.quack();
	}

}
