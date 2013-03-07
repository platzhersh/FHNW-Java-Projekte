/*
 * Copyright (c) 2000-2012 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank.gui.tests;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import bank.Account;
import bank.Bank;

public class ConcurrentReads implements BankTest {
  private static int NUMBER_READER = 10;
  private static int NUMBER_READS = 1000000;
  

  @Override
  public String getName() {
    return "Test concurrent reads";
  }

  @Override
  public boolean isEnabled(int size) {
    return size >= 1;
  }

  @Override
  public void runTests(JFrame context, Bank bank, String currentAccountNumber) throws Exception {
    final Account account = bank.getAccount(currentAccountNumber);
    
    final AtomicLong startTime = new AtomicLong(); // set when barrier is tripped
    
    final CyclicBarrier start = new CyclicBarrier(NUMBER_READER, new Runnable() {
      @Override
      public void run() {
         startTime.set(System.currentTimeMillis());
      }
    });
    
    final CountDownLatch end = new CountDownLatch(NUMBER_READER);
    for(int i = 0; i< NUMBER_READER; i++) {
      new Thread(){
        public void run() {
          try {
            start.await();
            @SuppressWarnings("unused")
            double balance = 0;
            for (int j = 0; j < NUMBER_READS; j++) {
              balance += account.getBalance(); // summing up to avoid optimization
            }
            end.countDown();
            
          } catch (Exception e) {
            e.printStackTrace();
          } 
        };
      }.start();
    }
    
    end.await();
    long duration = System.currentTimeMillis() - startTime.get();
    
    JOptionPane.showMessageDialog(context, ""+ NUMBER_READER + " * " + NUMBER_READS+ " concurrent reads took " + duration + "ms.", "Test Result", JOptionPane.INFORMATION_MESSAGE);
  }

}
