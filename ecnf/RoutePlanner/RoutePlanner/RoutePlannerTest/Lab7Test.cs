using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Fhnw.Ecnf.RoutePlanner.RoutePlannerLib;
using Fhnw.Ecnf.RoutePlanner.RoutePlannerLib.Dynamic;
using Fhnw.Ecnf.RoutePlanner.RoutePlannerLib.Export;
using System.IO;
using Microsoft.Office.Interop.Excel;


namespace Fhnw.Ecnf.RoutePlanner.RoutePlannerTest
{
    [TestClass]
    [DeploymentItem("data/citiesTestDataLab2.txt")]
    public class Lab7Test
    {
        [Ignore]
        [TestMethod]
        public void TestExcelExport()
        {
            string excelFileName = Directory.GetCurrentDirectory() + @"\ExportTest.xlsx";

            City bern = new City("Bern", "Switzerland", 5000, 46.95, 7.44);
            City zuerich = new City("Zürich", "Switzerland", 100000, 32.876174, 13.187507);
            City aarau = new City("Aarau", "Switzerland", 10000, 35.876174, 12.187507);
            Link link1 = new Link(bern, aarau, 15, TransportModes.Ship);
            Link link2 = new Link(aarau, zuerich, 20, TransportModes.Ship);
            List<Link> links = new List<Link>();
            links.Add(link1);
            links.Add(link2);

            // TODO: Fix starting Excel on sever (not robust)
            ExcelExchange excel = new ExcelExchange();

            Console.WriteLine("Export Path is: {0}", excelFileName);

            excel.WriteToFile(excelFileName, bern, zuerich, links);

            // first verify that file exists
            Assert.IsTrue(File.Exists(excelFileName));

            // now verify the content of the file
            // TODO: Fix reading file on sever
            VerifyExcelContent(excelFileName);

        }


        private void VerifyExcelContent(string excelFileName)
        {
            var sheet = OpenWorkbookAndSelectSheet(excelFileName);

            VerifyHeader(sheet);
        }

        private static Worksheet OpenWorkbookAndSelectSheet(string excelFileName)
        {
            var excel = new Application();
            excel.Visible = false;
            excel.ScreenUpdating = false;
            excel.DisplayAlerts = false;

            // take the first and only one
            Workbook workbook;
            try
            {
                workbook = excel.Workbooks.Open(excelFileName, Type.Missing, true);

            }
            catch (Exception e)
            {
                throw new IOException("could not open excel file", e);
            }

            var sheet = (Worksheet)workbook.Sheets[1];
            return sheet;
        }

        private static void VerifyHeader(Worksheet sheet)
        {
            Assert.AreEqual("From", ((Range) sheet.Cells[1, 1]).Value.ToString());
            Assert.AreEqual("To", ((Range) sheet.Cells[1, 2]).Value.ToString());
            Assert.AreEqual("Distance", ((Range) sheet.Cells[1, 3]).Value.ToString());
            Assert.AreEqual("Transport Mode", ((Range) sheet.Cells[1, 4]).Value.ToString());
        }

        [TestMethod]
        public void TestDynamicWorld()
        {
            Cities cities = new Cities();

            cities.ReadCities("citiesTestDataLab2.txt");

            dynamic world = new World(cities);

            dynamic karachi = world.Karachi();
            Assert.AreEqual("Karachi", karachi.Name);

            string notFound = world.Entenhausen();
            Assert.AreEqual("The city <Entenhausen> does not exist!", notFound);

        }
    }
}
