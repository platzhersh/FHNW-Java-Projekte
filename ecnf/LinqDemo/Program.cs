using System;

namespace LinqDemo
{
    class Program
    {
        static void Main(string[] args)
        {
            AddressList linqDemo = new AddressList();

            linqDemo.PrintPersonsInCity("Washington");

            Console.WriteLine("\nPress enter to quit:>");
            Console.ReadLine();
        }
    }
}
