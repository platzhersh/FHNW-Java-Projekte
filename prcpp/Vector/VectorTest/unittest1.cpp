#include "stdafx.h"
#include "CppUnitTest.h"
#include "Expression.hpp"
#include "Array.hpp"
#include "Scalar.hpp"
#include "Operations.hpp"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace VectorTest
{		
	TEST_CLASS(Vector)
	{
	public:
		
		TEST_METHOD(Construction)
		{
			double a_data[] = { 2, 3, 5, 9 };
			Array<double> A(a_data, 4);

			Assert::IsTrue(A.getLength() == 4);

			Scalar<double> S(2);

			Assert::IsTrue(S.m_value == 2);
		}
		TEST_METHOD(Addition)
		{
			double a_data[] = { 2, 3, 5, 9 };
			double b_data[] = { 1, 0, 0, 0 };
			double c_data[] = { 3, 0, 2, 5 };
			double d_data[4];
			double e_data[] = { 6, 3, 7, 14 };

			Array<double> A(a_data, 4);
			Array<double> B(b_data, 4);
			Array<double> C(c_data, 4);
			Array<double> D(d_data, 4);
			Array<double> E(e_data, 4);
			
			D = A + B + C;
			
			Assert::IsTrue(D == E);
		}
		TEST_METHOD(Subtraction)
		{
			double a_data[] = { 2, 3, 5, 9 };
			double b_data[] = { 1, 0, 0, 0 };
			double c_data[] = { 3, 0, 2, 5 };
			double d_data[4];
			double f_data[] = { -2, 3, 3, 4 };

			Array<double> A(a_data, 4);
			Array<double> B(b_data, 4);
			Array<double> C(c_data, 4);
			Array<double> D(d_data, 4);
			Array<double> F(f_data, 4);

			D = A - B - C;

			Assert::IsTrue(D == F);
		}
		TEST_METHOD(MultiplicationVectorVector) 
		{
			double a_data[] = { 2, 3, 5, 9 };
			double b_data[] = { 1, 0, 0, 0 };
			double d = 2+0+0+0;


			Array<double> A(a_data, 4);
			Array<double> B(b_data, 4);
			Scalar<double> D;

			D = A * B;

			Assert::IsTrue(D.m_value == d);

		}
		TEST_METHOD(MultiplicationVectorScalar) 
		{
			double a = 2;
			double b_data[] = { 2, 3, 5, 9 };
			double c_data[] = { 4, 6, 10, 18 };
			double d_data[4];


			Scalar<double> A(a);
			Array<double> B(b_data, 4);
			Array<double> C(c_data, 4);
			Array<double> D(d_data, 4);

			D =  B * A;

			Assert::IsTrue(D == C);
		}
		TEST_METHOD(MultiplicationScalarVector) 
		{
			double a = 2;
			double b_data[] = { 2, 3, 5, 9 };
			double c_data[] = { 4, 6, 10, 18 };
			double d_data[4];


			Scalar<double> A(a);
			Array<double> B(b_data, 4);
			Array<double> C(c_data, 4);
			Array<double> D(d_data, 4);

			D = A * B;

			Assert::IsTrue(D == C);
		}

		TEST_METHOD(MultiplicationScalarScalar) 
		{
			double a = 2;
			double b = 3;

			Scalar<double> A(a);
			Scalar<double> B(b);

			Assert::IsTrue((A * B)[1] == 6);
		}
		TEST_METHOD(StammTest) {
			double k = 5;
			double l = 3.14;
			double a_data[] = { 2, 3, 5, 9 };
			double b_data[] = { 1, 0, 0, 1 };
			double c_data[] = { 3, 0, 2, 5 };
			double d_data[4];
			double e_data[] = {20.42,15,31.28,61.7};
			Array<double> A(a_data, 4);
			Array<double> B(b_data, 4);
			Array<double> C(c_data, 4);
			Array<double> D(d_data, 4);
			Array<double> E(d_data, 4);
			D = k*A + B + l*C;

			Assert::IsTrue(D == E);
		}
	};
}