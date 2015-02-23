package as;
/** A square section of the complex plane. */
public class Plane {
  public final Complex center;
  public final double length;
  
  public Plane(Complex center, double length) {
    this.center = center; this.length = length;
  }
}