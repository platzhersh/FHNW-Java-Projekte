using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using AssemblyAnalyzer.Properties;

namespace AssemblyAnalyzer
{
    class AssemblyAnalyzer
    {
        private Assembly a;

        public enum Types { Class, Interface, Any }
        public AssemblyAnalyzer(Assembly asmbly)
        {
            this.a = asmbly;
        }
        public AssemblyAnalyzer(string asmbly)
        {
            this.a = Assembly.LoadFrom(asmbly);
        }

        public void PrintTypes(Types typesToPrint, bool includeMethods)
        {
            var types = a.GetTypes();

            foreach (var type in types)
            {
                switch (typesToPrint)
                {
                    case Types.Any: 
                        Console.Out.WriteLine(type.ToString());
                        break;
                    case Types.Interface:
                        if (type.IsInterface) Console.Out.WriteLine(type.ToString());
                        break;
                    case Types.Class:
                        if (type.IsClass) Console.Out.WriteLine(type.ToString());
                        break;
                }
                if (includeMethods)
                {
                    foreach (var methtype in type.GetMethods())
                    {
                        Console.Out.WriteLine("    "+methtype.ToString());
                    }
                }

            }
        }

        /* TODO */
        public static void GetAssembliesFromDir(string dir)
        {
            var s = Settings.Default.assemblypath;
        }
    }

}
