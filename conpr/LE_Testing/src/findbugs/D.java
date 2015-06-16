package findbugs;

import javax.annotation.concurrent.GuardedBy;

public class D {
  @GuardedBy("this")
  private int i;
  
  public synchronized void inc() {
    i++;
  }
  
  public int get() {
    return i;
  }
} 
