using System.Collections.Generic;
using System.Threading;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Fhnw.Ecnf.RoutePlanner.RoutePlannerLib;
using System.Threading.Tasks;
using System;
using System.Diagnostics;

namespace Fhnw.Ecnf.RoutePlanner.RoutePlannerTest
{
    [TestClass]
    [DeploymentItem("data/citiesTestDataLab4.txt")]
    [DeploymentItem("data/linksTestDataLab4.txt")]
    public class Lab10Test
    {
        List<Link> linksExpected;
        List<Link> linksActual;
      
        private const string CitiesTestFile = "citiesTestDataLab4.txt";
        private const string LinksTestFile = "linksTestDataLab4.txt";

        /// <summary>
        /// Tests if synchronous and asynchronous execution provide the same
        /// results.
        /// </summary>
        [TestMethod]
        public async Task TestFindShortestRouteBetweenAsynch()
        {
            var cities = new Cities();
            cities.ReadCities(CitiesTestFile);

            var routes = new RoutesDijkstra(cities);
            routes.ReadRoutes(LinksTestFile);

            // do synchronous execution
            linksExpected = 
            routes.FindShortestRouteBetween("Basel", "Zürich", TransportModes.Rail);

            // do asynchronous execution
            linksActual =
             await routes.GoFindShortestRouteBetween("Basel", "Zürich", TransportModes.Rail);

            // not test the results
            Assert.IsNotNull(linksActual);
            Assert.AreEqual(linksExpected.Count, linksActual.Count);


            for (int i = 0; i < linksActual.Count; i++)
            {
                Assert.AreEqual(linksExpected[i].FromCity, linksActual[i].FromCity);
                Assert.AreEqual(linksExpected[i].ToCity, linksActual[i].ToCity);
            }
        }

        [TestMethod]
        public async Task TestFindShortestRouteBetweenAsynchProgress()
        {
            var cities = new Cities();
            cities.ReadCities(CitiesTestFile);

            var routes = new RoutesDijkstra(cities);
            routes.ReadRoutes(LinksTestFile);


            var progress = new Progress<string> (ProgressReport);
            // do asynchronous execution
            linksActual =
             await routes.GoFindShortestRouteBetween("Basel", "Zürich", TransportModes.Rail, progress);

            // the following assert has to be made after the routine routine returns 
            // assert, that in minimum 5 progress calls are made
            Assert.IsTrue(progCount >= 5, "less than 5 progress calls");

            // See comment in ProgressReport method
            Assert.IsFalse(doneMissing, String.Format("the progress message <{0}>does not contain <done>", failingMessage));
        }

        static int progCount = 0;

        static bool doneMissing = false;
        static string failingMessage;

        /// <summary>
        /// Note: the delegate method in executed in the async thread
        /// => no unit test Assert-calls can be made there
        /// </summary>
        /// <param name="message"></param>
        private void ProgressReport(string message)
        {
            progCount++;
            string localMessage = message;

            // test if "done" is in message
            // Cannot call the Assert in this delegate, since the delegate is executed in the async thred
            // so just set the status if it contains the "done" keyword and save also the failing message
            // the actual assert is done in the test method after the routine returns (in the unit test thread!!)
            if (!localMessage.Contains("done")) failingMessage = message;
            doneMissing = doneMissing || !localMessage.Contains("done");
            

        }
            
    }
}
