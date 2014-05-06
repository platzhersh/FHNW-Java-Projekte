using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GarbageCollector
{
    class LogFileReader
    {
        private TextReader logFile = null;
        public LogFileReader(String fileName)
        { //open file for reading 

            
            try
            {
                if (!File.Exists(fileName)) { File.Create(fileName); } 
                logFile = new StreamReader(fileName);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }
        ~LogFileReader()
        {
            Console.WriteLine("Instance :{0} of class <{1}> is finalizing", this.GetHashCode(), this.GetType());
            // make sure file is closed, when object is garbage collected 
            logFile.Close();
        }
    }
}
