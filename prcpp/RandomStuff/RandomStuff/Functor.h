#pragma once
#include <iostream> 
#include <stdio.h>
#include <string>

using namespace std;

class Functor {

	int m_val;

public:
	Functor(int x) :m_val(x) {};

	int operator()(int i) {
		cout << i + m_val << endl;
		return i + m_val;
	}

};



int add2(int i) {
	cout << i + 2 << endl;
	return i + 2;
}

template<int val>
int addval(int i) {
	cout << i + val << endl;
	return i + val;
}

double my_divide(double a, double b) {
	return a / b;
}