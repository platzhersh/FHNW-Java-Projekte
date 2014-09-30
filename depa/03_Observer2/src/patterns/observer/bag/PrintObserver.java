package patterns.observer.bag;

public class PrintObserver implements Observer {

	@Override
	public void update(Observable source) {
		IntegerBag bag = (IntegerBag) source;
		System.out.print("Content has changed: [ ");
		for (int x : bag.getValues()) {
			System.out.print(x + " ");
		}
		System.out.println("]");
	}
}
