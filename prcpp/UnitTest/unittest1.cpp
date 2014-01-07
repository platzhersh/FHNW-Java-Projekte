#include "stdafx.h"
#include "CppUnitTest.h"
#include "RandomAccessFile.hpp"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace UnitTest
{		
	TEST_CLASS(UnitTest1)
	{
	public:
		
		TEST_METHOD(WriteReadTestInt)
		{
			RandomAccessFile file("test.bin");

			file.write(100);
			file.write(77);

			Assert::AreEqual("100", file.read(0));
			Assert::AreEqual("77", file.read(sizeof(int)));

		}

	};
}