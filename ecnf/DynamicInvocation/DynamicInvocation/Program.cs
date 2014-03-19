using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DynamicInvocation
{
    class Program
    {
        static void Main(string[] args)
        {
            var di = new DynamicInvocation();
            Type t = di.GetTypeFromAssembly();
            di.InvokeMethodByInterface(t);

        }
    }
}
