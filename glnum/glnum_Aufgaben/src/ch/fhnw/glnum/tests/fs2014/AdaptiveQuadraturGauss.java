package ch.fhnw.glnum.tests.fs2014;


class AdaptiveQuadraturGauss {

  // Integrationsintervall [a, b]
  static final double a = 0.0;
  static final double b = 1.0;
  static int ifun = 0;		// function call counter
  
// Stützstellen und Gewichte für Gauss-Quadratur der Ordnung 3
  static final int n = 4;
  static int irek=0;
  static final double roots[]   = 
  { 0.9061798459, 0.5384693101, 0.0,          -0.5384693101, -0.9061798459 };

  static final double weights[] =
  { 0.2369268850, 0.4786286705, 0.5688888889, 0.4786286705, 0.2369268850 };

// definiert die zu integrierende Funktion f
  static double f1(double x) {
    ifun++;
    return (1/(Math.pow(x,2)+1));
  }
//definiert die zu integrierende Funktion f
 static double f2(double x) {
   ifun++;
   return (1/(Math.pow(x, 2)+1));
 }

// implementiert die Gauss-Quadratur
  static double gauss1(double a, double b) {
    double sum=0.0;
    for (int j=0; j<n ; j++)
       sum += weights[j] * f1( ((b-a)*roots[j]+b+a)/2.0 );
    return sum*(b-a)/2.0;
  }
//implementiert die Gauss-Quadratur
 static double gauss2(double a, double b) {
   double sum=0.0;
   for (int j=0; j<n ; j++)
      sum += weights[j] * f2( ((b-a)*roots[j]+b+a)/2.0 );
   return sum*(b-a)/2.0;
 }
  
  
  static double rek1(double a, double b, double eps) {
    irek++;
    double intTot=0.0, intLeft=0.0, intRight=0.0;
    intTot   = gauss1( a      , b      );
    intLeft  = gauss1( a      , (a+b)/2);
    intRight = gauss1( (a+b)/2, b      );
    if ( Math.abs( intTot - intLeft - intRight ) > eps )  
        return rek1(a, (a+b)/2, eps/2.0) + rek1((a+b)/2, b, eps/2.0);
      else 
        return intLeft + intRight;  
  }               

  static double rek2(double a, double b, double eps) {
	    irek++;
	    double intTot=0.0, intLeft=0.0, intRight=0.0;
	    intTot   = gauss2( a      , b      );
	    intLeft  = gauss2( a      , (a+b)/2);
	    intRight = gauss2( (a+b)/2, b      );
	    if ( Math.abs( intTot - intLeft - intRight ) > eps )  
	        return rek2(a, (a+b)/2, eps/2.0) + rek2((a+b)/2, b, eps/2.0);
	      else 
	        return intLeft + intRight;  
	  }               

  
  public static void main(String[] args) {
    double integral1 = rek1(a, b, 1e-6);
    double integral2 = rek2(a, b, 1e-6);
    
    System.out.println("\n\n");
    System.out.println(" ** Adaptive Quadratur mit Gauss-Verfahren ** " + "\n");
    System.out.println("    Integral1( f1(x), x=a..b ) = " + integral1);
    System.out.println("    Integral2( f2(x), x=a..b ) = " + integral2);
    System.out.println("    Integral2 + Integral2 = " + (integral1 + integral2));
    System.out.println("\n\n");
  }
}