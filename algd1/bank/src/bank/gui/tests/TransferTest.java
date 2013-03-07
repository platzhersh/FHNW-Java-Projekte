package bank.gui.tests;

import java.util.Iterator;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

public class TransferTest implements BankTest {

	@Override
	public String getName() {
		return "Transfer Test";
	}

	@Override
	public boolean isEnabled(int size) {
		return size >= 2;
	}

	@Override
	public void runTests(JFrame context, final Bank bank,
			String currentAccountNumber) throws Exception {
		Set<String> s = bank.getAccountNumbers();
		Iterator<String> it = s.iterator();
		final Account a1 = bank.getAccount(it.next());
		final Account a2 = bank.getAccount(it.next());

		String msg = null;
		
		if (msg == null) {
			double bal1 = a1.getBalance();
			double bal2 = a2.getBalance();

			try {
				bank.transfer(a1, a2, bal1 + 1);
			} catch (InactiveException e) {
				/* should not be thrown */
			} catch (OverdrawException e) {
				/* expected */
			}

			if (bal1 != a1.getBalance() || bal2 != a2.getBalance()) {
				msg = "your implementation of transfer is not correct";
			}
		}

		if (msg == null) {
			msg = "found no errors in the implementation of transfer";
		}

		JOptionPane.showMessageDialog(context, msg, "Test Result",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
