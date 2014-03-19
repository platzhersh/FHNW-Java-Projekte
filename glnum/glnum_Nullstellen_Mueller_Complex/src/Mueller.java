/**
 * @author: Manfred Vogel
 * @date: February 2014
 * 
 *        Numerik FS14
 * 
 *        ------- Mueller-Verfahren ---------------- ------- Uebung 2 Aufgabe
 *        4.) ----------------
 * 
 */

public class Mueller {
	static public void main(String[] args) {
		// ------------------------------------------------------------------
		// --------- Mueller Verfahren --------------
		// ------------------------------------------------------------------
		int j = 2;
		int M = 500;
		double err = 1.0E-8;
		double x;
		Complex a, b, c, z1, z2, nenn = new Complex();

		InOut.println("");
		// InOut.print("Start-Wert für Nullstelle eingeben:   ") ;
		// x = InOut.getDouble() ;
		// Complex p0 = new Complex(x , Math.random()*10.0-5.0 ) ;
		// Complex p1 = new Complex(x-0.5 , Math.random()*10.0-5.0 ) ;
		// Complex p2 = new Complex(x+0.5 , Math.random()*10.0-5.0 ) ;
		Complex p0 = new Complex(-2, -1);
		Complex p1 = new Complex(-0.5, 1);
		Complex p2 = new Complex(0, 2);

		do {
			c = f(p2);

			nenn = p0.mult(p0.sub(p0, p2), p0.sub(p1, p2));
			nenn = p0.mult(nenn, nenn.sub(p0, p1));
			z1 = p0.mult(c.sub(p0, p2), c.sub(f(p1), f(p2)));
			z2 = p1.mult(c.sub(p1, p2), c.sub(f(p0), f(p2)));

			b = z1.sub(z1.mult(z1, p0.sub(p0, p2)), z2.mult(z2, p1.sub(p1, p2)));
			b = b.div(b, nenn);

			a = z2.div(z2.sub(z2, z1), nenn);

			nenn = b.sqrt(b.sub(b.mult(b, b), a.mult(a.mult(a, 4.0), c)));
			if (b.real(b) < 0)
				nenn = b.sub(b, nenn);
			else
				nenn = b.add(b, nenn);

			a = p2.sub(p2, nenn.div(c.mult(c, 2.0), nenn));

			Complex _p0 = p0, _p1 = p1, _p2 = p2;

			p0 = p1;
			p1 = p2;
			p2 = a;

			j++;
			InOut.print(" Nullstelle " + j + " : ");
			InOut.print(p2.real(p2), 4, 6);
			InOut.print(" +  i * ");
			InOut.println(p2.imag(p2), 4, 6);

			// Berechne Aitken aus den letzten 3 Werten der Müller Funktion -
			// Imanol Schlag
			InOut.print("Aitken: " + aitken(_p0, _p1, _p2) + "\n\n");

		} while ((p2.betrag(p2.sub(p2, p1)) > err) && (j < M));

	}

	private static Complex aitken(Complex p0, Complex p1, Complex p2) {

		Complex zaehler = p0.mult(p0.sub(p1, p0), p0.sub(p1, p0));
		Complex nenner = p0.add(p0.sub(p2, p0.mult(p1, 2)), p0);

		return p0.sub(p0, p0.div(zaehler, nenner));
	}

	/*
	 * Matlab Aitken:
	 * 
	 * format long 
	 * syms p0 p1 p2 
	 * aitken = p0 - ((p0 - p1)^2 / (p2 - 2*p1 + p0))
	 * eval(subs(aitken,{p0,p1,p2},{ -2-i, -0.5+i, 2i}))
	 * 
	 * 
	 * Grenzwert: limit( x^2 + 2*(log(x) + 1 - 2*x),x,1)
	 */

	static private Complex f(Complex x) {
		Complex z = new Complex();
		z = z.mult(x, x);
		z = z.add(z, 1);
		z = z.mult(z, x);
		z = z.mult(z, x);
		z = z.add(z, 1);

		/*
		 * ((x^2 -4)*x -3)*x + 5
		 * 
		 * z = z.mult(x,x) ; z = z.add(z, -4.0) ; z = z.mult(x,z) ; z = z.add(z,
		 * -3.0) ; z = z.mult(x,z) ; z = z.add(z, 5.0) ;
		 */

		// z = z.add(x,1.0) ;
		// z = z.mult(x,z) ;
		// z = z.add(z, 3.0) ;
		// z = z.mult(x,z) ;
		// z = z.add(z, 2.0) ;
		// z = z.mult(x,z) ;
		// z = z.add(z, 2.0) ;

		return (z);
	}
}
