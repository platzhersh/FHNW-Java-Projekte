/*
 * Created on Mar 16, 2009
 */
package ch.fhnw.swent.osgi.logger.api;

import ch.fhnw.swent.osgi.logger.Activator;


public class Logger {
	public static void writeMsg(String s) {
		Activator.writeMsg(s);
	}
}
