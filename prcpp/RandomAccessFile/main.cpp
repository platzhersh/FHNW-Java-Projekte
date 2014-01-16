#include "RandomAccessFile.hpp"
#include "PersistentVector.h"
#include <iostream>

int main() {

		cout << "Sorting Persistent Vector" << endl;

		cout << "-------------------------------" << endl;
	
		remove("test.bin");
		PersistentVector<int> pv2("test.bin");
		pv2.push_back(454);
		pv2.push_back(123);
		pv2.push_back(1231);
		pv2.push_back(2);
		pv2.push_back(49);
		pv2.push_back(201);
		pv2.push_back(99999);

		PersistentVector<int>::iterator a = pv2.begin();
		PersistentVector<int>::iterator z = pv2.end();


		while (a != z) {
			cout << "a++: " << *a++ << endl;
		}

		a = pv2.begin();

		cout << "-------------------------------" << endl;

		sort(a, z);

		cout << "sorting.." << endl;

		cout << "-------------------------------" << endl;

		while (a != z) {
			cout << "a++: " << *a++ << endl;
		}

}