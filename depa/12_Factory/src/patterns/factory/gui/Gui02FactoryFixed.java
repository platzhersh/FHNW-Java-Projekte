package patterns.factory.gui;

import patterns.factory.gui.ComponentFactory.ActionListener;
import patterns.factory.gui.ComponentFactory.Button;
import patterns.factory.gui.ComponentFactory.Component;
import patterns.factory.gui.ComponentFactory.Field;
import patterns.factory.gui.ComponentFactory.Frame;

// generic solution of calculator
// (i.e. factory can be changed from FactorySwing
// to FactoryAWT)
/////////////////////////////////////////////////

public class Gui02FactoryFixed {

	public static void main(String[] args){
		ComponentFactory componentFactory = new FactorySwing();

		Frame f = componentFactory.createFrame("Calculator");

		final Field x   = componentFactory.createField(10, true);
		final Field y   = componentFactory.createField(10, true);
		final Field sum = componentFactory.createField(10, false);

		Button b = componentFactory.createButton("Compute",
			new ActionListener() {
				public void actionPerformed(Component source) {
					int ix = Integer.parseInt(x.getText());
					int iy = Integer.parseInt(y.getText());
					sum.setText("" + (ix + iy));
				}
			}
		);

		f.setGrid(4, 2);

		f.add(componentFactory.createLabel("x"));   f.add(x);
		f.add(componentFactory.createLabel("y"));   f.add(y);
		f.add(componentFactory.createLabel("sum")); f.add(sum);
		f.add(b);

		f.setVisible(true);
	}
}

