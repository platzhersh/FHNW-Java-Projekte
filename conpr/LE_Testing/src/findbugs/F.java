package findbugs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class F {
  private final Lock lock = new ReentrantLock();
  private final Condition cond = lock.newCondition();
  
  private Object elem;
  
  public Object get() throws InterruptedException {
    lock.lock();
    try {
      while(elem == null) {
        cond.wait();
      }
      return elem;
    } finally {
      lock.unlock();
    }
  }
  
  public void put(Object elem) {
    lock.lock();
    try {
      this.elem = elem;
      cond.notifyAll();
    } finally {
      lock.unlock();
    }
  }
}
