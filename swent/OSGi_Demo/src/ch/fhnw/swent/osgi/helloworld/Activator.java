package ch.fhnw.swent.osgi.helloworld;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	public void start(BundleContext context) {
		System.out.println("Hello!");
	}

	public void stop(BundleContext context) {
		System.out.println("Goodbye.");
	}

}
