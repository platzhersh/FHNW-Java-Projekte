package ch.fhnw.glnum.Serie11;
/** 
 *    @author:    Manfred Vogel
 *    @date:      Juni 2013
 *    @filename:  DoppelIntegral
 *
 *    Grundlagen der Numerik /  Micro-Test 5 / Aufgabe 2
 *
 *      Flächenintegrale 
 * 
 */

class Micro_5_DoppelIntegral_Variante{

  // Integrationsintervalle [a, b] und [c, d]   und 
  static final double a = 0.0;
  static final double b = Math.PI/4.0;
  
  // # Teilintervalle für die Auswertung des (Teil-)Integrals mit der zusammengesetzten Simpson-Regel
  static final int n = 6;
  static final int m = 6;
  
  // definiert die zu integrierende Funktion f
  static double f(double x, double y) {
	  return (Math.pow(x, 2)*y+x*Math.pow(y, 2));
  }
  
  // innere Grenzen  c und d
  static double c(double x) { return 0; }
  
  static double d(double x) { return (x*(5-x))/3; }
  
  static double k(double x) {
    double k = ( d(x)-c(x) ) /(2*m);
    return k;
  }
  static double Simp(double x) {
    double sum= f(x,c(x)) +  f(x,d(x)) ;
      for (int j=1; j<=m-1; j++) sum += 2*f(x,c(x)+2*j*k(x));
      for (int j=1; j<=m  ; j++) sum += 4*f(x,c(x)+(2*j-1)*k(x));
    return sum *=k(x)/3.0;
  }
  // implementiert die zusammengesetzte Simpson'sche Regel für Doppellintegrale
  static double simpson(double a, double b) {
    
    double h = (b - a) /(2*n);
    double Int=Simp(a);
    for (int i=1; i<=n-1; i++) Int += 2.0 * Simp(a+2*i*h);
    for (int i=1; i<=n; i++)   Int += 4.0 * Simp(a+(2*i-1)*h);
    Int +=Simp(b);
    
    return Int*h/3.0;
  }
  
  public static void main(String[] args) {
    long t;
    t = System.currentTimeMillis();
    
    double integral = simpson(a, b);
    
    t = System.currentTimeMillis() - t ;
    System.out.println("   Time needed  Funktional : "+t);
    System.out.println("\n\n");
    System.out.println(" ** Doppel-Integral mit Simpson-Verfahren ** " + "\n");
    System.out.print  ("    Integral( f(x,y), x=a..b, y=c..d ) = " + integral);
    System.out.println("\n\n");
  }
}