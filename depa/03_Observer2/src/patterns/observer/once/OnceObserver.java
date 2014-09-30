package patterns.observer.once;

public class OnceObserver implements Observer {

	@Override
	public void update(Observable source) {
		System.out.println("Once Observer called, bye...");
		source.removeObserver(this);
	}

}
