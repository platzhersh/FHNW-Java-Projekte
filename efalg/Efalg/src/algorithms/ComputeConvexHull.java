package algorithms;
/** -----------------------------------------------------------------
 *  -------  Effiziente Algorithmen                  ----------------
 *  -------                                          ----------------
 *  -------  calcutes the convex hull of             ----------------
 *  -------  points in the xy-plane                  ----------------
 *  -------                                          ----------------
 *  -------  @author  Manfred Vogel                  ----------------
 *  -------  2014 october                            ----------------
 *  -----------------------------------------------------------------
 */  

public class ComputeConvexHull {

  public static void main(String[] args) {
    
    int maxPoints=100;                        
        double x[] = new double[maxPoints];
    double y[] = new double[maxPoints];
    int n=0;
    
    System.out.println("enter x and y-components of points, separated by , ");
    String line = InOut.getLine(); 

    while (line.length() > 0)   {            // read points
           String[] temp = line.split(",");
       x[n] = Double.parseDouble(temp[0]);
       y[n] = Double.parseDouble(temp[1]);
       n++;
       line = InOut.getLine();
    }
    
    System.out.println(n+" Punkte eingelesen"); 
    
    Point p[] = new Point[n];
    for( int i=0; i<n; i++)
       p[i] = new Point(x[i],y[i]); 
                                                 // call convex hull algorithm
    //        int h=Graham.computeHull(p);
        int h=JarvisMarch.computeHull(p);
                                                 // output konvex polygon

        for( int i=0; i<h; i++)
            System.out.println("Convex hull point Nr. "+(i+1)+" ("+p[i].x+" , "+p[i].y+")"); 

    }
}

