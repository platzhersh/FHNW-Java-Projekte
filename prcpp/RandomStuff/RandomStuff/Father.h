#pragma once
#include "Human.h"
#include <iostream>
#include <stdio.h>

using namespace std;

class Father : public Human {

public:

	Father() {
		cout << "Father Constructor called" << endl;
	}
	~Father() {
		cout << "Father Destructor called" << endl;
	}

	virtual void getName() {
		cout << "I'm Father." << endl;
	}



};