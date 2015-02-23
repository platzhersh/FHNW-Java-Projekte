package worksheet;

import org.junit.Test;

import static org.junit.Assert.*;
public class AccountTest {
	
    @Test
    public void testAccount() throws InterruptedException {
        final BankAccount c = new BankAccount();
        final int N = 100;
        
        Thread t0 = new Thread() {
            public void run() {
                for (int i = 0; i < N; i++) {
                    c.deposit(10);
                }
            }
        };
        
        Thread t1 = new Thread() {
            public void run() {
                for (int i = 0; i < N; i++) {
                    c.withdraw(10);
                }
            }
        };
        
        t0.start(); t1.start();
        t0.join(); t1.join();
        assertEquals(0, c.getBalance());
    }
}

class BankAccount {
   private int balance = 0;
   private final Object myLock = new Object();

   public void deposit(int amount) {
	   synchronized(myLock) {
		   balance += amount;
	   }
   }

   public void withdraw(int amount) {
	   synchronized(myLock) {
		   balance -= amount;
	   }
   }

   public int getBalance() {
       return balance;
   }
}
