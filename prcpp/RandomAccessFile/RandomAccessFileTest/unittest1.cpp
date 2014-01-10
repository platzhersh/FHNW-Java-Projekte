#include "stdafx.h"
#include "CppUnitTest.h"
#include "RandomAccessFile.hpp"
#include "PersistentVector.h"
#include <iostream>

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace RandomAccessFileTest
{		
	TEST_CLASS(UnitTest1)
	{
	public:

		TEST_CLASS_INITIALIZE(setup) {
			// todo
		}
		TEST_CLASS_CLEANUP(teardown){

			// todo
		}
		
		
		TEST_METHOD(WriteReadTestInt)
		{

			RandomAccessFile file("test.bin");

			file.write(100);
			file.write(77);

			Assert::AreEqual(100, file.read<int>(0));
			Assert::AreEqual(77, file.read<int>(sizeof(int)));
		}

		TEST_METHOD(WriteReadTestChar)
		{
			RandomAccessFile file("test.bin");

			file.write('c');
			file.write('h');

			Assert::AreEqual('c', file.read<char>(0));
			Assert::AreEqual('h', file.read<char>(sizeof(char)));
		}

		TEST_METHOD(PersistentVectorWrite)
		{
			remove("test.bin");
			PersistentVector<int> pv("test.bin");
			Assert::IsTrue(pv.isEmpty());
			pv[0] = 13;
			Assert::AreEqual(13, (int) pv[0]);
			//Assert::AreEqual(99, pv[1]);
		}

		TEST_METHOD(PersistentVectorPush)
		{
			remove("test.bin");
			PersistentVector<int> pv("test.bin");
			Assert::AreEqual(pv.isEmpty(), true);
			pv.push_back(99);
			pv.push_back(77);

			Assert::AreEqual(99, (int) pv[0]);
			Assert::AreEqual(77, (int) pv[1]);
		}

		TEST_METHOD(IteratorSetGet)
		{
			remove("test.bin");
			PersistentVector<int> pv("test.bin");
			Assert::AreEqual(pv.isEmpty(), true);
			pv.push_back(99);
			pv.push_back(77);
			pv.push_back(55);

			PersistentVector<int>::iterator it = pv.begin();
			PersistentVector<int>::iterator end = pv.end();
			
			Assert::AreEqual(99, it.get());
			Assert::AreEqual(55, (--end).get());

			it.set(100);
			Assert::AreEqual(100, it.get());
		}
		TEST_METHOD(IteratorOperators)
		{
			remove("test.bin");
			PersistentVector<int> pv("test.bin");
			Assert::AreEqual(pv.isEmpty(), true);
			pv.push_back(99);
			pv.push_back(77);
			pv.push_back(55);

			PersistentVector<int>::iterator it = pv.begin();
			PersistentVector<int>::iterator end = pv.end();

			Assert::AreEqual(55, (--end).get());
			Assert::AreEqual((--end).get(), (++it).get());
			
			Assert::IsTrue(it == it);
			Assert::IsFalse(it != it);
			Assert::IsTrue(it == end);

			Assert::AreEqual(it++.get(), it.get());
			
			//it = end;
			
			Assert::IsFalse(it == end);
			Assert::IsTrue(it > end);
			Assert::IsFalse(it < end);

			Assert::AreEqual(55, it.get());

			Assert::AreEqual(99, (it-=2).get());
		}
		TEST_METHOD(Sort)
		{
			remove("test.bin");
			PersistentVector<int> pv("test.bin");
			Assert::AreEqual(pv.isEmpty(), true);
			pv.push_back(55);
			pv.push_back(99);
			pv.push_back(77);

			PersistentVector<int>::iterator a = pv.begin();
			PersistentVector<int>::iterator z = pv.end();

			//sort(a, z);
		}


	};
}