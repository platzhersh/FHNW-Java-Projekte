package ch.fhnw.depa;

import ch.fhnw.depa.decorators.QuackableCount;
import ch.fhnw.depa.ducks.Flock;
import ch.fhnw.depa.factory.AbstractDuckFactory;
import ch.fhnw.depa.factory.CountingDuckFactory;


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
		AbstractDuckFactory f = new CountingDuckFactory();
		simulator.simulate(f);
	}
	
	void simulate(AbstractDuckFactory f) {
		Quackable mallardDuck = f.createMallardDuck();
		Quackable rubberDuck = f.createRubberDuck();
		Quackable duckCall = f.createDuckCall();
		
		System.out.println("\nDuck Simulator\n");
		
		Flock flockOfDucks = new Flock();
		
		flockOfDucks.add(mallardDuck);
		flockOfDucks.add(rubberDuck);
		flockOfDucks.add(duckCall);
		
		simulate(flockOfDucks);
		
		
		System.out.println("\n"+QuackableCount.getNumberOfQuacks());
	}
	
	void simulate(Quackable duck) {
		duck.quack();
	}

}
