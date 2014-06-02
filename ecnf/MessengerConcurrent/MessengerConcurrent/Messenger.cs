using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace MessengerConcurrent
{
    class Messenger
    {

        public bool BringMessage()
        {
            // bringing the message takes a while ..
            Thread.Sleep(2000);
            return true;
        }

        async public Task<bool> BringMessageConcurrent()
        {
            // bringing the message takes a while ..
            await Task.Delay(2000);
            Console.WriteLine("blubb");
            //Thread.Sleep(2000);
            return true;
        }

        

    }
}
