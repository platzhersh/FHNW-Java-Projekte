package ch.fhnw.glnum.Serie11;
/** 
 *    @author:    Manfred Vogel
 *    @date:      Juni 2013
 *    @filename:  DoppelIntegral
 *
 *    Grundlagen der Numerik /  Micro-Test 5 / Aufgabe 2
 *
 *      Flächenintegrale 
 */

class Micro_5_DoppelIntegral {

  // Integrationsintervalle [a, b] und [c, d]   und 
  static final double a = 0.0;
  static final double b = Math.PI/4.0;
  
  // # Teilintervalle für die Auswertung des (Teil-)Integrals mit der zusammengesetzten Simpson-Regel
  static final int n = 500;
  static final int m = 500;
  
  // definiert die zu integrierende Funktion f
  static double f(double x, double y) {
    return (2.0*y*Math.sin(x) + Math.cos(x)*Math.cos(x));
  }
  
  // innere Grenzen c und d
  static double c(double x) { return Math.sin(x); }

  static double d(double x) { return Math.cos(x); }
  
  // implementiert die zusammengesetzte Simpson'sche Regel für Doppellintegrale
  static double simpson(double a, double b) {
    double h = (b-a)/(2*n);
    
    double k = ( d(a)-c(a) ) /(2*m);
      double sum= f(a,c(a)) +  f(a,d(a)) ;
      for (int j=1; j<=m-1; j++) sum += 2*f(a,c(a)+2*j*k);
      for (int j=1; j<=m  ; j++) sum += 4*f(a,c(a)+(2*j-1)*k);
    double Int=sum*k;
    
    for (int i=1; i<=n-1; i++) {
      k = ( d(a+2*i*h)-c(a+2*i*h) ) /(2*m);
      sum = f(a+2*i*h , c(a+2*i*h) ) + f(a+2*i*h , d(a+2*i*h) );
        for (int j=1; j<=m-1; j++) sum += 2*f(a+2*i*h,c(a+2*i*h)+2*j*k);
        for (int j=1; j<=m  ; j++) sum += 4*f(a+2*i*h,c(a+2*i*h)+(2*j-1)*k);
      Int += 2*k * sum ;
    }
    
    for (int i=1; i<=n; i++) {
      k = ( d(a+(2*i-1)*h)-c(a+(2*i-1)*h) ) /(2*m);
      sum = f(a+(2*i-1)*h , c(a+(2*i-1)*h) ) + f(a+(2*i-1)*h , d(a+(2*i-1)*h));
        for (int j=1; j<=m-1; j++) sum += 2*f(a+(2*i-1)*h,c(a+(2*i-1)*h)+2*j*k);
        for (int j=1; j<=m  ; j++) sum += 4*f(a+(2*i-1)*h,c(a+(2*i-1)*h)+(2*j-1)*k);
      Int += 4*k * sum ;
    }
    
    k = ( d(b)-c(b) ) /(2*m);
    sum = f(b , c(b) ) + f(b , d(b) );
      for (int j=1; j<=m-1; j++) sum += 2*f(b,c(b)+2*j*k);
      for (int j=1; j<=m  ; j++) sum += 4*f(b,c(b)+(2*j-1)*k);
    Int += k * sum ;
    
    return Int*h/9.0;
  }
  
  public static void main(String[] args) {
    long t;
    t = System.currentTimeMillis();

    double integral = simpson(a, b);

    t = System.currentTimeMillis() - t ;
    System.out.println("  Time needed  ausprogrammier: "+t);
    System.out.println("\n\n");
    System.out.println(" ** Doppel-Integral mit Simpson-Verfahren ** " + "\n");
    System.out.print  ("    Integral( f(x,y), x=a..b, y=c..d ) = " + integral);
    System.out.println("\n\n");
  }
}