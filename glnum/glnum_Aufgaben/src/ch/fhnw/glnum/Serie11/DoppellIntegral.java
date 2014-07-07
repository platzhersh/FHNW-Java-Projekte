package ch.fhnw.glnum.Serie11;


/** 
 *    @author:    Manfred Vogel
 *    @date:      Mai 2014
 *    @filename:  DoppellIntegral
 *
 *      Grundlagen der Numerik /  Übung 11  / Aufgabe 1
 *
 *      Flächenintegrale 
 * 
 */

class DoppellIntegral {

// Integrationsintervalle [a, b] und [c, d]   und
  static final double a = 0.0;
  static final double b = 5.0;
  static final double c = 0.0;
  //static final double d = 2.0;		/* -> g(x) = x*(5-x)*/
  static private double g(double a2) {
	  return a2*(5-a2)/3;
  }

// # Teilintervalle für die Auswertung des (Teil-)Integrals mit der zusammengesetzten Simpson-Regel
  static final int n = 6;
  static final int m = 6;

// definiert die zu integrierende Funktion f
  static double f(double x, double y) {
      return (Math.pow(x, 2)*y+x*Math.pow(y, 2));
//    return Math.exp(y-x);
//    return (2.0*y*Math.sin(x) + Math.cos(x)*Math.cos(x));
  }

  static double k(double x, double c, int m) {
	  return (g(x)-c)/(2*m);
  }
  
  // implementiert die zusammengesetzte Simpson'sche Regel für Doppellintegrale
  static double simpson(double a, double b, double c, double d) {
    double h = (b-a)/(2*n);
    double k = (d-c)/(2*m);

      double sum= f(a,c) +  f(b,c) ;
      for (int i=1; i<=n-1; i++) sum += 2*f(a + 2 * i * h,c);
      for (int i=1; i<=n  ; i++) sum += 4*f(a + (2*i-1)*h,c);
 
      for (int j=1; j<=m-1; j++) {
         sum += 2*f(a , c + 2 * j * k(a, c, m));
         for (int i=1; i<=n-1; i++) sum += 4*f(a + 2 * i * h , c + 2 * j * k(a + 2 * i * h, c, m));
         for (int i=1; i<=n  ; i++) sum += 8*f(a + (2*i-1)*h , c + 2 * j * k(a + (2*i-1)*h, c ,m));
         sum += 2*f(b , c + 2 * j * k(b, c, m));
      }
       
      for (int j=1; j<=m; j++) {
         sum += 4*f(a , c + (2*j-1) * k(a,c,m));
         for (int i=1; i<=n-1; i++) sum += 8*f(a + 2 * i * h , c + (2*j-1)*k(a + 2 * i * h ,c,m));
         for (int i=1; i<=n  ; i++) sum +=16*f(a + (2*i-1)*h , c + (2*j-1)*k(a + (2*i-1)*h,c,m));
         sum += 4*f(b , c + (2*j-1)*k(b,c,m));
      }
       
      for (int i=1; i<=n-1; i++) sum += 2*f(a + 2 * i * h,d);
      for (int i=1; i<=n  ; i++) sum += 4*f(a + (2*i-1)*h,d);
      sum += f(a,d) +  f(b,d) ;  
      
      return sum*h*k(a,c,m)/9; 
  }
  
  public static void main(String[] args) {
    double integral = simpson(a, b, c, g(a));
    
    System.out.println("\n\n");
    System.out.println(" ** Doppel-Integral mit Simpson-Verfahren ** " + "\n");
    System.out.print  ("    Integral( f(x,y), x=a..b, y=c..d ) = " + integral);
    System.out.println("\n\n");
  }
}