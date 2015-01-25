/*
 * Created on Dec 14, 2014
 */
package patterns.factory.gui;

/**
 * @author Wolfgang Weck
 */
public final class ConfigImpl implements Config {
	private CalculatorFactory calcFactory;
	private ComponentFactory componentFactory;

	public ConfigImpl() {
		calcFactory = new CalculatorFactoryImpl();
		componentFactory = new FactoryFX();
		((CalculatorFactoryImpl)calcFactory).setComponentFactory(componentFactory);
//		((CalculatorFactoryImpl)calcFactory).setTitle("CalcTool");
	}

	public Object getObject(String name) {
		if (name.equals("calcFactory")) return calcFactory;
		else if (name.equals("componentFactory")) return componentFactory;
		else throw new IllegalArgumentException();
	}
}
