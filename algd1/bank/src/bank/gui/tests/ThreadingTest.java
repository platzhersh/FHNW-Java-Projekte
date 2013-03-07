package bank.gui.tests;

import java.util.Iterator;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

public class ThreadingTest implements BankTest {

	@Override
	public String getName() {
		return "Threading Test";
	}

	@Override
	public boolean isEnabled(int size) {
		return size > 1;
	}

	@Override
	public void runTests(JFrame context, final Bank bank, String currentNumber) throws Exception {
		final Account acc = bank.getAccount(currentNumber);
		final double amount = acc.getBalance();

		String msg = null;

		class TestThread extends Thread {
			private double val;

			TestThread(double val) {
				this.val = val;
			}

			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					try {
						acc.deposit(val);
						acc.withdraw(val);
						yield();
					} catch (Exception e) {
						break;
					}
				}
			}
		}

		if (msg == null) {
			TestThread t1 = new TestThread(120);
			TestThread t2 = new TestThread(60);
			TestThread t3 = new TestThread(20);
			TestThread t4 = new TestThread(55.5);
			t1.start();
			t3.start();
			t2.start();
			t4.start();
			try {
				t1.join();
				t2.join();
				t3.join();
				t4.join();
			} catch (InterruptedException e) {
				/* ignore interrupts */
			}

			if (amount != acc.getBalance()) {
				msg = "your bank is not thread safe";
			}
		}
		
		// deadlock in transfer
		if(msg == null){
			String  n = bank.createAccount("Account3");
			final Account a = bank.getAccount(n);

			a.deposit(100);

			Thread t = new Thread(){
				@Override public void run(){
					try {
						bank.transfer(a, a, 50);
					}
					catch(Exception e){	}
					try {
						a.withdraw(100);
					}
					catch(Exception e){	}
				}
			};
			t.start();
			try {
				t.join(8000);
			}
			catch(InterruptedException e){
			}
			if(a.getBalance()>0) {
				msg = "A call of bank.transfer ended in a deadlock!";
			}
			
			bank.closeAccount(n);
		}

		if (msg == null) {
			Set<String> s = bank.getAccountNumbers();
			Iterator<String> it = s.iterator();
			final Account a1 = bank.getAccount(it.next());
			final Account a2 = bank.getAccount(it.next());

			class TestThread2 extends Thread {
				private final Account from, to;
				private volatile boolean running = false;

				TestThread2(Account from, Account to) {
					this.from = from;
					this.to = to;
				}

				@Override
				public void run() {
					running = true;
					for (int i = 0; i < 100; i++) {
						try {
							bank.transfer(from, to, 1);
							bank.transfer(to, from, 1);
						} catch (Exception e) {
							break;
						}
					}
					running = false;
				}

				boolean isRunning() {
					return running;
				}
			}

			TestThread2 t1 = new TestThread2(a1, a2);
			TestThread2 t2 = new TestThread2(a2, a1);

			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			double bal1 = a1.getBalance();
			double bal2 = a2.getBalance();

			try {
				a1.deposit(100);
				a2.deposit(100);
				t1.start();
				t2.start();
			} catch (InactiveException e) {
			}

			try {
				t1.join(5000);
				t2.join(5000);
			} catch (InterruptedException e) {
			}

			if (t1.isRunning() || t2.isRunning()) {
				msg = "your bank seems to hang in a deadlock";
			} else {
				try {
					a1.withdraw(100);
					a2.withdraw(100);
					bank.transfer(a1, a2, bal1 + 1);
				} catch (InactiveException e) {
				} catch (OverdrawException e) {
				}

				if (bal1 != a1.getBalance() || bal2 != a2.getBalance()) {
					msg = "your implementation of transfer is not correct";
				}
			}
		}
		
		if(msg == null) {
			msg = "your bank seems to be thread safe";
		}
		
		JOptionPane.showMessageDialog(context, msg, "Test Result",
				JOptionPane.INFORMATION_MESSAGE);

	}

}
