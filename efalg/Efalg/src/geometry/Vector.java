package geometry;


public class Vector {
	  
	  double x, y, z;
	  
	  public Vector(double x, double y){
		  this.x = x;
		  this.y = y;
		  this.z = 0;
	  }
	  
	  private Vector(double x, double y, double z){
		  this.x = x;
		  this.y = y;
		  this.z = z;
	  }
	  
	  private Vector cross(Vector other){
		  
		  double a = y*other.z - z*other.y;
		  double b = z*other.x - x*other.z;
		  double c = x*other.y - y*other.x;
		  
		  Vector ret = new Vector(a,b,c);  
		  return ret;
	  }
	  
	  public double getX() {
		  return x;
	  }
	  public double getY() {
		  return y;
	  }
	  public double getZ() {
		  return z;
	  }
	  
	  public static Vector diff(Point p1, Point p2) {
		  return new Vector(p2.x-p1.x, p2.y-p1.y);
	  }
	  
	  public static Vector norm(Vector v) {
		  return new Vector(v.getY(),-v.getX());
	  }
	  
	  public static double ccw(Point q, Point r, Point p) {
		  
		  Vector a = new Vector(q.x-p.x,q.y-p.y);
		  Vector b = new Vector(r.x-p.x,r.y-p.y);
		  
		  Vector res = a.cross(b);
		  
		  return res.z;
	  }
	  
}