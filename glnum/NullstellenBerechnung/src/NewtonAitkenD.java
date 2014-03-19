/**
 *    @author:    Manfred Vogel
 *    @date:      February 2014
 *
 *           Numerik FS14
 *
 *  -------  Newton-Aitken Verfahren (double)        ----------------
 *  -------  Uebung 2  Aufgabe 3.)                   ----------------
 *
 */

public class NewtonAitkenD  {
   static public void main( String[] args )  {   
// ------------------------------------------------------------------      
// ---------    Newton Verfahren mit double numbers    --------------      
// ------------------------------------------------------------------      
      int j = 0                                        ;
      int M  = 500                                     ;
      double  err = 1.0E-9                             ;
      double [] x = new double[M+1]                    ;
      double  a                                        ;
      
      InOut.println("")                                ;
      InOut.print("Start-Wert für Nullstelle eingeben : ")  ;
      x[0] = InOut.getDouble()                         ;
 
      InOut.println(" Startwert eingelesen : "+x[0])   ;

      do { 
        j++                                            ;
        x[j] = x[j-1] - f(x[j-1])/f1(x[j-1])           ;
        InOut.print(" Nullstelle    = "+j+" ")         ;
        InOut.print(x[j], 4, 10)                       ;
        InOut.print("      Funktionswert = ")          ;
        InOut.println(f(x[j]) )                        ;
      }
      while( (Math.abs(x[j-1]-x[j]) > err ) && (j<M) ) ;
    
      if(j >= M ) 
         InOut.println(" Newton-Verfahren konvergiert nicht") ;
        else {
          InOut.println(" ")                           ; 
          InOut.println(" Iteration      Newton             Aitken ") ; 
          InOut.println("-----------------------------------------------") ; 
          for( int i=0 ; i<=j-2 ; i++ ) { 
             a =(x[i] - Math.pow(x[i+1]-x[i] , 2 ) /(x[i+2]-2.0*x[i+1]+x[i]) );
             InOut.print("    "+i)                  ;
             InOut.print(x[i], 8, 11)               ;
             InOut.println(a, 6, 15)                ;
          }
          InOut.print("    "+(j-1) )                ; 
          InOut.println(x[j-1], 8, 11)              ; 
          InOut.print("    "+(j) )                  ; 
          InOut.println(x[j], 8, 11)                ; 
        }   
    }  
  
    static private double f(double x) { 
      double y                                      ;
      y=  (Math.exp(-x)+Math.pow(2, -x)+2*Math.cos(x)-6);
      return(y)                                     ;
    }

    static private double f1(double x) { 
      double d=0.0001                               ;
      double y= (f(x+d) - f(x)) / d                 ;
      if (y == 0) y= (d*Math.random())              ;
      return y                                      ;
    }
  
}
