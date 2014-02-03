#pragma once
#include <iostream>
#include <stdio.h>

using namespace std;

class Human {

	static int count;

protected:

	Human();
	~Human();

	void getName();

public:

	static void getCount();

};