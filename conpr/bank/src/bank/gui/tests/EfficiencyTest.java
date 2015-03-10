/*
 * Copyright (c) 2000-2015 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank.gui.tests;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import bank.Account;
import bank.Bank;

public class EfficiencyTest implements BankTest {

	// TODO to be defined over construcotr
	final static int NUMBER_OF_EFF_TESTS = 1000000; // CONPR
	// final static int NUMBER_OF_EFF_TESTS = 1000; // DS

	@Override
	public String getName() {
		return "Efficiency Test";
	}

	@Override
	public boolean isEnabled(int size) {
		return size > 0;
	}

	@Override
	public void runTests(JFrame context, Bank bank, String currentAccountNumber) throws Exception {
		final Account acc = bank.getAccount(currentAccountNumber);

		String msg;
		try {
			System.gc();
			long st = System.currentTimeMillis();
			for (int i = 1; i <= NUMBER_OF_EFF_TESTS; i++) {
				acc.deposit(i);
				acc.withdraw(i);
			}
			st = System.currentTimeMillis() - st;
			msg = 2 * NUMBER_OF_EFF_TESTS + " operations in " + st / 1000.0
					+ " Sek\n" + st / (2.0 * NUMBER_OF_EFF_TESTS) + " msec/op";
		} catch (Exception e) {
			msg = "test did throw an exception\n" + e.getMessage();
		}

		JOptionPane.showMessageDialog(context, msg, "Test Result",
				JOptionPane.INFORMATION_MESSAGE);

	}

}
