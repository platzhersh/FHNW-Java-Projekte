package findbugs;

public class C {
  private static C instance;
  
  public static C instance() {
    if(instance == null) {
      synchronized (C.class) {
        if(instance == null) {
          instance = new C();
        }
      }
    }
    return instance;
  }
}
