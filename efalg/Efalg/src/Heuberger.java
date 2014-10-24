import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class Heuberger {

	public static void main(String[] args) throws Exception
	  {
	    Scanner in = new Scanner(new File("heuberger.in"));
	    PrintWriter out=new PrintWriter("heuberger.out");
	    
	    // location of shooter	    
	    int sX = in.nextInt();
	    int sY = in.nextInt();
	    TargetPoint origin = new TargetPoint(sX, sY);
	    
	    int count = 0;
	    LinkedList<Target> visible = new LinkedList<Target>();
	    
	    // TODO: just for debugging
	    LinkedList<Target> targetList = new LinkedList<Target>();
	    
	    
	    int numOfCoordinates = in.nextInt();
	    
	    List<TargetPoint> points = new ArrayList<TargetPoint>();
	    
	    // für alle Punkte Distanz & Winkel zum Schützen bestimmen 
	    for (int i = 0; i < numOfCoordinates; i+=2) {
	    	TargetPoint t1 = new TargetPoint(in.nextInt(), in.nextInt());
	    	TargetPoint t2 = new TargetPoint(in.nextInt(), in.nextInt());
	    	
	    	t1.angle = getAngle(origin, t1);
	    	t2.angle = getAngle(origin, t2);
	    	
	    	Target t;
	    	// ensure that point with smaller angle is added as start point
	    	if (t1.angle > t2.angle) {
	    		t = new Target(t2, t1);
	    	} else {
	    		t = new Target(t1, t2);
	    	}
	    	// TODO: just for debugging
	    	targetList.add(t);
	    	
	    	points.add(t1);
	    	points.add(t2);
	    }
	    
	    // sortiere Points nach Winkel
	    Collections.sort(points);
	    
	    // Punkte abarbeiten
	    System.out.println(points.size());
	    for (int i = 0; i < points.size(); i++) {
	    	TargetPoint p = points.get(i);
	    	
	    	// case: Punkt ist Anfang eines Targets
	    	if (p == p.t.start) {
	    		System.out.println(p.toString() + " Punkt ist Targetstart");
	    		if (visible.size() == 0) {
	    			visible.add(p.t);
	    			visible.get(0).visible = true;
	    		}
	    		else {
	    			List<Target> tl = new LinkedList<Target>(visible);
	    			Iterator<Target> it = tl.iterator();
	    			while (it.hasNext()) {
	    				Target q = it.next();
	    				int intercept = CounterClockWise(q.start, q.end ,p);
	    				
	    				// case: Point on Line
	    				if (intercept == 0) {
	    					System.out.println("WATCH OUT: 0");
	    					TargetPoint ep = p.t.end;
	    					for (Target l : visible) {
	    						int ic = CounterClockWise(l.start, l.end ,ep);
	    						// TODO:
	    					}
	    				}
	    				
	    				// case: Point left of line -> visible
	    				if (intercept == 1) {
	    					visible.addFirst(p.t);
	    					break;
	    				}

	    				// case: Point right of line -> not visible
	    				if (intercept == -1) {
    						visible.addLast(p.t);
    						break;
	    				}

	    			}
					if (visible.size() > 0) visible.get(0).visible = true;
	    		}
	    	}
	    	
	    	// case: Punkt ist Ende eines Target
	    	else {
	    		System.out.println(p.toString() + " Punkt ist Targetend");
	    		if (p.t.visible) count++;
	    		visible.remove(p.t);
	    		if (visible.size() > 0) visible.get(0).visible = true;
	    	}
	    }

	    System.out.println("Count: " + count);
	    
	    
	    // TODO: just for debugging
	    for (Target t : targetList) {
	    	System.out.println(t.toString());
	    }
	    
	    in.close();
	    out.close();
	  }
	
	public static double getAngle(TargetPoint o, TargetPoint t) {
		double d1 = getDistance(o,t);
		t.distance = d1;
		double d2 = getDistance(t,new TargetPoint(o.x,o.y+1));
		double angle = Math.acos( (Math.pow(d1,2) + 1 - Math.pow(d2, 2)) / (2*d1) );
    	
    	return o.x < t.x ? 2*Math.PI-angle : angle;
	}
	
	public static double getDistance(TargetPoint a, TargetPoint b){		
		return Math.sqrt(Math.pow((a.x - b.x), 2)+Math.pow((a.y - b.y),2));
	}
	
	public static int CounterClockWise(TargetPoint q, TargetPoint r, TargetPoint p) {
		Vektor a = new Vektor(q.x-p.x,q.y-p.y);
		  Vektor b = new Vektor(r.x-p.x,r.y-p.y);
		  
		  Vektor res = a.cross(b);
		  
		  // normalize
		  int result = 0;
		  result = res.z > 0 ? 1 : -1;
		  
		  return result;
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
	  
	public static class Target {
		  TargetPoint start;
		  TargetPoint end;
		  boolean visible = false;
		  
		  public Target(TargetPoint point1, TargetPoint point2) {
			  start = point1;
			  start.setTarget(this);
			  end = point2;
			  end.setTarget(this);
		  }
		  
		  public String toString() {
			  return "Target["+start.toString()+", "+end.toString()+"]" + " visible: " + visible;
		  }
	  }
	
	public static class TargetPoint implements Comparable<TargetPoint> {
		int x;
		int y;
		Target t;
		double angle;
		double distance;	// deprecated
		
		public TargetPoint(int t1, int t2) {
			x = t1;
			y = t2;
		}
		
		public void setTarget(Target t) {
			this.t = t;
		}

		@Override
		public int compareTo(TargetPoint p) {
			if (this.angle < p.angle) return -1;
			if (this.angle > p.angle) return 1;
			if (this.angle == p.angle) {
				if (this.distance < p.distance) return -1;
				if (this.distance > p.distance) return 1;
				return 0;
			}
			return -42;
		}
		
		public String toString() {
			return "("+x+","+y+")";
		}
		
		
	}
}