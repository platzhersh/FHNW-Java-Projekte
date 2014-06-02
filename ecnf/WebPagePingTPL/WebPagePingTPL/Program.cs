using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WebPagePingTPL
{
    class Program
    {
        static void Main(string[] args)
        {
            WebPageAnalyzer wpa = new WebPageAnalyzer();
            wpa.CheckWebSites();
            Console.WriteLine("----------------------");
            wpa.CheckWebsitesParallel();
        }
    }
}
