#include <iostream>

using  namespace std;

template <typename T>
struct Scalar {
	T m_value;
	// constructor
	Scalar( T data) : m_value(data) { }
	Scalar() : m_value(0) { }
	
	// assign an expression to the array
	template <typename T, typename Op>
	void operator=(Expr<Array<T>, Op, Array<T>, T> expr) {
		m_value = 0;
		for ( int i = 0; i < expr.m_left.getLength(); ++i) {
			m_value += expr[i];
		}
	}
	
	T operator[](int i) {
		return m_value;
	}
	void print() const {
		cout << m_value << endl;
	}
};