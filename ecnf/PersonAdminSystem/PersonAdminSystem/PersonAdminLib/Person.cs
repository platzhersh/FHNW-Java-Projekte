namespace PersonAdminLib
{
    /// <summary>
    /// 
    /// </summary>
    public class Person
    {
        private string firstname;
        public string Firstname { get { return firstname; } set { firstname = value;  } }
        
        public string surname { get; set; }

        public string Surname { get { return surname; } set { surname = value; } }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="firstname"></param>
        /// <param name="surname"></param>
        public Person(string firstname, string surname)
        {
            this.firstname = firstname;
            this.surname = surname;
        }

    }
}

namespace MyNamespace
{


}
