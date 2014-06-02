using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ParallelPrimaries
{
    class Program
    {
        static void Main(string[] args)
        {

            DateTime t1 = DateTime.Now;
            CalculatePrimeSequential(3, 30);
            DateTime t2 = DateTime.Now;
            Console.WriteLine(t2.Subtract(t1).TotalMilliseconds+"ms");
            
            DateTime t3 = DateTime.Now;
            CalculatePrimeSequential(3, 1000);
            DateTime t4 = DateTime.Now;
            Console.WriteLine(t4.Subtract(t3).TotalMilliseconds + "ms");

            DateTime t5 = DateTime.Now;
            CalculatePrimeSequential(3, 1000000);
            DateTime t6 = DateTime.Now;
            Console.WriteLine(t6.Subtract(t5).TotalMilliseconds + "ms");

        }

        static int[] CalculatePrimeSequential(int start, int end)
        {
            var numbers = Enumerable.Range(start, end);
            var parallelQuery = numbers.Where(n => Enumerable.Range(2, (int)Math.Sqrt(n)).All(i => n % i != 0));
            return parallelQuery.ToArray();
        }

        static int[] CalculatePrimeParallel(int start, int end)
        {
            var numbers = Enumerable.Range(start, end);
            var parallelQuery = numbers.Where(n => Enumerable.Range(2, (int)Math.Sqrt(n)).All(i => n % i != 0)); 
            return parallelQuery.ToArray();
        }
    }
}
