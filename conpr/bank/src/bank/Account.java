/*
 * Copyright (c) 2000-2015 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank;

import java.io.IOException;

/**
 * The Account interface represents a single account. It can be used to deposit
 * and withdraw money. The amounts are always given in Swiss Francs. References
 * to accounts can be obtained over a bank interface.
 * 
 * @see Bank
 * @author Dominik Gruntz
 * @version 3.0
 */
public interface Account {

	/**
	 * Returns the account number.
	 * 
	 * @return the account number.
	 * @throws IOException if a remoting or communication problem occurs
	 */
	String getNumber() throws IOException;

	/**
	 * Returns the name of the owner.
	 * 
	 * @return the name of the owner
	 * @throws IOException if a remoting or communication problem occurs
	 */
	String getOwner() throws IOException;

	/**
	 * Returns the state of the account. An account may be active or passive. A
	 * fresh created account is active. When the account is deleted, it becomes
	 * passive. Transactions performed on a passive account are not allowed and
	 * throw an InactiveException.
	 * 
	 * @return the state of the account
	 * @throws IOException if a remoting or communication problem occurs
	 */
	boolean isActive() throws IOException;

	/**
	 * Deposits the given amount on the account.
	 * 
	 * @param amount value to deposit
	 * @pre amount &ge; 0
	 * @pre isActive()
	 * @throws InactiveException if the account is not active
	 * @throws IllegalArgumentException if the argument is negative
	 * @throws IOException if a remoting or communication problem occurs
	 */
	void deposit(double amount) throws IOException,
			IllegalArgumentException, InactiveException;

	/**
	 * Withdraws the given amount from the account.
	 * 
	 * @param amount value to withdraw
	 * @pre amount &ge; 0
	 * @pre isActive()
	 * @throws InactiveException if the account is not active.
	 * @throws OverdrawException if the amount is greater than the current
	 *             balance
	 * @throws IllegalArgumentException if the argument is negative
	 * @throws IOException if a remoting or communication problem occurs
	 */
	void withdraw(double amount) throws IOException,
			IllegalArgumentException, OverdrawException, InactiveException;

	/**
	 * Returns the balance of the account. The balance of an account which has
	 * been closed is always zero. An <code>InactiveException</code> is never
	 * thrown when the balance is accessed.
	 * 
	 * @return the balance of this account.
	 * @throws IOException if a remoting or communication problem occurs
	 */
	double getBalance() throws IOException;
}
