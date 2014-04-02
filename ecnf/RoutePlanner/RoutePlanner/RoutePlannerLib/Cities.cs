using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Fhnw.Ecnf.RoutePlanner.RoutePlannerLib.Util;

namespace Fhnw.Ecnf.RoutePlanner.RoutePlannerLib
{
    public class Cities
    {

        public List<City> CityList { get; set; }
        public int Count { get { return CityList.Count(); }}
        
        public Cities()
        {
            CityList = new List<City>();
        }

        public int ReadCities(string filename)
        {
            using (TextReader reader = new StreamReader(filename))
            {               
                IEnumerable<string[]> citiesAsStrings = reader.GetSplittedLines('\t');
                return (from cs in citiesAsStrings select cs .Any(c =>
                {
                    City city = new City(cs[0], cs[1], int.Parse(cs[2]), double.Parse(cs[3]), double.Parse(cs[4]));
                    CityList.Add(city);
                    return true;
                })).
                Count();
            }
        }

        public List<City> FindNeighbours(City city, double distance)
        {
            return FindNeighbours(city.Location, distance);
        }

        public City FindCity(string cName)
        {
            Predicate<City> findCityPredicate = delegate(City c)
            {
                return c.Name.ToLower().Equals(cName.ToLower());
            };
            return CityList.Find(findCityPredicate);
        }


        public List<City> FindNeighbours(WayPoint location, double distance)
        {
            return CityList.Where(c => c.Location.Distance(location) <= distance).OrderBy(c => c.Location.Distance(location) <= distance).ToList();
        }

        public City this[int index]
        {
            get { return index < CityList.Count ? CityList[index] : null; }
            set { CityList[index] = value; }
        }

    }
}
