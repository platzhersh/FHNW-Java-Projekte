package algorithms;
/*  -----------------------------------------------------------------
 *  -------  Effiziente Algorithmen                  ----------------
 *  -------                                          ----------------
 *  -------  implements the Graham algorithm         ----------------
 *  -------  for the calculation of the convex       ----------------
 *  -------  hull of points in the xy-plane          ----------------
 *  -------                                          ----------------
 *  -------  @author  Manfred Vogel                  ----------------
 *  -------  2014 october                            ----------------
 *  -----------------------------------------------------------------
 */
 
public class Graham extends ConvexHull {

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


