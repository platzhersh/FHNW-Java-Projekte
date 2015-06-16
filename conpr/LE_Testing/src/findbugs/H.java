package findbugs;

public class H {
  public static void main(String[] args) {
    new Thread(new Runnable() { 
      public void run() { 
        System.out.print(Thread.currentThread()); 
      }
    }).run();
  }
}
