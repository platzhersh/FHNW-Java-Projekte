using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Fhnw.Ecnf.RoutePlanner.RoutePlannerLib;
using Fhnw.Ecnf.RoutePlanner.RoutePlannerLib.Util;
using System.IO;


namespace Fhnw.Ecnf.RoutePlanner.RoutePlannerTest
{
    [TestClass]
    [DeploymentItem("data/citiesTestDataLab4.txt")]
    [DeploymentItem("data/linksTestDataLab4.txt")]
    public class Lab4Test
    {
        private const string CitiesTestFile = "citiesTestDataLab4.txt";
        private const string LinksTestFile = "linksTestDataLab4.txt";

        [TestMethod]
        public void TestWayPointCalculation()
        {
            WayPoint wp1 = new WayPoint("Home", 10.4, 20.8);
            WayPoint wp2 = new WayPoint("Target", 1.2, 2.4);

            WayPoint addWp = wp1 + wp2;
            Assert.AreEqual(wp1.Name, addWp.Name);
            Assert.AreEqual(wp1.Latitude + wp2.Latitude, addWp.Latitude);
            Assert.AreEqual(wp1.Longitude + wp2.Longitude, addWp.Longitude);

            WayPoint minWp = wp1 - wp2;
            Assert.AreEqual(wp1.Name, minWp.Name);
            Assert.AreEqual(wp1.Latitude - wp2.Latitude, minWp.Latitude);
            Assert.AreEqual(wp1.Longitude - wp2.Longitude, minWp.Longitude);
        
        }

        [TestMethod]
        public void TestTask4FindRoutes()
        {
            Cities cities = new Cities();
            cities.ReadCities(CitiesTestFile);
            List<Link> expectedLinks = new List<Link>();
            expectedLinks.Add(new Link(new City("Zürich", "Switzerland", 7000, 1,2), 
                                       new City("Aarau", "Switzerland", 7000, 1,2), 0));
            expectedLinks.Add(new Link(new City("Aarau", "Switzerland", 7000, 1, 2),
                                       new City("Liestal", "Switzerland", 7000, 1, 2), 0));
            expectedLinks.Add(new Link(new City("Liestal", "Switzerland", 7000, 1, 2),
                                       new City("Basel", "Switzerland", 7000, 1, 2), 0));

            Routes routes = new Routes(cities);
            routes.ReadRoutes(LinksTestFile);

            Assert.AreEqual(11, cities.Count);

            // test available cities
            List<Link> links = routes.FindShortestRouteBetween("Zürich", "Basel", TransportModes.Rail); 
            
            Assert.IsNotNull(links);
            Assert.AreEqual(expectedLinks.Count, links.Count);

            for (int i = 0; i < links.Count; i++)
            {
                Assert.AreEqual(expectedLinks[i].FromCity.Name, links[i].FromCity.Name);
                Assert.AreEqual(expectedLinks[i].ToCity.Name, links[i].ToCity.Name);
            }

            links = routes.FindShortestRouteBetween("doesNotExist", "either", TransportModes.Rail);
            Assert.IsNull(links);
        }

        [TestMethod]
        public void TestGetSplittedLinesExtension()
        {
            const string inputstrings = "abc\tdef\tghi\n123\t456\t789";

            TextReader reader = new StringReader(inputstrings);

            List<string[]> splittedStringList = new List<string[]>(reader.GetSplittedLines('\t'));

            // test some of the strings
            Assert.AreEqual("abc", splittedStringList[0][0]);
            Assert.AreEqual("789", splittedStringList[1][2]);
        }
    }
    
}
