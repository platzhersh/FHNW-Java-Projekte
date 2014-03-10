using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PersonAdminLib
{
    static class ListExtension
    {

        public static IEnumerable<String> GetListItemsAsStrings<T>(this List<T> l)
        {
            foreach (T item in l)
            {
                yield return l.ToString();
            }        }

        /*
        public static List<string> GetListItemsAsStrings<T>(this List<T> l)
        {
            List<string> sl = new List<string>();
            foreach (T item in l)
            {
                sl.Add(item.ToString());
            }
            return sl;
        }
         */
    }
}
