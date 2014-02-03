#pragma once
#include "Human.h"

int Human::count = 0;

Human::Human() {
	cout << "Human Constructor called" << endl;
	count++;
}
Human::~Human() {
	cout << "Human Destructor called" << endl;
	count++;
}

void Human::getName() {
	cout << "I'm Human." << endl;
}

void Human::getCount() {
	cout << "Human-instances: " << count << endl;
}