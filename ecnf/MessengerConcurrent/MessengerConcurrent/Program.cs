using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Net.Configuration;
using System.Runtime.Remoting.Contexts;
using System.Text;
using System.Threading.Tasks;

namespace MessengerConcurrent
{
    class Program
    {
        static void Main(string[] args)
        {
            BringMultipleMessages();
            BringMultipleMessagesConcurrent();
        }

        static void BringMultipleMessages()
        {
            var messenger1 = new Messenger();
            var messenger2 = new Messenger();
            var messenger3 = new Messenger();
            var messenger4 = new Messenger();

            bool[] answers = new bool[4];
            var sw = new Stopwatch();
            sw.Start();
            answers[0] = messenger1.BringMessage();
            answers[1] = messenger2.BringMessage();
            answers[2] = messenger3.BringMessage();
            answers[3] = messenger4.BringMessage();

            Console.WriteLine("Synctime needed:{0}", sw.Elapsed);

            for (int i = 0; i < answers.Length; i++)
            {
                Console.WriteLine("Messenger {0} succeeded: {1}",i,answers[i]);
            }
        }

        static async void BringMultipleMessagesConcurrent()
        {
            var messenger1 = new Messenger();
            var messenger2 = new Messenger();
            var messenger3 = new Messenger();
            var messenger4 = new Messenger();

            Task<bool>[] answers = new Task<bool>[4];

            var sw = new Stopwatch();
            sw.Start();
            answers[0] = messenger1.BringMessageConcurrent();
            answers[1] = messenger2.BringMessageConcurrent();
            answers[2] = messenger3.BringMessageConcurrent();
            answers[3] = messenger4.BringMessageConcurrent();

            //Console.WriteLine("Synctime needed:{0}", sw.Elapsed);

            /*var results = new bool[answers.Length];

            for (int i = 0; i < answers.Length; i++)
            {
                results[i] = await answers[i];

                Console.WriteLine("Messenger {0} succeeded: {1}", i, results[i]);
            }*/
            Task.WaitAll(answers);

            for (int i = 0; i < answers.Length; i++)
            {
                Console.WriteLine("Messenger {0} succeeded: {1}", i, await answers[i]);
            }
            Console.WriteLine("Synctime needed:{0}", sw.Elapsed);
            
            Console.WriteLine("Messenger {0} succeeded: {2}, Sync Time needed {3}", 0, answers[0], sw.Elapsed);
            Console.WriteLine("Messenger {0} succeeded: {2}, Sync Time needed {3}", 1, answers[1], sw.Elapsed);

        }

    }
}
