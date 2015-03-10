/*
 * Copyright (c) 2000-2015 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank;

/**
 * The InactiveException is thrown when a bank transaction is called on an
 * closed account.
 * 
 * @see Account
 * @see Bank
 * @author Dominik Gruntz
 * @version 3.0
 */
public class InactiveException extends Exception {
	public InactiveException() {
		super();
	}

	public InactiveException(String reason) {
		super(reason);
	}

	public InactiveException(String reason, Throwable cause) {
		super(reason, cause);
	}

	public InactiveException(Throwable cause) {
		super(cause);
	}
}
