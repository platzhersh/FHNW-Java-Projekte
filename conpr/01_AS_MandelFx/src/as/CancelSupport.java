package as;
public class CancelSupport {
  private boolean cancel;
  
  public boolean isCancelled() {
    return cancel;
  }
  
  public void cancel() {
    cancel = true;
  }
}