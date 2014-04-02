using System.Linq;
using Fhnw.Ecnf.RoutePlanner.RoutePlannerLib;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Fhnw.Ecnf.RoutePlanner.RoutePlannerTest
{


    /// <summary>
    /// This test class contains all tests for Lab 2
    /// 
    /// REMARK:
    /// Putting all test of a lab is just for convenience; 
    /// usually make, at least, one test file per "class under test" (CUT).
    ///</summary>
    [TestClass]
    [DeploymentItem("data/citiesTestDataLab2.txt")]
    public class Lab2Test
    {
        private const string Name = "Windisch";
        private const double Latitude = 0.564F;
        private const double Longitude = 0.646F;
        private const string CitiesTestFile = "citiesTestDataLab2.txt";

        /// <summary>
        ///A test for WayPoint Constructor        
        /// </summary>
        [TestMethod]
        public void TestWayPointValidConstructor()
        {
            var target = new WayPoint(Name, Latitude, Longitude);

            Assert.AreEqual(Name, target.Name);
            Assert.AreEqual(Latitude, target.Latitude);
            Assert.AreEqual(Longitude, target.Longitude);
        }

        [TestMethod]
        public void TestWayPointToString()
        {
            // test complete way point
            var target = new WayPoint(Name, Latitude, Longitude);
            string wayPointOut = string.Format("WayPoint: {0} {1:N2} / {2:N2}", 
                                Name, Latitude, Longitude);

            Assert.AreEqual(wayPointOut, target.ToString());

            // test no-name case
            var targetNullName = new WayPoint(null, Latitude, Longitude);
            string wayPointOutNullName = string.Format("WayPoint: {0:N2} / {1:N2}",
                                Latitude, Longitude);
            Assert.AreEqual(wayPointOutNullName, targetNullName.ToString());
            
        }

        [TestMethod]
        public void TestWayPointDistanceCalculation()
        {
            var bern = new WayPoint("Bern", 46.95, 7.44);
            var tripolis = new WayPoint("Tripolis", 32.876174, 13.187507);
            const double expected = 1638.74410788167;
            double actual = bern.Distance(tripolis);
            Assert.IsFalse(double.IsNaN(actual));
            Assert.AreEqual(expected, actual, 0.001);
        }

        /// <summary>
        ///A test for City Constructor        
        /// </summary>
        [TestMethod]
        public void TestCityValidConstructor()
        {
            const double latitude = 47.479319847061966;
            const double longitude = 8.212966918945312;
            const int population = 75000;
            const string name = "Bern";
            const string country = "Schweiz";

            var bern = new City(name, country, population, latitude, longitude);

            Assert.AreEqual(name, bern.Name);
            Assert.AreEqual(name, bern.Location.Name); // city name == wayPoint name
            Assert.AreEqual(population, bern.Population);
            Assert.AreEqual(longitude, bern.Location.Longitude, 0.001);
            Assert.AreEqual(latitude, bern.Location.Latitude, 0.001);
        }


        [TestMethod]
        public void TestReadCitiesAndIndexer()
        {
            const int readCitiesExpected = 10;
            var cities = new Cities();

            Assert.AreEqual(readCitiesExpected, cities.ReadCities(CitiesTestFile));

            Assert.AreEqual(readCitiesExpected, cities.Count);

            // cities should be added to list
            Assert.AreEqual(readCitiesExpected, cities.ReadCities(CitiesTestFile));

            // total count should be doubled
            Assert.AreEqual(2*readCitiesExpected, cities.Count);

            //verify first and last city
            Assert.AreEqual("Mumbai", cities[0].Name);
            Assert.AreEqual("Jakarta", cities[9].Name);

            // check for invalid index
            Assert.IsNull(cities[100]);
        }

        [TestMethod]
        public void TestFindNeighbours()
        {
            var cities = new Cities();
            cities.ReadCities(CitiesTestFile);

            var loc = cities[0].Location;

            var neighbors = cities.FindNeighbours(loc, 2000);

            Assert.AreEqual(4, neighbors.Count);

            //verifies if the correct cities were found
            Assert.IsNotNull(neighbors.First(c=>c.Name == "Mumbai"));
            Assert.IsNotNull(neighbors.First(c => c.Name == "Karachi"));
            Assert.IsNotNull(neighbors.First(c => c.Name == "Dhaka"));
            Assert.IsNotNull(neighbors.First(c => c.Name == "Dilli"));
        }

        
        /*
        [TestMethod]
        public void TestFindNeighboursSorted()
        {
            var cities = new Cities();
            cities.ReadCities(CitiesTestFile);

            var loc = cities[0].Location;

            var neighbors = cities.FindNeighbours(loc, 2000);

            Assert.AreEqual(4, neighbors.Count);

            //verify the correct order (sorted  by distance)
            Assert.AreEqual("Mumbai", neighbors[0].Name);
            Assert.AreEqual("Karachi", neighbors[1].Name);
            Assert.IsTrue(loc.Distance(neighbors[0].Location) <= loc.Distance(neighbors[1].Location));
            Assert.AreEqual("Dilli", neighbors[2].Name);
            Assert.IsTrue(loc.Distance(neighbors[1].Location) <= loc.Distance(neighbors[2].Location));
            Assert.AreEqual("Dhaka", neighbors[3].Name);
            Assert.IsTrue(loc.Distance(neighbors[2].Location) <= loc.Distance(neighbors[3].Location));
        }

        */

    }

}
