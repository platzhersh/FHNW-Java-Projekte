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
			Assert::AreEqual(13, (int)pv[0]);
			//Assert::AreEqual(99, pv[1]);
		}

		TEST_METHOD(PersistentVectorPush)
		{
			remove("test.bin");
			PersistentVector<int> pv("test.bin");
			Assert::AreEqual(pv.isEmpty(), true);
			pv.push_back(99);
			pv.push_back(77);

			Assert::AreEqual(99, (int)pv[0]);
			Assert::AreEqual(77, (int)pv[1]);
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
	};
	TEST_CLASS(VectorIteratorTest) {
		TEST_METHOD(IteratorTestOperators)
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
			pv.push_back(3);
			pv.push_back(1);
			pv.push_back(9);
			pv.push_back(2);
			pv.push_back(30);
			pv.push_back(42);
			pv.push_back(4);

			PersistentVector<int>::iterator a = pv.begin();
			PersistentVector<int>::iterator z = pv.end();

			sort(a, z);

			Assert::IsTrue(a.get() == 1); a++;
			Assert::IsTrue(a.get() == 2); a++;
			Assert::IsTrue(a.get() == 3); a++;
			Assert::IsTrue(a.get() == 4); a++;
			Assert::IsTrue(a.get() == 9); a++;
			Assert::IsTrue(a.get() == 30); a++;
			Assert::IsTrue(a.get() == 42);

		}
		TEST_METHOD(IteratorTestDefault)
		{
			// default iterator criteria
			Assert::IsTrue(is_copy_constructible<VectorIterator<int>>::value);
			Assert::IsTrue(is_copy_assignable<VectorIterator<int>>::value);
			Assert::IsTrue(is_destructible<VectorIterator<int>>::value);
			
			// Can be incremented
			remove("test.bin");
			PersistentVector<int> pv("test.bin");
			Assert::AreEqual(pv.isEmpty(), true);
			pv.push_back(55);
			pv.push_back(77);

			PersistentVector<int>::iterator a = pv.begin();
			a++;
			PersistentVector<int>::iterator b = pv.begin();
			++b;

			Assert::AreEqual(77, a.get());
			Assert::AreEqual(a.get(), b.get());
		}
		TEST_METHOD(IteratorTestForward)
		{
			//Assert::IsTrue(is_default_constructible<VectorIterator<int>>::value);

			remove("test.bin");
			PersistentVector<int> pv("test.bin");
			Assert::AreEqual(pv.isEmpty(), true);
			pv.push_back(55);
			pv.push_back(88);

			PersistentVector<int>::iterator a = pv.begin();
			PersistentVector<int>::iterator b = pv.begin();
			PersistentVector<int>::iterator z = pv.end();

			// Supports equality / inequality comparisons
			Assert::IsTrue(a == b);
			Assert::IsTrue(a != z);
			a++; a++;
			Assert::IsTrue(a == z);

			// Can be dereferenced as an rvalue

			// Can be dereferenced as an lvalue
			// (only for mutable iterator types)
			// Multi-pass: neither dereferencing nor incrementing affects dereferenceability
		}
		TEST_METHOD(IteratorTestBidirectional)
		{
			// bidirectional iterator criteria
			// Can be decremented
		}
		TEST_METHOD(IteratorTestRandomAccess)
		{

			// random access iterator criteria
			// Supports arithmetic operators + and -
			// Supports inequality comparisons (<, >, <= and >=) between iterators
			// Supports compound assignment operations += and -=
			// Supports offset dereference operator ([])
		}

	};
}