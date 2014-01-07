#include "RandomAccessFile.hpp"
#include "PersistentVector.h"
#include <iostream>

int main() {
	RandomAccessFile file("test.bin");

	file.write(100);
	file.write(77);

	cout << file.read<int>(0) << " "
		<< file.read<int>(sizeof(int)) << endl;

	file.write((char)'c', 2 * sizeof(int));
	file.write((char)'h');

	cout << file.read<char>(2 * sizeof(int)) << " "
		<< file.read<char>(2 * sizeof(int)+sizeof(char)) << endl;

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
		cout << "-------------------------------" << endl;
		//sort(a, z);
		//todo fix iterator
		while (a != z) {
			cout << a++.get() << endl;

		}

}