package patterns.observer.bag;

public class AdderObserver implements Observer {

	@Override
	public void update(Observable source) {
		IntegerBag bag = (IntegerBag) source;

		int sum = 0;
		for (int n : bag.getValues()) {
			sum += n;
		}

		System.out.println("Content has changed: new sum is " + sum);
	}

}
