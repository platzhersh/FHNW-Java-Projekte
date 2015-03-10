/*
 * Copyright (c) 2000-2015 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank.gui.tests;

import javax.swing.JFrame;

import bank.Bank;

public interface BankTest {
	String getName();
	boolean isEnabled(int size);
	void runTests(JFrame context, Bank bank, String currentAccountNumber) throws Exception;
}
