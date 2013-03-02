package bank.driver;

import java.io.Serializable;

public class Command implements Serializable {

	private String a_number;
	private String a_owner;
	private double a_balance;
	
	private enum type {
		REQUEST, RESPONSE
	}
	
	
}
