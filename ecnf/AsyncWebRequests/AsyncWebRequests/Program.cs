using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace AsyncWebRequests
{
    class Program
    {
        static void Main(string[] args)
        {
            var p = new Program();
            p.MakeRequests();
        }

        public async void MakeRequests()
        {
            Task[] tasks = new Task[39];

            for (int i = 0; i < 40; i++)
            {
                tasks[i] = Task.Run(() => MakeRequestAsync());
            }
        }

        public Program()
        {
            
        }

        async void MakeRequest(WebRequest url)
        {
            Console.WriteLine("hm");
            for (int i = 0; i < 40; i++)
            {
                var x = await MakeRequestAsync();
                Console.WriteLine(x);
            }
        }

        async private Task<int> MakeRequestAsync()
        {
            var client = new HttpClient();
            Task<string> getStringTask = client.GetStringAsync("http://msdn.microsoft.com");
            Console.WriteLine("test");
            
            string urlcontents = await getStringTask;
            Console.WriteLine("test");
            return urlcontents.Length;
        }
    }
}
