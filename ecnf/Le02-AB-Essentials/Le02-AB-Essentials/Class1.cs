using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Le02_AB_Essentials
{
    public class Class1
    {
        static void Main(string[] args)
        {
            double e = 2.718281828459045; 
            double d = e; 
            object o1 = d; 
            object o2 = e; 
            Console.WriteLine(d == e); 
            Console.WriteLine(o1 == o2);
        }
    }
}
