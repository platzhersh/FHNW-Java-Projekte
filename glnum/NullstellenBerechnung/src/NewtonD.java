/**
 *    @author:    Manfred Vogel
 *    @date:      February 2014
 *
 *           Numerik FS14
 *
 *  -------  Newton-Verfahren mit double precision   ----------------
 *  -------  Uebung 2  Aufgabe 1.)                   ----------------
 *
 */

public class NewtonD  {
   static public void main( String[] args )  {   
// ------------------------------------------------------------------      
// ---------    Newton Verfahren mit double numbers    --------------      
// ------------------------------------------------------------------      
      int j = 0                                        ;
      int M  = 500                                     ;
      double  err = 1.0E-12                            ;
      double [] x = new double[M+1]                    ;
      double  a                                        ;
      
      InOut.println("")                                ;
      InOut.print("Start-Wert für Nullstelle eingeben:   ")  ;
      x[0] = InOut.getDouble()                         ;                         

      InOut.println(" Startwert eingelesen : "+x[0])   ;

      do { 
         j++                                           ;
         x[j] = x[j-1] - f(x[j-1])/f1(x[j-1])          ;
         InOut.print(" Nullstelle    = "+j)            ;
         InOut.print(x[j], 4, 10)                      ;
         InOut.print("      Funktionswert = ")         ;
         InOut.println(f(x[j]) )                       ;
      }
      while( (Math.abs(x[j-1]-x[j]) > err ) && (j<M) ) ;
    }  
  
    static private double f(double x)  { 
      double y                                         ;
         y= (Math.exp(-x) + Math.pow(2.0,-x) + 2.0*Math.cos(x) - 6.0) ;
     //    y= (Math.exp(6*x) + 3.0*Math.log(2.0)*Math.log(2.0)*Math.exp(2*x) - Math.log(8)*Math.exp(4*x) - Math.pow(Math.log(2.0) ,3) );
      return(y)                                        ;
    }

    static private double f1(double x) { 
      double eps=0.0001                                ;
      return ( f(x+eps) - f(x) ) / eps                 ;
    }
  
}
