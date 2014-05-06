using System;
using System.Collections.Generic;
using Excel = Microsoft.Office.Interop.Excel;

namespace Fhnw.Ecnf.RoutePlanner.RoutePlannerLib.Export
{
    public class ExcelExchange
    {
        public void WriteToFile(String fileName, City from, City to, List<Link> links)
        {
            var excel = new Excel.Application();
            excel.DisplayAlerts = false;
            excel.Workbooks.Add();
            
            excel.Range["A1", "D1"].Borders.LineStyle = Excel.XlLineStyle.xlContinuous;
            excel.Range["A1", "D1"].Font.Size = 14;
            excel.Range["A1", "D1"].Font.Bold = true;
            excel.Range["A1"].Value = "From";
            excel.Range["B1"].Value = "To";
            excel.Range["C1"].Value = "Distance";
            excel.Range["D1"].Value = "Transport Mode";

            int row = 2;
            foreach (var l in links)
            {
                excel.Range["A" + row.ToString()].Value = l.FromCity.Name;
                excel.Range["B" + row.ToString()].Value = l.ToCity.Name;
                excel.Range["C" + row.ToString()].Value = l.Distance;
                excel.Range["D" + row.ToString()].Value = l.TransportMode.ToString();
                row++;
            }

            excel.Columns.AutoFit();

            Excel._Workbook doc = excel.ActiveWorkbook;
            
            doc.SaveAs(fileName);
            doc.Close();
        }
    }
}
