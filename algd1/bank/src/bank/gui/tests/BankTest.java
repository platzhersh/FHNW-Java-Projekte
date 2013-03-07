package bank.gui.tests;

import javax.swing.JFrame;

import bank.Bank;

public interface BankTest {
	String getName();
	boolean isEnabled(int size);
	void runTests(JFrame context, Bank bank, String currentAccountNumber) throws Exception;
}
