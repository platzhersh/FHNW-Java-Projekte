#include "RandomAccessFile.hpp"
#include "PersistentVector.h"
#include <iostream>

int main() {
	RandomAccessFile file("test.bin");

	file.write(100);
	file.write(77);

	cout << file.read<int>(0) << " "
		<< file.read<int>(sizeof(int)) << endl;

	file.write((char)'c',2*sizeof(int));
	file.write((char)'h');

	cout << file.read<char>(2*sizeof(int)) << " "
		<< file.read<char>(2*sizeof(int)+sizeof(char)) << endl;


	PersistentVector<int> pv("test2.bin");
	if(pv.isEmpty()) {
		pv.push_back(99);
		pv.push_back(77);
	}
	else {
		cout << pv[0] << " ";
		cout << pv[1] << endl;
	}
}