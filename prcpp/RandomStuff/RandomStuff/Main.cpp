#include <iostream>
#include <stdio.h>
#include <functional>

#include "Child.h"

#include "StateMachine.h"
#include "StateMachine2.h"

#include "BitFieldsAndUnions.h"
#include "RelationalOperator.h"

#include "Functor.h"
#include <algorithm>
#include <vector>

using namespace std::placeholders;

int main(int args, const char * argv[]) {

	cout << "------" << endl;

	StateMachine2 sm2 = StateMachine2();
	sm2.run();


	cout << "------" << endl;

	RelationalOperator r1(1);
	RelationalOperator r2(2);

	cout << (r1 < r2) << endl;
	cout << (r1 > r2) << endl;
	cout << (r1 == r2) << endl;
	cout << (r1 != r2) << endl;

	cout << "------" << endl;


	StateMachine sm = StateMachine();
	sm.start();

	Child child = Child();
	child.getName();
	child.getCount();


	const uint32_t type = 0x12345678;
	CPU info;
	info.num = type;
	cout << info.id.Stepping << " " << info.id.Model << endl;

	vector<int> v = { 1, 2, 3, 4 };
	vector<int> v2;
	v2.resize(v.size());
	
	int x = 2;

	for_each(v.begin(), v.end(), add2);
	for_each(v.begin(), v.end(), addval<3>);
	for_each(v.begin(), v.end(), Functor(x));
	for_each(v.begin(), v.end(), [x](int v){
		cout << v + x << endl;
	});

	cout << "------";

	transform(v.begin(), v.end(), v2.begin(), [x](int v){
		cout << v + x << endl;
		return v + x;
	});


	cout << "------" << endl;

	vector<int>::iterator y = find_if(v.begin(), end(v), [](int v) {
		if (v % 2 == 0) {
			cout << v << endl;
			return true;
		}
		else return false;		
	});

	cout << *y << endl;

	function<double(double a, double b)> func;
	func = bind(my_divide, _2, _1);

	cout << func(1, 3) << endl;

	return 0;
}