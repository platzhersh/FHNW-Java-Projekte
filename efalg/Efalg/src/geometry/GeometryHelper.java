package geometry;

import java.awt.Point;

import junit.framework.Assert;

public class GeometryHelper {

	/* just to play around */
	public static void main(String[] args) {
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,5);
		
		Point p3 = new Point(-2,1);
		Point p4 = new Point(-1,1);
		
		System.out.println(LineIntersection(p1, p2, p3, p4));
	}
	
	/***
	 * 
	 * @param p Punkt auf PQ
	 * @param q Punkt auf PQ
	 * @param r Punkt 
	 * @return 	1  falls P links von QR oder auf QR aber nach R
	 * 			0  falls P zwischen Q und R
	 * 			-1 falls P rechts von QR oder auf QR vor Q
	 */
	public static int CounterClockWise(Point p, Point q, Point r) {
		Vektor a = new Vektor(q.x-p.x,q.y-p.y);
		  Vektor b = new Vektor(r.x-p.x,r.y-p.y);
		  
		  Vektor res = a.cross(b);
		  
		  return res.z;
	}
	
	
	/***
	 * Return if two lines intercept
	 * @param l1
	 * @param l2
	 * @param m1
	 * @param m2
	 * @return
	 */
	public static boolean LineIntersection(Point l1, Point l2, Point m1, Point m2) {
		boolean b1 = CounterClockWise(l1, m1, m2) * CounterClockWise(l2, m1, m2) <= 0;
		boolean b2 = CounterClockWise(m1, l1, l2) * CounterClockWise(m2, l1, l2) <= 0;
		return b1 && b2;
	}
	
	public static Point getInterceptionPoint(Point a, Point b, Point c, Point d) {
		
		double nebenD = (d.x-c.x)*(c.y-a.y)-(d.y-c.y)*(c.x-a.x);
		double hauptD = (b.x-a.x)*(d.y-c.y)-(d.x-c.x)*(b.y-a.y);
		double s = nebenD / hauptD;
		System.out.println(s);
		
		double rx = a.x + s*(b.x-a.x);
		double ry = a.y + s*(b.y-a.y);
		
		return new Point((int)rx,(int)ry);
	}
	
	  public static class Vektor {
		  
		  int x, y, z;
		  
		  public Vektor(int x, int y){
			  this.x = x;
			  this.y = y;
			  this.z = 0;
		  }
		  
		  private Vektor(int x, int y, int z){
			  this.x = x;
			  this.y = y;
			  this.z = z;
		  }
		  
		  private Vektor cross(Vektor other){
			  
			  int a = y*other.z - z*other.y;
			  int b = z*other.x - x*other.z;
			  int c = x*other.y - y*other.x;
			  
			  Vektor ret = new Vektor(a,b,c);  
			  return ret;
		  }
	  }
	
}
