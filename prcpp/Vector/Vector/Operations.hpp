template <typename T> struct  Plus {
	static T apply(T left, T right) {
		return left + right;
	}
};

template <typename T> struct  Minus {
	static T apply(T left, T right) {
		return left - right;
	}
};

template <typename T> struct  Mul {
	static T apply(T left, T right) {
		cout << "left" << left << " * " << "right" << right << endl;
		return left * right;
	}
};


// Wieso Left nicht als Array definiert? --> Weil bei verketteter Addition links auch eine Expr stehen kann
template <typename Left, typename T> 
Expr<Left, Plus<T>, Array<T>, T> operator+(Left a, Array<T> b) {
	return Expr<Left, Plus<T>, Array<T>, T>(a,b);
};

template <typename Left, typename Right> 
Expr<Left, Plus<double>, Right, double> operator+(Left a, Right b) {
	return Expr<Left, Plus<double>, Right, double>(a,b);
};

template <typename Left, typename T> 
Expr<Left, Minus<T>, Array<T>, T> operator-(Left a, Array<T> b) {
	return Expr<Left, Minus<T>, Array<T>, T>(a,b);
};
template <typename Left, typename Right>
Expr<Left, Minus<double>, Right, double> operator-(Left a, Right b) {
	return Expr<Left, Minus<double>, Right, double>(a, b);
};
/* -------------------------------------------------------

	Multiplikationen

------------------------------------------------------- */

// Multiplikation Vektor * Vektor -> Skalar
template <typename T>
Expr<Array<T>, Mul<T>, Array<T>, T> operator*(Array<T> a, Array<T> b) {
	return Expr<Array<T>, Mul<T>, Array<T>, T>(a, b);
};

// Multiplikation double * Skalar -> Skalar
template <typename T>
Expr<Scalar<T>, Mul<T>, Scalar<T>, T> operator*(double a, Scalar<T> b) {
	cout << "Multiplikation double * T -> Scalar" << endl;
	return Expr<Scalar<T>, Mul<T>, Scalar<T>, T>(a, b);
};

// Multiplikation dobule * Vektor -> Vektor
template <typename T>
Expr<Scalar<T>, Mul<T>, Array<T>, T> operator*(double a, Array<T> b) {
	cout << "Multiplikation dobule * Vektor -> Vektor" << endl;
	return Expr<Scalar<T>, Mul<T>, Array<T>, T>(a, b);
};

// Multiplikation T * Vektor -> Vektor
template <typename T, typename Left> 
Expr<Left, Mul<T>, Array<T>, T> operator*(Left a, Array<T> b) {
	return Expr<Left, Mul<T>, Array<T>, T>(a, b);
};

// Multiplikation Vektor * T -> Vektor
template <typename T, typename Right>
Expr<Array<T>, Mul<T>, Right, T> operator*(Array<T> a, Right b) {
	return Expr<Array<T>, Mul<T>, Right, T>(a, b);
};

// Multiplikation Scalar * Scalar -> Scalar
template <typename T>
Expr<Scalar<T>, Mul<T>, Scalar<T>, T> operator*(Scalar<T> a, Scalar<T> b) {
	cout << "Multiplikation Scalar * Scalar -> Scalar" << endl;
	return Expr<Scalar<T>, Mul<T>, Scalar<T>, T>(a, b);
};