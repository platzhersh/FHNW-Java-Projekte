package as;

final public class Complex {
	public static final Complex ZERO = new Complex(0, 0);

	public final double r, i;

	public Complex(double r, double i) {
		this.r = r;
		this.i = i;
	}

	public Complex plus(Complex c) {
		return new Complex(r + c.r, i + c.i);
	}

	public Complex sub(Complex c) {
		return new Complex(r - c.r, i - c.i);
	}

	public Complex times(Complex c) {
		return new Complex(r * c.r - i * c.i, i * c.r + r * c.i);
	}

	public Complex pow(int power) {
		if (power <= 0) throw new IllegalArgumentException();
		Complex tmp = this;
		for (int i = 0; i < power - 1; i++) {
			tmp = tmp.times(this);
		}
		return tmp;
		// KILLS PERFORMANCE BY A FACTOR OF 4!
		// return Stream.generate(() ->
		// this).limit(power).reduce(Complex::times).get();
	}

	public double abs() {
		return Math.sqrt(absSq());
	}

	public double absSq() {
		return r * r + i * i;
	}
}
