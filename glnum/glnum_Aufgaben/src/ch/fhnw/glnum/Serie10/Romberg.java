package ch.fhnw.glnum.Serie10;
/** 
 *    @author:    Manfred Vogel
 *    @date:      9. Mai 2014
 *    @filename:  Romberg
 *
 *      Grundlagen der Numerik /  Übung 9  / Aufgabe 1 
 *      Romberg Quadratur
 * 
 */

class Romberg{
  
  static final int n = 10;     // max. Romberg steps
  static double[][] r = new double[n][n];

// Integrationsintervall [a, b]
    static final double a = 0.0;
//  	static final double a = 1.0;
//    static final double a = 3.0;
  
//    static final double b = 1.0;
//  static final double b = 2.0;
//    static final double b = 5.0;
  static final double b = Math.PI;
  
  // definiert die zu integrierende Funktion f
  static double f(double x) {
	  
//         return ( x*Math.log(x) ) ;
//              return ( 1.0/Math.sqrt(x*x-4.0) ) ;
    //  return ( x*x*Math.exp(-x) ) ;
      return ( x*x*Math.cos(x) ) ;
    
    //    if (x == 0.0 ) return 1.0; return ( Math.sin(x)/x ) ;
    
//      return ( Math.exp(-x*x) ) ;
  }
  
  
// implementiert die Romberg Methode
  static double romberg(double a, double b, double eps) {
    r[0][0]=(b-a)/2.0 *(f(a)+f(b)) ;
    int k = 0;
    double h = (b-a);
    double sum;
    double err = 100.0;
    while (err > eps  &&  k < (n-1) ) {
      k++;
      h= h/2.0 ;
      sum=0.0;
      for (int i = 1; i <= Math.pow(2,(k-1)); i++) sum += f(a+(2*i-1)*h);
      r[k][0]= (r[k-1][0] + h*2.0*sum)/2.0;
      for (int j = 1; j <= k; j++) {
        r[k][j]= r[k][j-1] + (r[k][j-1] - r[k-1][j-1])/(Math.pow(4,j)-1.0);
        err= Math.abs( r[k][k] - r[k-1][k-1] );
      }
      System.out.println  (k+". Romberg-Approximation :" + r[k][k]);
    }
    if ( k ==  n-1) System.out.print  ("    Integral( f(x), x=a..b )    not converged ");
    return r[k][k];
  }

  public static void main(String[] args)  {
    double integral = romberg(a, b, 1e-9);
    
    System.out.println("\n\n");
    System.out.println(" ** Romberg Integration ** " + "\n");
    if ( integral == -999.999) System.out.print  ("    Integral( f(x), x=a..b )    not converged "); 
      else System.out.print  ("    Integral( f(x), x=a..b ) = " + integral);
    System.out.println("\n\n");
  }
}
