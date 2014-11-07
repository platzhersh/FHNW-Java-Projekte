package algorithms;
/*  -----------------------------------------------------------------
 *  -------  Effiziente Algorithmen                  ----------------
 *  -------                                          ----------------
 *  -------  helper class for the calculation of     ----------------
 *  -------  the convex hull of points               ----------------
 *  -------  in the xy-plane                         ----------------
 *  -------                                          ----------------
 *  -------  @author  Manfred Vogel                  ----------------
 *  -------  2014 october                            ----------------
 *  -----------------------------------------------------------------
 */
 
abstract class ConvexHull {

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

