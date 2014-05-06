using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GarbageCollector
{
    class Program
    {
        static void Main(string[] args)
        {
            var lfr = new LogFileReader("blubb.txt");
            lfr = null;
            
            var lfr2 = new LogFileReader("blubb.txt");
            var lfr3 = new LogFileReader("blubb.txt");
            GC.Collect();
            lfr2 = null;
            Console.ReadLine();
            GC.Collect();
            Console.ReadLine();
        }
    }
}
