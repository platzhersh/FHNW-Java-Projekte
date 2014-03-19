using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using Calculators;

namespace DynamicInvocation
{
    class DynamicInvocation
    {
        private Assembly asm;

        public void InvokeMethodByInterface(Type type)
        {
            ICalculator calc = asm.CreateInstance(type.FullName) as ICalculator;
            var retVal = calc.Add(1, 2);
            /*Console.Out.WriteLine(type.GetMethods());
            MethodInfo mi = type.GetMethod("Add");
            var retVal = mi.Invoke(calc, new object[] {1,2});*/
            Console.WriteLine(retVal.ToString());

        }
        public Type GetTypeFromAssembly()
        {
            asm = Assembly.LoadFrom("SimpleCalculator.dll");
            foreach (var t in asm.GetTypes())
            {
                //if (t.IsClass && t.GetInterfaces()))
                Console.Out.WriteLine(t.FullName);
                if (t.GetInterface("Calculators.ICalculator") != null && t.IsClass)
                {
                    return t;
                }
            }
            return null;
        }
    }
}
