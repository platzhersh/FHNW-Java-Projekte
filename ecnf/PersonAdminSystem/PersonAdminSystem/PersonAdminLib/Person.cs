namespace PersonAdminLib
{
    /// <summary>
    /// 
    /// </summary>
    public class Person
    {
        public string Firstname { get; set; }

        public string Surname { get; set; }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="firstname"></param>
        /// <param name="surname"></param>
        public Person(string firstname, string surname)
        {
            Firstname = firstname;
            Surname = surname;
        }

        
    }
}