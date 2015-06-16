package findbugs;


public class G {
  private Object elem;

  public synchronized Object get() throws InterruptedException {
    if (elem == null) {
      wait();
    }
    return elem;
  }
  
  public void put(Object elem) {
    this.elem = elem;
    notify();
  }
}