/*
 * Copyright (c) 2000-2015 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank;

import java.io.IOException;

import javax.swing.SwingUtilities;

import bank.gui.BankGUI;

/**
 * Class Client is used to start the Client side of the bank application. As a
 * runtime parameter the name of the class which implements the
 * <code>BankDriver</code> interface has to be specified. This class is then
 * loaded and used to access the bank. This class needs a public constructor.
 * 
 * <pre>
 * Usage: java bank.Client &lt;classname&gt;
 * </pre>
 * 
 * E.g. start the application with one of the following commands. The additional
 * runtime arguments are passed to the connect method of the BankDriver
 * implementation.
 * 
 * <pre>
 * java bank.Client bank.local.Driver
 * java bank.Client bank.sockets.Driver localhost 1234
 * </pre>
 * 
 * @see BankDriver
 * @author Dominik Gruntz
 * @version 3.0
 */
public final class Client {
	
	/** Utility class which is only used to start the application */
	private Client() { }

	public static void main(String args[]) {
		if (args.length < 1) {
			System.out.println("Usage: java " + Client.class.getName()
					+ " <class>");
			System.exit(1);
		}

		BankDriver server = null;
		try {
			Class<?> c = Class.forName(args[0]);
			server = (BankDriver) c.newInstance();
		} catch (ClassNotFoundException e) {
			System.out.println("class " + args[0] + " coult not be found");
			System.exit(1);
		} catch (InstantiationException e) {
			System.out.println("class " + args[0]
					+ " could not be instantiated");
			System.out
					.println("probably it has no public default constructor!");
			System.exit(1);
		} catch (IllegalAccessException e) {
			System.out.println("class " + args[0]
					+ " could not be instantiated");
			System.out.println("probably it is not declared public!");
			System.exit(1);
		}

		String[] serverArgs = new String[args.length - 1];
		System.arraycopy(args, 1, serverArgs, 0, args.length - 1);

		try {
			server.connect(serverArgs);
		} catch (IOException e) {
			System.out.println("Problem while connecting to the server:");
			e.printStackTrace();
			System.exit(1);
		}

		final BankGUI app = new BankGUI(server);
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				app.pack();
				app.setVisible(true);
			}});
	}

}
