using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.NetworkInformation;
using System.Security.Policy;
using System.Text;
using System.Threading.Tasks;

namespace WebPagePingTPL
{
    class WebPageAnalyzer
    {

        private String[] sites = new[]
        {
            "www.fhnw.ch",
            "www.albahari.net",
            "www.platzh1rsch.ch",
            "www.rugbywuerenlos.ch",
            "www.rugbygear.ch"
        };

        public void CheckWebsitesParallel()
        {
            Parallel.ForEach(sites, site => Console.WriteLine("WebPage: {0} : {1}", site, IsWebPageAlive(site)));

        }

        public void CheckWebSites()
        {
            foreach (string site in sites)
            {
                Console.WriteLine("WebPage: {0} : {1}",site, IsWebPageAlive(site));
            }
        }

        private bool IsWebPageAlive(string url)
        {
            PingReply p = new Ping().Send(url);
            return p.Status == IPStatus.Success;
        }

    }
}
