package patterns.observer.bag;

import java.util.ArrayList;
import java.util.List;

public class IntegerBag extends Observable {
	private List<Integer> list = new ArrayList<Integer>();

	public void addValue(int value) {
		list.add(value);
		notifyObservers();
	}

	public void removeValue(int value) {
		list.remove((Object) value);
		notifyObservers();
	}

	public Integer[] getValues() {
		return list.toArray(new Integer[list.size()]);
	}

}
