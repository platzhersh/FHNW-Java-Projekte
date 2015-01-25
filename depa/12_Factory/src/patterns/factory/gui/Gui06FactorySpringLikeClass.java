package patterns.factory.gui;

import patterns.factory.gui.ComponentFactory.Frame;

public class Gui06FactorySpringLikeClass {
	private static Config conf;
	static {
		try {
			conf = (Config)Class.forName("patterns.factory.gui.ConfigImpl").newInstance();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void main(String[] args) throws Exception {
		CalculatorFactory calcFactory = (CalculatorFactory)conf.getObject("calcFactory");
		Frame f = calcFactory.newCalculatorFrame();
		f.setVisible(true);
	}
}