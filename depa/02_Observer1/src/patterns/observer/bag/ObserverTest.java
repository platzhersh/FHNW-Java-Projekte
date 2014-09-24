package patterns.observer.bag;

public class ObserverTest {
	public static void main(String[] args) {
		IntegerBag bag = new IntegerBag();
		
		PrintObserver po = new PrintObserver();
		AdderObserver ao = new AdderObserver();
		
		bag.addObserver(po);
		bag.addObserver(ao);
		bag.addObserver(ao);

		System.out.println(">> add 17");
		bag.addValue(17);
		
		bag.removeObserver(ao);
		System.out.println(">> add 42");
		bag.addValue(42);
		
		System.out.println(">> remove 17");
		bag.removeValue(17);
		
		System.out.println(">> remove 18");
		bag.removeValue(18);
	}
}