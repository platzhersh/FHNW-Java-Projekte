package ch.fhnw.glnum.Serie9;

/** 
 *    @author:    Manfred Vogel
 *    @date:      2. Mai 2014
 *    @filename:    GaussQuadratur
 *
 *      Grundlagen der Numerik /  Übung 9  / Aufgabe 3
 *      Gauss-Quadratur
 * 
 */


class GaussQuadratur {

  // Ordnung, Stützstellen und Gewichte der Gauss-Quadratur mit Maple bestimmt
  static final int n = 6;
  static final double roots[][]   = {
         { 0.0 },
         { 0.0 },
         { 0.5773502692,-0.5773502692 },
         { 0.7745966692, 0.0         ,-0.7745966692 },
         { 0.8611363116, 0.3399810436,-0.3399810436, -0.8611363116 },
         { 0.9061798459, 0.5384693101, 0.0,          -0.5384693101, -0.9061798459 },
         { 0.9324695142, 0.6612093865, 0.2386191861, -0.2386191861, -0.6612093865, -0.9324695142 } };
  static final double weights[][] = { 
         { 2.0 },
         { 2.0 },
         { 1.0         , 1.0          },
         { 0.5555555556, 0.8888888889, 0.5555555556 },
         { 0.3478548451, 0.6521451549, 0.6521451549, 0.3478548451 },
         { 0.2369268850, 0.4786286705, 0.5688888889, 0.4786286705, 0.2369268850 },
         { 0.1713244924, 0.3607615729, 0.4679139349,  0.4679139349,  0.3607615729,  0.1713244924 } };


  // implementiert die Gauss-Quadratur
  static double gauss(double a, double b, int n) {
    double sum=0.0;
    for (int j=0; j<n ; j++){
       sum += weights[n][j] * f( ((b-a)*roots[n][j]+b+a)/2.0 );
       // Auswertungspunkte ausgeben
       System.out.println(((b-a)*roots[n][j]+b+a)/2.0);  
    }
    return sum*(b-a)/2.0;
  }
  

  public static void main(String[] args) {
    System.out.println("\n");
    System.out.println("     Integral( f(x), x=a..b ) :"+"\n");
    for (int j=2; j<=6 ; j++)
       System.out.println(" *** Gauss-Quadratur "+j+". Ordnung : " + gauss(a, b, j) );
  }

  // Integrationsintervall [a, b]
//  static final double a = 0.0;
//  static final double a = 1.0;
//  static final double a = 3.0;
//  	static final double a = 3.0;
  static final double a = 0;
  
  
//  static final double b = 1.0;
//  static final double b = 2.0;
//  static final double b = 5.0;
//  static final double b = Math.PI;
//  	static final double b = .0;
  static final double b = Math.PI;
  
  // definiert die zu integrierende Funktion f
  static double f(double x)  {
//      return ( x*Math.log(x) ) ;
//      return ( 1.0/Math.sqrt(x*x-4.0) ) ;
//      return ( x*x*Math.exp(-x) ) ;
//      return ( x*x*Math.cos(x) ) ;
//	  	return (Math.sqrt(x)/Math.sqrt(Math.pow(x, 2)-4));
	  return ((Math.exp(-(Math.pow(x/2,2)))*Math.sin(x))/(1+Math.pow(x, 2)));
    
//    if (x == 0.0 ) return 1.0; return ( Math.sin(x)/x ) ;
    
//      return ( Math.exp(-x*x) ) ;
  }
  
}


