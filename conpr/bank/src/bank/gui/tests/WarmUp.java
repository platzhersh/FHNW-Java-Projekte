/*
 * Copyright (c) 2000-2015 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank.gui.tests;

import javax.swing.JFrame;

import bank.Account;
import bank.Bank;

public class WarmUp implements BankTest {

	final static int NUMBER_OF_EFF_TESTS = 5 * EfficiencyTest.NUMBER_OF_EFF_TESTS;

	@Override
	public String getName() {
		return "WarmUp";
	}

	@Override
	public boolean isEnabled(int size) {
		return true;
	}

	@Override
	public void runTests(JFrame context, Bank bank, String currentAccountNumber) throws Exception {
		Account acc = null;
		if(bank.getAccountNumbers().size() == 0) {
			String id = bank.createAccount("TestUser");
			acc = bank.getAccount(id);
		} else {
			acc = bank.getAccount(currentAccountNumber);
		}

		try {
			System.gc();
			long st = System.currentTimeMillis();
			for (int i = 1; i <= NUMBER_OF_EFF_TESTS; i++) {
				acc.deposit(i);
				acc.withdraw(i);
			}
			st = System.currentTimeMillis() - st;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
