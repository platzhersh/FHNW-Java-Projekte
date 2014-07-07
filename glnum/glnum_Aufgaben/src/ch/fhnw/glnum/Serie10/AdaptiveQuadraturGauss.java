package ch.fhnw.glnum.Serie10;

/** 
 *    @author:    Manfred Vogel
 *    @date:      9. Mai 2014
 *    @filename:  AdaptiveQuadraturGauss
 *
 *      Grundlagen der Numerik /  Übung 9  / Aufgabe 2 
 *
 *      Adaptives Quadratur-Verfahren (mit Gauss-Quadratur Ord. 3)
 * 
 */


class AdaptiveQuadraturGauss {

  // Integrationsintervall [a, b]
  static final double a = 0.0;
  static final double b = 1.0;
  static int ifun = 0;		// function call counter
  
// Stützstellen und Gewichte für Gauss-Quadratur der Ordnung 3
  static final int n = 4;
  static int irek=0;
  static final double roots[]   = 
//  { 0.7745966692, 0.0         ,-0.7745966692 };
  { 0.9061798459, 0.5384693101, 0.0,          -0.5384693101, -0.9061798459 };
//    { 0.9324695142, 0.6612093865, 0.2386191861, -0.2386191861, -0.6612093865, -0.9324695142 };
//	  {0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0 };
  static final double weights[] =
//  { 0.5555555556, 0.8888888889, 0.5555555556 };
  { 0.2369268850, 0.4786286705, 0.5688888889, 0.4786286705, 0.2369268850 };
//    { 0.1713244924, 0.3607615729, 0.4679139349,  0.4679139349,  0.3607615729,  0.1713244924 } ;
//	  {1.0, 1.22, 1.35, 1.44, 1.5, 1.55, 1.59, 1.63, 1.66, 1.68, 1.70 };

// definiert die zu integrierende Funktion f
  static double f(double x) {
    ifun++;
//    return Math.sin(x*x*x) * Math.exp(-x/Math.exp(1.0));
//    return (1/(Math.pow(x,2)+1));
    return (1/(Math.pow(x,2)+1));
  }

// implementiert die Gauss-Quadratur
  static double gauss(double a, double b) {
    double sum=0.0;
    for (int j=0; j<n ; j++)
       sum += weights[j] * f( ((b-a)*roots[j]+b+a)/2.0 );
    return sum*(b-a)/2.0;
  }
  
  
  static double rek(double a, double b, double eps) {
    irek++;
    double intTot=0.0, intLeft=0.0, intRight=0.0;
    intTot   = gauss( a      , b      );
    intLeft  = gauss( a      , (a+b)/2);
    intRight = gauss( (a+b)/2, b      );
    if ( Math.abs( intTot - intLeft - intRight ) > eps )  
        return rek(a, (a+b)/2, eps/2.0) + rek((a+b)/2, b, eps/2.0);
      else 
        return intLeft + intRight;  
  }               

  public static void main(String[] args) {
    double integral = rek(a, b, 1e-6);
    
    System.out.println("\n\n");
    System.out.println(" ** Adaptive Quadratur mit Gauss-Verfahren ** " + "\n");
    System.out.println("    Integral( f(x), x=a..b ) = " + integral);
    System.out.println("    Integral*2 = " + integral*2);
    System.out.println("    Numer of calls of rek " + irek);
    System.out.print  ("    Numer of function calls "+ifun);
    System.out.println("\n\n");
  }
}