#pragma once
#include "Human.h"
#include <iostream>
#include <stdio.h>

using namespace std;

class Mother : public Human {

public:

	Mother() {
		cout << "Mother Constructor called" << endl;
	}
	~Mother() {
		cout << "Mother Destructor called" << endl;
	}

	void getName() {
		cout << "I'm Mother." << endl;
	}


};