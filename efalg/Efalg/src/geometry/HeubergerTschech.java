package geometry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class HeubergerTschech
{
  public static void main(String[] args) throws Exception
  {
	  BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("heuberger.in"))));
	  PrintWriter out=new PrintWriter("heuberger.out");
    
	  int a = Vektor.ccw(new Point(0,0), new Point(2,2), new Point(1,1));
	  
	  System.out.println(a);
    
	  out.close();
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
	  
	  public static int ccw(Point q, Point r, Point p) {
		  
		  Vektor a = new Vektor(q.x-p.x,q.y-p.y);
		  Vektor b = new Vektor(r.x-p.x,r.y-p.y);
		  
		  Vektor res = a.cross(b);
		  
		  return res.z;
	  }
  }
  
  public static class Point{
	  
	  public int x, y;
	  public double angle;
	  
	  public Point(int x, int y){
		  this.x = x;
		  this.y = y;
	  }
	  
  }
}