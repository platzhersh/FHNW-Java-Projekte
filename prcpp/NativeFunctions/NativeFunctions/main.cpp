#include "Java\Test.h"
#include <iostream>
using namespace std;

JNIEXPORT void JNICALL Java_Test_display(JNIEnv*, jclass) {
cout << "C++: Hello, world!" << endl;
}
JNIEXPORT jint JNICALL Java_Test_increment (JNIEnv*, jclass, jint i) {
return i + 1;
}