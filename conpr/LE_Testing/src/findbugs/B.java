package findbugs;

public class B {
  private final String lock = "LOCK";
  
  private int id;
  
  public int nextId() {
    synchronized(lock) {
      return id++;
    }
  }
}
