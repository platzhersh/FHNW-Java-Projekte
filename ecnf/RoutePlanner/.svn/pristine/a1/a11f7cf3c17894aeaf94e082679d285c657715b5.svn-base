using System;
using System.Collections.Generic;
using System.Linq;

namespace Fhnw.Ecnf.RoutePlanner.RoutePlannerLib
{
    /// <summary>
    /// Searches routes with FloydWarshall algorithm
    /// <see cref="http://algowiki.net/wiki/index.php?title=Floyd-Warshall%27s_algorithm"/>
    /// </summary>

    public class RoutesFloydWarshall: Routes
    {
        private double [,]D;
        private City [,]P;
        
        public RoutesFloydWarshall(Cities cities)
            : base(cities)
        {
           
        }


        public override List<Link> FindShortestRouteBetween(string fromCity, string toCity,
                                        TransportModes mode, IProgress<string> reportProgress = null)
        {
            List<City> cities = FindCitiesBetween(fromCity, toCity);
            if (cities == null || cities.Count < 1)
            {
                return null;
            }
            List<Link> links = FindAllLinks(cities, mode);
            if (links == null || links.Count < 1)
                return null;
			
            Setup(cities, links);  

            City source = FindCity( fromCity, cities );
            City target = FindCity(toCity, cities);
            if (D[source.Index, target.Index] == Double.MaxValue)
            {
                return new List<Link>(); // no path between source and target
            }
            List<City> path = GetIntermediatePath(source, target);

            // must construct route from path
            List<Link> route = new List<Link>();
            route.Add( new Link(source, path.ElementAt(0), D[source.Index, path.ElementAt(0).Index] ) );
            for (int i = 0; i < path.Count - 1; i++)
            {
                City from = path.ElementAt(i);
                City to = path.ElementAt(i + 1);
                route.Add( new Link(from, to, D[from.Index, to.Index]) );
            }
            route.Add( new Link(path.ElementAt(path.Count - 1), target, 
                                D[path.ElementAt(path.Count - 1).Index, target.Index]) );
            return route;

        }

        private List<Link> FindAllLinks(List<City> cities, TransportModes mode)
        {
            List<Link> links= new List<Link>();
            foreach (Link r in routes)
            {
                if( r.IsIncludedIn( cities ) )
                {
                    links.Add(r);
                }
            }
            return links;
        }

        private City FindCity(string cityName, List<City> cities)
        {
            foreach (City c in cities)
            {
                if (c.Name == cityName)
                {
                    return c;
                }
            }
            return null;
        }

        private List<City> GetIntermediatePath(City source, City target)
        {
           if(P[source.Index, target.Index] == null)  {
	            return new List<City>();
	        }
	        List<City> path = new List<City>();
	        path.AddRange( GetIntermediatePath(source, P[source.Index, target.Index]));
	        path.Add( P[source.Index, target.Index] );
	        path.AddRange( GetIntermediatePath(P[source.Index, target.Index], target));
	        return path;
        }

      private void Setup(List<City> cities, List<Link> links) {
	        
	        D = InitializeWeight(cities, links);
	        P = new City[cities.Count, cities.Count];

			for( int k=0; k<cities.Count; k++ )
			{
	            for( int i=0; i<cities.Count; i++ )  {
	                for( int j=0; j<cities.Count; j++ )  {
	                    if( D[i,k] != Double.MaxValue 
	                     && D[k,j] != Double.MaxValue 
	                     && D[i,k]+D[k,j] < D[i,j] )
	                    {
	                        D[i,j] = D[i,k]+D[k,j];
	                        P[i,j] = cities[k];
	                    }
	                }
	            }
			}

	    }

        private double[,] InitializeWeight ( List<City> cities, List<Link> links )  {

	        double[,] weight = new double[cities.Count, cities.Count];
            // initialize with MaxValue:
            for (int i = 0; i < cities.Count; i++)
            {
                for (int j = 0; j < cities.Count; j++)
                {
                    weight[i, j] = Double.MaxValue;
                }
            }
        
	        foreach( Link e in links )  {
                weight[e.FromCity.Index,e.ToCity.Index] = e.Distance;
                weight[e.ToCity.Index,e.FromCity.Index] = e.Distance;
	        }
	        return weight;
	    }

    }
}
