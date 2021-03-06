﻿using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Fhnw.Ecnf.RoutePlanner.RoutePlannerLib;

namespace Fhnw.Ecnf.RoutePlanner.RoutePlannerTest
{
    [TestClass]
    [DeploymentItem("data/citiesTestDataLab3.txt")]

    public class Lab6Test
    {
        [TestMethod]
        public void TestFindCitiesByTransportMode()
        {
            Cities cities = new Cities();
            cities.ReadCities(@"citiesTestDataLab3.txt");
            var routes = new RoutesDijkstra(cities);
            int count = routes.ReadRoutes(@"linksTestDataLab3.txt");
            Assert.AreEqual(7, count);


            City[] citiesByMode = routes.FindCities(TransportModes.Rail);

            Assert.AreEqual(11, citiesByMode.Count());

            // there must be no cities
            City[] emptyCitiesByMode = routes.FindCities(TransportModes.Bus);
            Assert.AreEqual(0, emptyCitiesByMode.Count());

        }

        /// <summary>
        /// a very simple test if linqs and lambdas have been implemented
        /// </summary>
        [TestMethod]
        public void TestForLambdaInCitiesReadCities()
        {
            List<string> codeLines = GetMethodCodeFromFile("RoutePlannerLib/Cities.cs", "ReadCities");

            // check if linq with query syntax has been implemented 
            var linq = (from line in codeLines where (line.Contains("from") && line.Contains("in")) select line).ToList();

            // number of query linq queries must be >= 1
           Assert.IsTrue(linq.Count >= 1);
        }

        [TestMethod]
        public void TestForLambdaInCitiesFindNeighbours()
        {
            List<string> codeLines = GetMethodCodeFromFile("RoutePlannerLib/Cities.cs", "FindNeighbours");


            var lambda = (from line in codeLines where line.Contains(".Where(") && line.Contains("=>") select line).ToList();
            // number of method linq queries with lambda must be >= 1
            Assert.IsTrue(lambda.Count >= 1);
        }

        [TestMethod]
        public void TestForLambdaInRoutesFindCities()
        {
            List<string> codeLines = GetMethodCodeFromFile("RoutePlannerLib/Routes.cs", "FindCities");

            var linq = (from line in codeLines where (line.Contains("from") && line.Contains("in")) select line).ToList();

            // number of query linq queries must be >= 1
            Assert.IsTrue(linq.Count >= 1);

        }

        #region Helper functions to read source code

        /// <summary>
        /// Get the source code of the specified method from the also specified file.
        /// Current restriction: gets only the 20 first lines of the method.
        /// </summary>
        /// <param name="filename">source code file name</param>
        /// <param name="functionName">method name to find implementation</param>
        /// <returns>source code of method as a string list</returns>
        private List<string> GetMethodCodeFromFile(string filename, string functionName)
        {
            List<string> codeLines = ReadSourceFile(filename).ToList();

            // add opening parenthes, to ensure the correct function is found
            return GetMethodCode(codeLines, functionName + "(");

        }

        private List<string> ReadSourceFile(string filename)
        {
            TextReader sourceFile;
            try
            {
                // used for local test execution
                sourceFile = new StreamReader("../../" + filename);
            }
            catch (Exception)
            {
                // used for ci server test exceution (different output path)
                sourceFile = new StreamReader("../../../" + filename);
            }

            List<string> codeLines = new List<string>();

            string codeLine;
            while ((codeLine = sourceFile.ReadLine()) != null)
            {
                codeLines.Add(codeLine);
            }

            return codeLines;
        }

        private List<string> GetMethodCode(List<string> codeLines, string functionName)
        {
            const int lineNumbersToRead = 20;

            // find line number where the method starts, check if, the method name does not occur in comment line
            int findNeighbourLineNumber = codeLines.FindIndex(line => line.Contains(functionName) && !line.Contains("//"));



            // just take the 20 following lines of the beginning line of the method
            // TODO: could make it more accurate and find end of method
            return codeLines.Skip(findNeighbourLineNumber - 1).Take(lineNumbersToRead).ToList();
        }

        #endregion

    }
}