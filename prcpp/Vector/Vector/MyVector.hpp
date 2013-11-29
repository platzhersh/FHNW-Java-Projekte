#include <iostream>

using namespace std;

template <typename Left, typename Op, typename Right, typename T>
struct Expr {
	Left m_left;
	Right m_right;
	Expr(Left t1, Right t2) : m_left(t1), m_right(t2) { }
	T operator[](int i) { return Op::apply(m_left[i], m_right[i]); }
};

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

/*template <Array<T> Left, typename T> struct  Mul {
	static T apply(T left, T right) {
		return left * right;
	}
};*/

template <typename T>
struct Array {
	T *m_data;
	int m_N;
	// constructor
	Array( T *data, int N) : m_data(data), m_N(N) { }
//	~Array() { ... }
	
	// assign an expression to the array
	template <typename Left, typename Op, typename Right>
	void operator=(Expr<Left,Op,Right,T> expr) {
		for ( int i = 0; i < m_N; ++i) {
			m_data[i] = expr[i];
		}
	}
	T operator[](int i) {
		return m_data[i];
	}
	void print() const {
		int l = 0; cout << '[';
		if (m_N > 0) cout << m_data[l++];
		while(l < m_N) cout << ',' << m_data[l++];
			cout << ']' << endl;
	}
};

// Wieso Left nicht als Array definiert? --> Weil bei verketteter Addition links auch eine Expr stehen kann
template <typename Left, typename T> Expr<Left, Plus<T>, Array<T>, T> operator+(Left a, Array<T> b) {
	return Expr<Left, Plus<T>, Array<T>, T>(a,b);
};

template <typename Left, typename T> Expr<Left, Minus<T>, Array<T>, T> operator-(Left a, Array<T> b) {
	return Expr<Left, Minus<T>, Array<T>, T>(a,b);
};

