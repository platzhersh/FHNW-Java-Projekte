/*
 * Created on Mar 16, 2009
 */
package ch.fhnw.swent.osgi.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
	private static PrintWriter log = null;

	public void start(BundleContext context) throws IOException {
		log = new PrintWriter(new FileWriter("log.txt"), true);
		writeMsg("Logging-Service started");
	}

	public void stop(BundleContext context) {
		if (log != null) {
			writeMsg("Logging-Service stopped");
			log.close();
			log = null;
		}
	}

	public static void writeMsg(String s) {
		if (log != null) {
			log.println(s);
		}
	}
}
