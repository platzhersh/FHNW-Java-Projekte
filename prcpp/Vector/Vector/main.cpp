#include <iostream>
#include "Expression.hpp"
#include "Array.hpp"
#include "Scalar.hpp"
#include "Operations.hpp"

using namespace std;

int main() {
	double k = 5;
	double l = 3.14;
	double a_data[] = { 2, 3, 5, 9 };
	double b_data[] = { 1, 0, 0, 1 };
	double c_data[] = { 3, 0, 2, 5 };
	double d_data[4];
	Array<double> A(a_data, 4);
	Array<double> B(b_data, 4);
	Array<double> C(c_data, 4);
	Array<double> D(d_data, 4);
	D = k*A + B + l*C;
	D.print();

	return 0;
}