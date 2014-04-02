using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Fhnw.Ecnf.RoutePlanner.RoutePlannerLib
{
    /// <summary>
    /// Monitors Route-calculation requests on Routes
    /// </summary>
    public class RouteRequestWatcher
    {
        private Dictionary<string, int> requestCountList = new Dictionary<string, int>();

        public void LogRouteRequests(object sender, RouteRequestEventArgs args)
        {
            if (!requestCountList.ContainsKey(args.FromCity.Name))
                requestCountList.Add(args.FromCity.Name, 0);

            if (requestCountList.ContainsKey(args.ToCity.Name))
                requestCountList[args.ToCity.Name]++;
            else requestCountList.Add(args.ToCity.Name, 1);

            Status();
        }

        private void Status()
        {
            Console.WriteLine("Current Request State");
            Console.WriteLine("----------------------------");
            foreach (KeyValuePair<string, int> city in requestCountList)
            {
                Console.WriteLine("ToCity: {0} has been requested {1} times.", city.Key, city.Value);
            }
        }

        public int GetCityRequests(string cityName)
        {
            int counter = 0;
            requestCountList.TryGetValue(cityName, out counter);
            return counter;
        }
    }
}
