package findbugs;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class A {
  private final Lock lock = new ReentrantLock();
  
  private int id;
  
  public int nextId() {
    synchronized(lock) {
      return id++;
    }
  }
}
