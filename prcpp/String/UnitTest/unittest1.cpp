#include "stdafx.h" 
#include "CppUnitTest.h" 
#include "MyString.h" 

using namespace Microsoft::VisualStudio::CppUnitTestFramework; 
namespace UnitTest { 
TEST_CLASS(MyString) { 
public: 
TEST_METHOD(Construction) { 
String s0; 
String s1(""); 
String s2("abc"); 
String s00(s0); 
String s11(s1); 
String s22(s2); 
} 
 
TEST_METHOD(Length) { 
Assert::IsTrue(String().length() == 0); 
Assert::IsTrue(String("").length() == 0); 
Assert::IsTrue(String("abc").length() == 3); 
} 
}; 
}
