using System;
using System.Collections.Generic;
using System.Linq;

namespace LinqDemo
{
  class AddressList
  {
      readonly private List<Person> persons;
     public AddressList()
     {
        persons = new List<Person> {
        new Person{FirstName = "Joe", LastName = "Adams", Address = "Chandler"},
        new Person{FirstName = "Don", LastName ="Alexander", Address = "Washington"},
        new Person{FirstName = "Dave", LastName = "Ashton", Address = "Seattle"},
        new Person{FirstName = "Bill", LastName = "Pierce", Address = "Sacromento"},
        new Person{FirstName = "Bill", LastName ="Giard", Address = "Camphill"}};
     }

     public void PrintPersonsInCity(string city)
     {

         var personsInCity = (from person in persons
                              where person.Address == city
                              orderby person.FirstName
                              select person);
         var x = personsInCity.AsQueryable();

        Console.WriteLine("\nOriginal person list\n====================");
        foreach (var person in x)
        {
            PrintPerson(person);
        }

        // Add a new person to the persons list
        var p = new Person { FirstName = "Ron", LastName = "Miller", 
                                Address = "Washington" };
        persons.Add(p);


        Console.WriteLine("\nChanged person list\n===================");
        foreach (var person in x)
        {
            PrintPerson(person);
        }

        // Hmm...what type is personsInCity and in which namespace is it?
        // TODO: Print the type and namespace of personsInCity
        Console.WriteLine("\npersonsInCity namespace\n===================");
        Console.WriteLine(x.GetType());

     }

     private static void PrintPerson(Person person)
     {
         Console.WriteLine(person.FirstName + " " + person.LastName +
                               " - " + person.Address);
     }


  }
}
