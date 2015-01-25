package patterns.factory.gui;

import patterns.factory.gui.ComponentFactory.Frame;

public class CalculatorFactoryImpl implements CalculatorFactory {

	public void setComponentFactory(ComponentFactory fact) {
		// TODO this method is invoked by Spring in order to set the property "componentFactory".
	}

	public Frame newCalculatorFrame() {
		// TODO this method is invoked by the main program to get the frame to be shown.
		return null;
	}

}
