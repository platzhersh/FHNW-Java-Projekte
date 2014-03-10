using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace OperatorOverloading
{
    class OperatorOverloadingApp
    {
        static void Main(string[] args)
        {
            Complex c1 = new Complex(1, 3);
            Complex c2 = new Complex(1.3, 3.5);
            Complex c3 = c1;

            Console.WriteLine("c1 + c2: {0}", (c1 + c2));
            Console.WriteLine("c3 += c2: {0}", (c3 += c2));
            Console.WriteLine("c1 - c2: {0}", (c1 - c2));

            Console.WriteLine(c1.Equals(c2));
            Complex c4 = new Complex(1, 3);
            Console.WriteLine(c1.Equals(c4));
            Console.WriteLine(c1 == c4);

            Complex c = new Complex(1, 1);
            Console.WriteLine("C++: {0} (should be: 1,1)", c++);
            Console.WriteLine("C: {0} (should be: 2,2)", c);
            Console.WriteLine("++C: {0} (should be: 3,3)", ++c);

            Console.WriteLine("Press any Key to continue");
            Console.ReadKey();
        }
    }
}
