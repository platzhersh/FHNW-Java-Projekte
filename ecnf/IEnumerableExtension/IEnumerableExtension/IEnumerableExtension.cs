using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace IEnumerableExtension
{
    static class IEnumerableExtension
    {
        public static IEnumerable<T> Collect<T>(this IEnumerable<T> ienum, Func<T, T> func)
        {
            return ienum.Select(func);
        }
    }
}
