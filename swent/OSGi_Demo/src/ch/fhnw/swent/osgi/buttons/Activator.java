package ch.fhnw.swent.osgi.buttons;

import java.awt.Frame;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	public void start(BundleContext context) {
		Frame f = new ButtonApp2(context.getBundle());
		f.setSize(200, 100);
		f.setVisible(true);
	}

	public void stop(BundleContext context) {
	}

}
