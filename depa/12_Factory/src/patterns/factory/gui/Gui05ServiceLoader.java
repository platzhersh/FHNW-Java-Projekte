package patterns.factory.gui;

import java.util.ServiceLoader;

import patterns.factory.gui.ComponentFactory.Frame;

public class Gui05ServiceLoader {
	public static final void main(String[] args) {
		ServiceLoader<ComponentFactory> services = ServiceLoader.load(ComponentFactory.class);
		for(ComponentFactory f : services) {
			System.out.println(f.getClass().getName());
		}
		
		CalculatorFactoryImpl calcFactory = new CalculatorFactoryImpl();
		calcFactory.setComponentFactory(services.iterator().next());
		Frame f = calcFactory.newCalculatorFrame();
		f.setVisible(true);
	}

}
