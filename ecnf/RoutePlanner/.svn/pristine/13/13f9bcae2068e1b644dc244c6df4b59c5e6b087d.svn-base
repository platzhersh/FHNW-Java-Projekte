using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using Fhnw.Ecnf.RoutePlanner.RoutePlannerLib.Properties;

namespace Fhnw.Ecnf.RoutePlanner.RoutePlannerLib
{
    public class RoutesFactory
    {
        public static IRoutes Create(Cities cities)
        {
            Console.WriteLine(Settings.Default.RouteAlgorithm);
            return Create(cities, Settings.Default.RouteAlgorithm);
        }
        public static IRoutes Create(Cities cities, String algo)
        {
            Console.WriteLine(algo);
            Assembly asm = Assembly.LoadFrom("RoutePlannerLib.dll");
            Type t = GetTypeFromAssembly(algo ,asm);
            if (t == null) return null;

            return Activator.CreateInstance(t, cities) as IRoutes;
        }

        public static Type GetTypeFromAssembly(String algo, Assembly a)
        {
            foreach (var t in a.GetTypes())
            {
                if (t.FullName.Equals(algo) && t.IsClass)
                {
                    return t;
                }
            }
            return null;
        }
    }
}
