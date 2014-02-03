#pragma once
#include "Father.h"
#include "Mother.h"
#include <iostream>
#include <stdio.h>

using namespace std;

class Child : public virtual Father, virtual Mother{

public:

	Child() {
		cout << "Child Constructor called" << endl;
	}
	~Child() {
		cout << "Child Destructor called" << endl;
	}

	virtual void getName() override {
		cout << "I'm Child." << endl;
	}


};