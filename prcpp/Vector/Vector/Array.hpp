#include <iostream>

using  namespace std;

template <typename T>
struct Array {
	
private: 
	T *m_data;
	int m_N;
	
public:
	// constructor
	Array( T *data, int N) : m_data(data), m_N(N) { }
	
	int getLength() {
		return m_N;
	}

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

	bool operator==(Array<T> a) {
		if (m_N == a.m_N) {
			bool r = true;
			for (int i = 0; i < m_N; i++) {
				r = r && m_data[i] == a[i];
			}
			return r;
		}
		else {
			return false;
		}
	}
};