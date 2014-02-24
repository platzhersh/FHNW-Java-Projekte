using System;
using System.Reflection;
using PersonAdminLib;

namespace PersonAdmin
{
    class Program
    {
        static void Main()
        {
            Console.WriteLine("My First C# Program: {0}",
                Assembly.GetExecutingAssembly().GetName().Version);

            Person p = new Person("Hans", "Müller");

            Console.WriteLine("Firstname: {0}\nSurname: {1}",
                p.Firstname, p.Surname);
            Console.WriteLine("Press any key to quit");
            Console.ReadKey();
        }
    }
}
