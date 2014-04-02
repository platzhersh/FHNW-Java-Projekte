using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IEnumerableExtension
{
    class Program
    {
        static void Main(string[] args)
        {
            int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

            var numbers2 = numbers.Collect(p => p*2);

            foreach (int i in numbers2) { 
                Console.WriteLine(i);
            }
        }
    }
}
