package ch.fhnw.glnum.Serie09;
/** 
 *    @author:    Manfred Vogel
 *    @date:      2.Mai 2014
 *    @filename:    Simpson
 *
 *      Grundlagen der Numerik /  Übung 8  / Aufgabe 2 
 *      Zusammengesetzte Simpson-Regel
 * 
 */

class Simpson{

  // # of intervalls
  static final int n = 3;

// Integrationsintervall [a, b]
//  static final double a = 0.0;
//  static final double a = 1.0;
//    static final double a = 3.0;
  static final double a = 2.0;
  
//  static final double b = 1.0;
//    static final double b = 2.0;
//    static final double b = 5.0;
//  static final double b = Math.PI;
      static final double b = 10.0;
  
  // definiert die zu integrierende Funktion f
  static double f(double x)   {
//	  return ( x*Math.log(x) ) ;
//    return ( 1.0/Math.sqrt(x*x-4.0) ) ;
//  return ( x*x*Math.exp(-x) ) ;
//  return ( x*x*Math.cos(x) ) ;
    return (Math.sqrt(x)/(Math.sqrt(Math.pow(x, 2)-2)));  
//  if (x == 0.0 ) return 1.0; return ( Math.sin(x)/x ) ;
    
//  return ( Math.exp(-x*x) ) ;
  }
  
  
  // implementiert die zusammengesetzte Simpson-Regel
  static double simpson(double a, double b, int n)  {
    double h = (b-a)/n;
    double sum= f(a)+f(b);
    for (int j=1; j<=(n/2)-1 ; j++) sum += 2*f(a+2*j*h);
    for (int j=1; j<=(n/2)   ; j++) sum += 4*f(a+(2*j-1)*h);
    return sum*h/3.0;
  }

  public static void main(String[] args)  {
    double integral = simpson(a, b, n);
    
    System.out.println("\n\n");
    System.out.println(" ** Zusammengesetzte Simpson-Integration ** " + "\n");
    if ( integral == -999.999) System.out.print  ("    Integral( f(x), x=a..b )    not converged "); 
    else System.out.print  ("    Integral( f(x), x=a..b ) = " + integral);
    System.out.println("\n\n");
  }
}

