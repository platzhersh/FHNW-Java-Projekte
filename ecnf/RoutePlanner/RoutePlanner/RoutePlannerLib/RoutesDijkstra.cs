using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System;

namespace Fhnw.Ecnf.RoutePlanner.RoutePlannerLib
{

    /// <summary>
    /// Manages a routes from a city to another city.
    /// </summary>
    public class RoutesDijkstra : Routes
    {

/// <summary>
        /// Initializes the Routes with the cities.
        /// </summary>
        /// <param name="cities"></param>
        public RoutesDijkstra(Cities cities)
            : base(cities)
        {
        }

       public override List<Link> FindShortestRouteBetween(string fromCity, string toCity,
                                        TransportModes mode, IProgress<string> reportProgress)
        {
           
            NotifyObservers(fromCity, toCity, mode);

            List<City> citiesBetween = FindCitiesBetween(fromCity, toCity);
            if (citiesBetween == null || citiesBetween.Count < 1 ||
               routes == null || routes.Count < 1)
                return null;

            if (reportProgress != null) reportProgress.Report("FindCitiesBetween done");

            City source = citiesBetween[0];
            City target = citiesBetween[citiesBetween.Count - 1];

            Dictionary<City, double> dist;
            Dictionary<City, City> previous;
            List<City> cityNodes = FillListOfNodes(citiesBetween, out dist, out previous);
            dist[source] = 0.0;
            if (reportProgress != null) reportProgress.Report("FillListOfNodes done");

            // the actual algorithm
            previous = SearchShortestPath(mode, cityNodes, dist, previous);
            if (reportProgress != null) reportProgress.Report("SearchShortestPath done");

            // create a list with all cities on the route
            List<City> citiesOnRoute = GetCitiesOnRoute(source, target, previous);
            if (reportProgress != null) reportProgress.Report("GetCitiesOnRoute done");

            // prepare final list if links
            List<Link> itinerary = FindPath(citiesOnRoute, mode);
            if (reportProgress != null) reportProgress.Report("FindPath done");

            return itinerary;
        }

        private static List<City> FillListOfNodes(List<City> cities,
                      out Dictionary<City, double> dist, out Dictionary<City, City> previous)
        {
            List<City> cityNodesList = new List<City>(); // the set of all nodes (cities) in Graph ;
            dist = new Dictionary<City, double>();
            previous = new Dictionary<City, City>();
            foreach (City v in cities)
            {
                dist[v] = double.MaxValue;
                previous[v] = null;
                cityNodesList.Add(v);
            }

            return cityNodesList;
        }

        /// <summary>
        /// Searches the shortest path for cities and the given links
        /// </summary>
        /// <param name="mode">transportation mode</param>
        /// <param name="cityNodesList"></param>
        /// <param name="dist"></param>
        /// <param name="previous"></param>
        /// <returns></returns>
        private Dictionary<City, City> SearchShortestPath(TransportModes mode,
                         List<City> cityNodesList, Dictionary<City, double> dist, Dictionary<City, City> previous)
        {
            while (cityNodesList.Count > 0)
            {
                City u = null;
                double minDist = double.MaxValue;
                // find city u with smallest dist
                foreach (City c in cityNodesList)
                {
                    if (dist[c] < minDist)
                    {
                        u = c;
                        minDist = dist[c];
                    }
                }
                if (u != null)
                {
                    cityNodesList.Remove(u);
                    foreach (City n in FindNeighbours(u, mode))
                    {
                        Link l = FindLink(u, n, mode);
                        double d = dist[u];
                        if (l != null)
                        {
                            d += l.Distance;
                        }
                        else
                        {
                            d += double.MaxValue;
                        }
                        if (dist.ContainsKey(n) && d < dist[n])
                        {
                            dist[n] = d;
                            previous[n] = u;
                        }
                    }
                }
                else
                {
                    break;
                }

            }

            return previous;
        }

        
      /// <summary>
        /// Finds all neighbor cities of a city. 
        /// </summary>
        /// <param name="city">source city</param>
        /// <param name="mode">transportation mode</param>
        /// <returns>list of neighbor cities</returns>
        private List<City> FindNeighbours(City city, TransportModes mode)
        {
            List<City> neighbors = new List<City>();
            foreach (Link r in routes)
            {
                if (mode.Equals(r.TransportMode))
                {
                    if (city.Equals(r.FromCity))
                    {
                        neighbors.Add(r.ToCity);
                    }
                    else if (city.Equals(r.ToCity))
                    {
                        neighbors.Add(r.FromCity);
                    }
                }
            }
            return neighbors;
        }

        private List<City> GetCitiesOnRoute(City source, City target, Dictionary<City, City> previous)
        {
            List<City> citiesOnRoute = new List<City>();
            City cr = target;
            while (previous[cr] != null)
            {
                citiesOnRoute.Add(cr);
                cr = previous[cr];
            }
            citiesOnRoute.Add(source);

            citiesOnRoute.Reverse();
            return citiesOnRoute;
        }

    }
}
