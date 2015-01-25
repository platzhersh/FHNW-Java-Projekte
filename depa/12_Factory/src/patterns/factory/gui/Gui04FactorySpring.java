package patterns.factory.gui;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import patterns.factory.gui.ComponentFactory.Frame;

public class Gui04FactorySpring {
	private static ClassPathXmlApplicationContext ctx;

	static {
		ctx = new ClassPathXmlApplicationContext("patterns/factory/gui/gui-context.xml");
	}

	public static final void main(String[] args) {
		CalculatorFactory calcFactory = (CalculatorFactory)ctx.getBean("calcFactory");
		Frame f = calcFactory.newCalculatorFrame();
		f.setVisible(true);
	}

}
