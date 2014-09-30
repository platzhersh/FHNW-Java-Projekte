package patterns.observer.bag;

public class ObserverTest {
	
	public static void main(String[] args) {
		IntegerBag bag = new IntegerBag();

		PrintObserver po = new PrintObserver();
		AdderObserver ao = new AdderObserver();
		
		bag.addObserver(po);
		bag.addObserver(ao);
		bag.addObserver(ao);

		bag.addValue(17);
		bag.addValue(42);
		bag.removeValue(17);
		bag.removeValue(18);
	}
	
}
