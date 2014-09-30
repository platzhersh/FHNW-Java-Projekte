package patterns.observer.once;

public class ObserverTest {
	public static void main(String[] args) {
		Sensor s = new Sensor(20);

		s.addObserver(new PrintObserver("Printer 1"));
		s.addObserver(new OnceObserver());
		s.addObserver(new PrintObserver("Printer 2"));
		s.addObserver(new PrintObserver("Printer 3"));

		s.setValue(22);
		s.setValue(30);
		s.setValue(25);
		s.setValue(22);
	}
}
