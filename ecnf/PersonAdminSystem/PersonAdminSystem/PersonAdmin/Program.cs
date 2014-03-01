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

            Person p1 = new Person("Hans", "Müller");
            Person p2 = new Person("Hans", "Müller2");
            Person p3 = new Person("Hans", "Müller3");
            Person p4 = new Person("Hans", "Müller4");

            PersonRegister pr = new PersonRegister();
            pr.ReadPersonsFromFile("Persons.txt");

            Console.WriteLine("Firstname: {0}\nSurname: {1}",
                p1.Firstname, p1.Surname);
            Console.WriteLine("Press any key to quit");

            for (int i = 0; i < pr.Count; i++)
            {
                Console.WriteLine("Firstname: {0}\nSurname: {1}",
                pr[i].Firstname, pr[i].Surname);
            }
            Console.ReadKey();
        }
    }
}
