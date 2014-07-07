package ch.fhnw.glnum.Serie10;
/** 
 *    @author:    Manfred Vogel
 *    @date:      9. Mai 2014
 *    @filename:  AdaptiveQuadraturSimpson
 *
 *      Grundlagen der Numerik /  Übung 9  / Aufgabe 2 
 *
 *      Adaptives Quadratur-Verfahren (mit Simpson-Integration)
 * 
 */

class AdaptiveQuadraturSimpson {

// Integrationsintervall [a, b]
  static final double a = 0.0;
  static final double b = 10.0;
  static int ifun=0;

// # Teilintervalle für die Auswertung des (Teil-)Integrals mit der zusammengesetzten Simpson-Regel
  static final int n = 10;
  static int irek=0;

// definiert die zu integrierende Funktion f
  static double f(double x) {
    ifun++;
    return Math.sin(x*x*x) * Math.exp(-x/Math.exp(1.0));
  }

// implementiert die zusammengesetzte Simpson'sche Regel
  static double simpson(double a, double b) {
    double h = (b-a)/n;
    double sum1=0.0, sum2=0.0;
      for (int i=1; i<=(n/2)-1; i++) sum1 += f(a + 2*h*i);
      for (int j=1; j<=n/2; j++)     sum2 += f(a + h*(2*j-1));
    return h/3 * ( f(a) + f(b) + 2*sum1 + 4*sum2 );
  }
  
  static double rek(double a, double b, double eps) {
    irek++;
    double intTot=0.0, intLeft=0.0, intRight=0.0;
    intTot   = simpson( a      , b      );
    intLeft  = simpson( a      , (a+b)/2);
    intRight = simpson( (a+b)/2, b      );
    if ( Math.abs( intTot - intLeft - intRight ) > eps )  
        return rek(a, (a+b)/2, eps/2.0) + rek((a+b)/2, b, eps/2.0);
      else 
        return intLeft + intRight;  
  }               

  public static void main(String[] args) {
    double integral = rek(a, b, 1e-6);
    
    System.out.println("\n\n");
    System.out.println(" ** Adaptive Quadratur mit Simpson-Verfahren ** " + "\n");
    System.out.println("    Integral( f(x), x=a..b ) = " + integral);
    System.out.println("    Numer of calls of rek " + irek);
    System.out.print  ("    Numer of function calls "+ifun);
    System.out.println("\n\n");
  }
}