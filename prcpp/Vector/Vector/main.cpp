#include "MyVector.hpp"

int main() {
	double a_data[] = { 2, 3, 5, 9 };
	double b_data[] = { 1, 0, 0 };
	double c_data[] = { 3, 0, 2, 5 };
	double d_data[4];
	Array<double> A(a_data, 4);
	Array<double> B(b_data, 4);
	Array<double> C(c_data, 4);
	Array<double> D(d_data, 4);
	D = A + B + C;
	D.print();
	D = A - B - C;
	D.print();

	return 0;
}