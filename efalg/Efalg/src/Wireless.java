import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Wireless
{
  public static void main(String[] args) throws Exception
  {
    Scanner in=new Scanner(new File("wireless.in"));
    PrintWriter out=new PrintWriter("wireless.out");
    
    
    //your code here
    //int a=in.nextInt();
    //out.println("solution");
    
    int maxPoints = Integer.parseInt(in.nextLine());                        
    double x[] = new double[maxPoints];
	double y[] = new double[maxPoints];
	int n=0;
	
	while (in.hasNextLine())   {            // read points
		String[] line = in.nextLine().split(" ");
	   x[n] = Double.parseDouble(line[0]);
	   y[n] = Double.parseDouble(line[1]);
	   n++;
	}
	
	//System.out.println(n+" Punkte eingelesen"); 
	
	Point p[] = new Point[n];
	for( int i=0; i<n; i++)
	   p[i] = new Point(x[i],y[i]); 
	                                             // call convex hull algorithm
	   int h=Graham.computeHull(p);
	    // int h=JarvisMarch.computeHull(p);
	                                             // output konvex polygon
	   Circle minSed = new Circle(new Point(0,0), Double.MAX_VALUE);
	   
	   // check circle pairs
	   for( int i=0; i<h; i++)
		   for (int j = i; j < h; j++) {
	        	Circle c = new Circle(p[i],p[j]);
	        	boolean contains = true;
	        	for (int k = 0; k < h; k++) {
	        		contains = contains && c.contains(p[k]);
	        	}
	        	if (contains)
	        		if (c.d < minSed.d) minSed = c;
	        	
	        }

	    // check circle triples
	   for( int i=0; i<h; i++)
	        for (int j = i; j < h; j++) {
	        	for (int l = j; l < h; l++) {
	        		Circle c = new Circle(p[i],p[j],p[l]);
		        	boolean contains = true;
		        	for (int k = 0; k < h; k++) {
		        		contains = contains && c.contains(p[k]);
		        	}
		        	if (contains)
		        		if (c.r < minSed.r) minSed = c;
	        	}
	        	
	        	
	        }
	
    out.println(new DecimalFormat("#0.00").format(minSed.r));
    out.println(new DecimalFormat("#0.00").format(minSed.m.x) + " " + new DecimalFormat("#0.00").format(minSed.m.y));
    
    out.close();
    in.close();
  }
  
  public static double getDistance(Point a, Point b){		
		return Math.sqrt(Math.pow((a.x - b.x), 2)+Math.pow((a.y - b.y),2));
	}
  
  
  public static class ConvexHull {

	    protected static Point[] p;
	    protected static int n;
	    protected static int h;

	    public static void setPoints(Point[] p0) {
	        p=p0;
	        n=p.length;
	        h=0;
	    }

	    protected static void exchange(int i, int j) {
	        Point t=p[i];
	        p[i]=p[j];
	        p[j]=t;
	    }

	    protected static void makeRelTo(Point p0) {
	        int i;
	        Point p1=new Point(p0); // necessary, because p0 migth be in p[] 
	        for (i=0; i<n; i++)
	            p[i].makeRelTo(p1);
	    }

	    protected static int indexOfLowestPoint() {
	        int i, min=0;
	        for (i=1; i<n; i++)
	            if (p[i].y<p[min].y || p[i].y==p[min].y && p[i].x<p[min].x)
	                min=i;
	        return min;
	    }

	    protected static boolean isConvex(int i) {
	        return p[i].isConvex(p[i-1], p[i+1]);
	    }
	}   

  public static class Point  {

	    public double x, y;

	    public Point(double x, double y)   { this.x=x; this.y=y; }

	    public Point(Point p)              { this(p.x, p.y);  }

	    public Point relTo(Point p)        { return new Point(x-p.x, y-p.y);}

	    public void makeRelTo(Point p)     { x-=p.x;  y-=p.y; }

	 //   public Point moved(double x0, double y0) { return new Point(x+x0, y+y0); }

	    public Point reversed()            { return new Point(-x, -y); }

	    public boolean isLower(Point p)    { return y<p.y || y==p.y && x<p.x; }

	    public double mdist() {   // Manhattan-distance
	       return Math.abs(x)+Math.abs(y);  }

	    public double mdist(Point p)       { return relTo(p).mdist(); }

	    public boolean isFurther(Point p)  { return mdist()>p.mdist(); }

	    public boolean isBetween(Point p0, Point p1)   {
	        return p0.mdist(p1)>=mdist(p0)+mdist(p1);
	    }

	    public double cross(Point p)       { return x*p.y-p.x*y; }

	    public boolean isLess(Point p)     {
	        double f=cross(p);
	        return f>0 || f==0 && isFurther(p);
	    }

	    public double area2(Point p0, Point p1)  {
	        return p0.relTo(this).cross(p1.relTo(this));
	    }

	    public boolean isConvex(Point p0, Point p1)  {
	        double f=area2(p0, p1);
	        return f<0 || f==0 && !isBetween(p0, p1);
	    }

	}    
  
  public static class Graham extends ConvexHull {

	  public static int computeHull(Point[] p) {
	        setPoints(p);
	        if (n<3) return n;
	        graham();
	        return h;
	    }

	    private static void graham()  {
	        exchange(0, indexOfLowestPoint());
	        Point pl=new Point(p[0]);
	        makeRelTo(pl);
	        sort();
	        makeRelTo(pl.reversed());
	        int i=3, k=3; 
	        while (k<n) {
	            exchange(i, k);
	            while (!isConvex(i-1))
	                exchange(i-1, i--);
	      k++;
	            i++;
	         }
	        h=i;
	    }

	    private static void sort()  {
	        quicksort(1, n-1); // without point 0
	    }

	    protected static void quicksort(int lo, int hi)  {
	        int i=lo, j=hi;
	        Point q=p[(lo+hi)/2];
	        while (i<=j)  {
	            while (p[i].isLess(q)) i++;
	            while (q.isLess(p[j])) j--;
	            if (i<=j) exchange(i++, j--);
	        }
	        if (lo<j) quicksort(lo, j);
	        if (i<hi) quicksort(i, hi);
	    }
	}

public static class Circle {
	double d;
	double r;
	Point m;
	
	public Circle(Point p1, Point p2) {
		double xdif = p1.x-p2.x;
		double ydif = p1.y-p2.y;
		
		m = new Point((p1.x - xdif)/2, (p1.y - ydif)/2);
		d = Math.abs(getDistance(p1, p2));
		r = d/2;
	}
	
	public Circle(Point p1, Point p2, Point p3) {
		double xdif = p1.x-p2.x;
		double ydif = p1.y-p2.y;
		
		m = new Point(p1.x - xdif, p1.y - ydif);
		r = getDistance(p1, p2);
	}
	
	public Circle(Point m, double d) {
		this.m = m;
		this.d = d;
		this.r = d/2;
	}
	public boolean contains(Point p) {
		if (Math.abs(getDistance(p, this.m)) < this.r) return true;
		else return false;
	}
}

  /*
  public static Set<Point> sed(Set<Point> P, Set<Point> R) {

	  if (P is empty or |R| = 3) then
      D := calcDiskDirectly(R)
 else
     choose a p from P randomly;
     D := sed(P - {p}, R);
     if (p lies NOT inside D) then
         D := sed(P - {p}, R u {p});
 return D;
	  
	  return null;
	  
  }*/
  
  
}