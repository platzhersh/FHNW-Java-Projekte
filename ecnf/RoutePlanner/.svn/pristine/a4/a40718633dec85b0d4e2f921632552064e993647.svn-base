using Fhnw.Ecnf.RoutePlanner.RoutePlannerLib;
using System;
using System.Collections.Generic;
public static class CitiesNewMethod {

/// <summary>
        /// Find all cities between 2 cities 
        /// </summary>
        /// <param name="from">source city</param>
        /// <param name="to">target city</param>
        /// <returns>list of cities</returns>
        public static List<City> FindCitiesBetween(this Cities cities, City from, City to)
        {
            var foundCities = new List<City>();

            if (from == null || to == null)
            {
                return foundCities;
            }

            foundCities.Add(from);

            double minLat = Math.Min(from.Location.Latitude, to.Location.Latitude);
            double maxLat = Math.Max(from.Location.Latitude, to.Location.Latitude);
            double minLon = Math.Min(from.Location.Longitude, to.Location.Longitude);
            double maxLon = Math.Max(from.Location.Longitude, to.Location.Longitude);

            // TODO: renames the name of the "cities" variable to your name of the internal City-List
            foundCities.AddRange(cities.CityList.FindAll(c =>
                c.Location.Latitude > minLat && c.Location.Latitude < maxLat
                        && c.Location.Longitude > minLon && c.Location.Longitude < maxLon));

            foundCities.Add(to);

            return foundCities;
        }
}