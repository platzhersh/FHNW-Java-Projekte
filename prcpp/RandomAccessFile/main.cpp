#include "RandomAccessFile.hpp"
#include "PersistentVector.h"
#include <iostream>

int main() {
	
	remove("test3.bin");
	PersistentVector<int> pv("test3.bin");
		pv.push_back(99);
		pv.push_back(77);
		pv[0] = 100;
		pv.push_back(55);

		cout << "-------------------------------" << endl;

		PersistentVector<int>::iterator it = pv.begin();
		PersistentVector<int>::iterator end = pv.end();

		
		//todo fix iterator
		while (it != end) {
			cout << it++.get() << endl;
			
			}

		remove("test.bin");
		PersistentVector<int> pv2("test.bin");
		pv2.push_back(55);
		pv2.push_back(99);
		pv2.push_back(77);

		PersistentVector<int>::iterator a = pv2.begin();
		PersistentVector<int>::iterator z = pv2.end();
		PersistentVector<int>::iterator b = a+1;

		a = 10;
		b = 10;

		cout << "-------------------------------" << endl;

		/* TODO: fix this

		sort(a, z);
		
		*/
		
		while (a != z) {
			cout << "a++: " << a++.get() << endl;
		}
}