using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace WindowsAsync
{
    class Program : Application
    {
        [STAThread]
        static void Main(string[] args)
        {
            Program app = new Program();
            
            app.Init();
            app.Run();
        }

        void Init()
        {
             TestUi window = new TestUi();
            window.Show();
        }


    }
}
