/**
 *    @author:    Manfred Vogel
 *    @date:      February 2014
 *
 *           Numerik FS14
 *
 *  -------  Mueller-Verfahren                       ----------------
 *  -------  Uebung 2  Aufgabe 4.)                   ----------------
 *
 */

public class Mueller {
   static public void main( String[] args )  {   
// ------------------------------------------------------------------      
// ---------          Mueller Verfahren                --------------      
// ------------------------------------------------------------------      
      int j = 2                                        ;
      int M  = 500                                     ;
      double  err = 1.0E-8                             ;
      double  x                                        ;
      Complex a ,b ,c , z1 , z2 , nenn =new Complex()  ;
    
      InOut.println("")                                ;
      InOut.print("Start-Wert für Nullstelle eingeben:   ")  ;
      x = InOut.getDouble()                            ;                         
      Complex p0 = new Complex(x , Math.random()*10.0-5.0 )               ;                         
      Complex p1 = new Complex(x-0.5 , Math.random()*10.0-5.0 )           ;                         
      Complex p2 = new Complex(x+0.5 , Math.random()*10.0-5.0 )           ;                         
    
      do {
        c = f( p2 )                                   ;

        nenn = p0.mult(p0.sub(p0,p2) , p0.sub(p1,p2)) ;
        nenn = p0.mult(nenn, nenn.sub(p0,p1))         ;
        z1 = p0.mult( c.sub(p0,p2) , c.sub(f(p1),f(p2)) ) ;
        z2 = p1.mult( c.sub(p1,p2) , c.sub(f(p0),f(p2)) ) ;
       
        b =  z1.sub(z1.mult(z1 , p0.sub(p0,p2)), z2.mult(z2 , p1.sub(p1,p2))  ) ;
        b =  b.div(b , nenn)                          ;
       
        a =  z2.div(z2.sub(z2 , z1) , nenn )          ;

        nenn = b.sqrt(b.sub(b.mult(b,b) , a.mult(a.mult(a,4.0) , c) ) ) ;
        if (b.real(b) < 0 ) nenn = b.sub(b,nenn)      ;
           else
          nenn = b.add(b,nenn)                        ;
     
        a  = p2.sub(p2,nenn.div(c.mult(c,2.0) , nenn));
        p0 = p1                                       ;
        p1 = p2                                       ;
        p2 = a                                        ;
     
        j++                                           ;
        InOut.print(" Nullstelle "+j+" : ")           ;
        InOut.print(p2.real(p2), 4, 6)                ;
        InOut.print(" +  i * ")                       ;
        InOut.println(p2.imag(p2), 4, 6)              ;
      }
    while( (p2.betrag(p2.sub(p2,p1)) > err ) && (j<M) ) ;

    }  
  
  
  
    static private Complex f(Complex x) {				
    	Complex z = new Complex();
     
    	// x*(x*(x^2 -4)-3)+5
       z = z.mult(x,x)                                ;	// x^2
       z = z.add(z, -4.0)                             ; // x^2 - 4
       z = z.mult(x,z)                                ;	// x*(x^2 -4)
       z = z.add(z, -3.0)                             ; // x*(x^2 -4) -3
       z = z.mult(x,z)                                ; // x*(x*(x^2 -4)-3)
       z = z.add(z, 5.0)                              ;	// x*(x*(x^2 -4)-3)+5
    
    
//       z = z.add(x,1.0)                                ;      
//       z = z.mult(x,z)                                 ;
//       z = z.add(z, 3.0)                               ;
//       z = z.mult(x,z)                                 ;
//       z = z.add(z, 2.0)                               ;
//       z = z.mult(x,z)                                 ;
//       z = z.add(z, 2.0)                               ;

      return(z)                                       ;
    }
}

