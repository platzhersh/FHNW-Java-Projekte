package patterns.observer.cycle;

import java.awt.Color;

public class RedScrollbar extends Observable implements Observer {
	
	private int value;
	
	RedScrollbar(ColorModel model) {
		this.value = model.getColor().getRed();
		model.addObserver(this);
	}

	public int getValue() {
		return value;
	}

    public void setValue(int value) {
        this.value = value;
        this.notifyObservers(value);
    }

	@Override
	public void update(Observable source, Object arg) {
		System.out.println("model changed its color => adjust scrollbar value");
		setValue(((Color)arg).getRed());
	}

}
