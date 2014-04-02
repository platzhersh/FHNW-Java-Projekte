using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Fhnw.Ecnf.RoutePlanner.RoutePlannerLib.Util
{
    public static class TextReaderExtensions
    {

        public static IEnumerable<string[]> GetSplittedLines(this TextReader tr, char splitAt)
        {
            String[] lines = tr.ReadToEnd().Split('\n');
            foreach (String s in lines)
            {
                String[] values = s.Split(splitAt);
                yield return (values);
            }
        }
    }
}
