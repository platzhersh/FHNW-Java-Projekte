/**
 *    @author:    Manfred Vogel
 *    @date:      February 2014
 *
 *           Numerik FS14
 *
 *  -------  class Complex                           ----------------
 *  -------                                          ----------------
 *  -------  provides operations for complex numbers ----------------
 *  -------  like addition, multiplication, etc.     ----------------
 *  -------  the methods are self-explaining.        ----------------
 *  -------                                          ----------------
 */

class Complex {
  
  double a,b;
  public Complex() {  a=0 ;  b=0;  }
  public Complex(Complex c) { this.a=c.a ; this.b=c.b ;   }
  public Complex(double x, double y) { this.a=x ; this.b=y ; }
  
  public double real(Complex x) {
    double z = x.a ;
    return(z);
  }
  
  public double imag(Complex x) {
    double z = x.b ;
    return(z);
  }
  
  public Complex conj(Complex x) {
  
    Complex z=new Complex();
    z.a= x.a ;
    z.b=-x.b ;
    return(z);
  }

  public Complex mult(Complex x, Complex y) {
    
    Complex z=new Complex();
    z.a=x.a*y.a-x.b*y.b;
    z.b=x.a*y.b+x.b*y.a;
    return(z);
  }

  public Complex mult(Complex x, double y) {
    
    Complex z=new Complex();
    z.a=x.a*y ;
    z.b=x.b*y ;
    return(z);
  }

  public Complex div(Complex x, Complex y) {
    
    Complex z=new Complex()       ;
    double b = y.betrag(y)        ;
    b = 1.0 / (b*b)               ;
    z = x.mult(x, y.conj(y))      ;
    z = z.mult(z, b )             ;
    return(z)                     ;
  }
  
  public Complex add(Complex x, Complex y) {
    
    Complex z=new Complex();
    z.a=x.a+y.a;
    z.b=x.b+y.b;
    return(z);    
  }

  public Complex add(Complex x, double y) {
    
    Complex z=new Complex();
    z.a=x.a+y ;
    z.b=x.b   ;
    return(z);    
  }
  
  public Complex sub(Complex x, Complex y) {
    
    Complex z=new Complex();
    z.a=x.a-y.a;
    z.b=x.b-y.b;
    return(z);    
  }

  public Complex sqrt(Complex x) {
    
    Complex z=new Complex()     ;
    double phi , r              ;
    r = x.betrag(x)             ;
    r = Math.sqrt(r)            ;
    phi = Math.atan2(x.b , x.a) ;
    phi = phi / 2.0             ;
    z.a = r * Math.cos(phi)     ;
    z.b = r * Math.sin(phi)     ;
    return(z);    
  }

  public double betrag(Complex x) {
  
    return(Math.sqrt(x.a*x.a+x.b*x.b));
  }
  
  public String toString(){
	  return "" + a + " + i * " + b;
  }
}


