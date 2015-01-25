package patterns.factory.gui;

import patterns.factory.gui.ComponentFactory.ActionListener;
import patterns.factory.gui.ComponentFactory.Component;
import patterns.factory.gui.ComponentFactory.Frame;
//import java.awt.*;

// generic solution of calculator where factory
// can be specified with a runtime argument
/////////////////////////////////////////////////

public class Gui03Argument {
	
	public static void main(String[] args) throws Exception {
		Class.forName("patterns.factory.gui." + args[0]);
		ComponentFactory componentFactory = CurrentFactory.getFactory();
		
		Frame f = componentFactory.createFrame("Calculator");
		
		final Component x   = componentFactory.createField(10, true);
		final Component y   = componentFactory.createField(10, true);
		final Component sum = componentFactory.createField(10, false);

		Component b = componentFactory.createButton("Compute",
			new ActionListener(){
				public void actionPerformed(Component source){
					int ix = Integer.parseInt(((ComponentFactory.Field)x).getText());
					int iy = Integer.parseInt(((ComponentFactory.Field)y).getText());
					((ComponentFactory.Field)sum).setText("" + (ix + iy));
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

