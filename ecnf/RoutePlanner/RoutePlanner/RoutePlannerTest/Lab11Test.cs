using System.Diagnostics;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Fhnw.Ecnf.RoutePlanner.RoutePlannerLib;

namespace Fhnw.Ecnf.RoutePlanner.RoutePlannerTest
{
    [TestClass]
    [DeploymentItem("data/citiesTestDataLab2.txt")]
    [DeploymentItem("data/citiesTestDataLab11.txt")]
    [DeploymentItem("data/linksTestDataLab11.txt")]
    public class Lab11Test
    {
        private const string CitiesTestFile = "citiesTestDataLab2.txt";

        [TestMethod]
        public void TestCorrectIndexingOfCities()
        {
            const int readCitiesExpected = 10;
            var cities = new Cities();

            Assert.AreEqual(readCitiesExpected, cities.ReadCities(CitiesTestFile)); 

            City from = cities.FindCity("Mumbai");
            City to = cities.FindCity("Istanbul");
            List<City> foundCities = cities.FindCitiesBetween(from, to);

            // verify that Index property is initialized
            int i = 0;
            foreach (var city in foundCities)
            {
                Assert.AreEqual(i, city.Index);
                i++;
            }
    
        }

        [TestMethod]
        public void TestTask1FindRoutes()
        {
            Cities cities = new Cities();
            cities.ReadCities(@"citiesTestDataLab4.txt");
            Assert.AreEqual(11, cities.Count);

            List<Link> expectedLinks = new List<Link>();
            expectedLinks.Add(new Link(new City("Zürich", "Switzerland", 7000, 1, 2),
                                       new City("Aarau", "Switzerland", 7000, 1, 2), 0));
            expectedLinks.Add(new Link(new City("Aarau", "Switzerland", 7000, 1, 2),
                                       new City("Liestal", "Switzerland", 7000, 1, 2), 0));
            expectedLinks.Add(new Link(new City("Liestal", "Switzerland", 7000, 1, 2),
                                       new City("Basel", "Switzerland", 7000, 1, 2), 0));

            Routes routes = new RoutesFloydWarshall(cities);
            int count = routes.ReadRoutes(@"linksTestDataLab4.txt");
            Assert.AreEqual(10, count);
    

            // test available cities
            routes.ExecuteParallel = true;
            List<Link> links = routes.FindShortestRouteBetween("Zürich", "Basel", TransportModes.Rail);

            Assert.IsNotNull(links);
            Assert.AreEqual(expectedLinks.Count, links.Count);

            for (int i = 0; i < links.Count; i++)
            {
                Assert.AreEqual(expectedLinks[i].FromCity.Name, links[i].FromCity.Name);
                Assert.AreEqual(expectedLinks[i].ToCity.Name, links[i].ToCity.Name);
            }


            //links = routes.FindShortestRouteBetween("doesNotExist", "either", TransportModes.Rail);
            //Assert.IsNull(links);

        }


        [TestMethod]
        public void TestTask2FindRoutes()
        {
            Cities cities = new Cities();
            cities.ReadCities(@"citiesTestDataLab11.txt");

            Assert.AreEqual(6372, cities.Count);

            List<Link> expectedLinks = new List<Link>();

            Routes routes = new RoutesFloydWarshall(cities);
            int count = routes.ReadRoutes(@"linksTestDataLab11.txt");
            Assert.AreEqual(112, count);

            // test available cities
            routes.ExecuteParallel = true;
            List<Link> links = routes.FindShortestRouteBetween("Lyon", "Berlin", TransportModes.Rail);
            Assert.AreEqual(13, links.Count);
            routes.ExecuteParallel = false;
            List<Link> links2 = routes.FindShortestRouteBetween("Lyon", "Berlin", TransportModes.Rail);

            Assert.IsNotNull(links);
            Assert.AreEqual(13, links.Count);
            Assert.IsNotNull(links2);
            Assert.AreEqual(13, links2.Count);
        }

        [TestMethod]
        public void TestTask3CompareAlgorithms()
        {
            Cities cities = new Cities();

            cities.ReadCities(@"citiesTestDataLab11.txt");
            Assert.AreEqual(6372, cities.Count);

            Routes routes = new RoutesDijkstra(cities);
            long dijkstraTime = FindRoutes(routes);

            routes = new RoutesFloydWarshall(cities);
            routes.ExecuteParallel = false;
            long floydWarshallTime = FindRoutes(routes);

            // the sequentiel floydWarshal should be slower
            Assert.IsTrue(floydWarshallTime > dijkstraTime, "FloydWarshal should be slower");

        }


        [TestMethod]
        public void TestTask5CompareParallelSequential()
        {
            Cities cities = new Cities();

            cities.ReadCities(@"citiesTestDataLab11.txt");
            Assert.AreEqual(6372, cities.Count);

            Routes routes = new RoutesFloydWarshall(cities);
            routes.ExecuteParallel = true;
            long floydWarshallParallelTime = FindRoutes(routes);

            routes = new RoutesFloydWarshall(cities);
            routes.ExecuteParallel = false;
            long floydWarshallTime = FindRoutes(routes);

            // the sequentiel floydWarshal should be slower
            Assert.IsTrue(floydWarshallTime > floydWarshallParallelTime, "FloydWarshal parallel should be faster than sequential");


        }

        /// <summary>
        /// Helper functions which calls find algorithm and returns needed time in 
        /// ticks
        /// </summary>
        /// <param name="routes">routes instance</param>
        /// <returns>needed time in ticks</returns>
        private long FindRoutes(Routes routes)
        {
            int count = routes.ReadRoutes(@"linksTestDataLab11.txt");

            // test available cities
            Stopwatch timer = new Stopwatch();

            timer.Start();
            List<Link> links = routes.FindShortestRouteBetween("Lyon", "Berlin", TransportModes.Rail);
            return timer.ElapsedTicks;
        }
    }
}
