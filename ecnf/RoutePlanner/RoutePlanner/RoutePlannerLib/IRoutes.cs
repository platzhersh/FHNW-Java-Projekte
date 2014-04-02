using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Fhnw.Ecnf.RoutePlanner.RoutePlannerLib
{
    public interface IRoutes { 
        /// <summary> 
        /// Reads a list of links from the given file. 
        /// Reads only links where the cities exist. 
        /// </summary>
        /// <param name="filename">name of links file</param> 
        /// <returns>number of read route</returns> 
        int ReadRoutes(string filename); 

        /// <summary> 
        /// Calculates and returns the shortest route between the passed cities. 
        /// </summary> 
        /// <param name="fromCity"></param>name of from city 
        /// <param name="toCity"></param>name of to city 
        /// param name="mode"></param>transportation mode 
        /// <returns></returns> 
        List<Link> FindShortestRouteBetween(string fromCity, 
                                        string toCity, 
                                        TransportModes mode); 
    }
}
