using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AssemblyAnalyzer
{
    class Program
    {
        static void Main(string[] args)
        {
            var a = new AssemblyAnalyzer("PersonAdminLib.dll");
            a.PrintTypes(AssemblyAnalyzer.Types.Any, true);
        }
    }
}
