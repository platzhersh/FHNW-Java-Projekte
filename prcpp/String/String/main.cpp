#include "MyString.h"
using namespace std;

int main() {

	String s001("ABC");
	String s002 = s001.concat('D');
	String s003("DEFG");
	String s004 = s001.concat(s003);
	cout << "s002.getLength() = "<< s002.getLength() << endl;
	cout << "s005.getStart() = " << s002.getStart() << endl;
	s002.print();
	//s004.print();

	cout << "-----------------------------------" << endl;

	String s005 = s004.substring(3,7);
	cout << "s004.substring(3,7)" << endl;
	cout << "s005.getLength() = "<< s005.length() << endl;
	cout << "s005.getStart() = " << s005.getStart() << endl;
	
	s005.print();

	cout << s005.toCString().get() << endl;

	cout << "-----------------------------------" << endl;

	int i = 1;
	int *pi = &i;

	shared_ptr<int> sp1(new int(2));
	cout << "use count: " << sp1.use_count() << endl;
	cout << "unique: " << sp1.unique() << endl;
	shared_ptr<int> sp2(sp1);

	cout << "use count: " << sp1.use_count() << endl;
	cout << "unique: " << sp1.unique() << endl;

	sp2.reset();

	cout << "use count: " << sp1.use_count() << endl;
	cout << "unique: " << sp1.unique() << endl;

	cout << "-----------------------------------" << endl;

	return 0;
}