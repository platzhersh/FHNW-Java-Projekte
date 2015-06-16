package findbugs;

public class E {
  private Long sequence = Long.valueOf(0);
  
  public Long next() {
    synchronized (sequence) {
      sequence = sequence.longValue() + 1;
      return sequence;
    }
  }
}
