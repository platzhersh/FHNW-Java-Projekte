using System;
using System.Reflection;
using PersonAdminLib;

namespace PersonAdmin
{
    class Program
    {
        private static void PrintPersons(PersonRegister personRegister)
        {
            foreach (Person p in personRegister.Persons)
            {
                System.Console.WriteLine("{0} {1}", p.Surname, p.Firstname);
            }
        }
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

            Console.WriteLine("--- Print Persons: ---");
            PrintPersons(pr);
            Console.WriteLine("--- Sort by Firstname: ---");
            pr.Sort(pr.sortByFirstName);
            PrintPersons(pr);
            Console.WriteLine("--- Sort by Surname: ---");
            pr.Sort(pr.sortBySurName);
            PrintPersons(pr);
            Console.WriteLine("--- GetPersons: ---");
            Console.WriteLine(pr.GetPersons()[0]);

            Console.ReadKey();
        }
    }
}
