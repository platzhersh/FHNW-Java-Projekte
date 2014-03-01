using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace PersonAdminLib
{
    public class PersonRegister
    {
        private List<Person> personList;

        public PersonRegister()
        {
            personList = new List<Person>();
        }

        public int Count {
            get { return personList.Count; }
        }

        public Person this[int index]
        {
            get { return personList[index]; }
            set { personList[index] = value; }
        }

        public void Add(Person p)
        {
            personList.Add(p);
        }

        public int ReadPersonsFromFile(String filename)
        {
            TextReader tr = new StreamReader(new FileStream(filename,FileMode.Open));
            String[] lines = tr.ReadToEnd().Split(new string[] { Environment.NewLine }, StringSplitOptions.None);
            foreach (String s in lines)
            {
                String fname = "", sname = "";
                fname = s.Split('\t')[0];
                sname = s.Split('\t')[1];
                Add(new Person(fname,sname));
            }
            return lines.Length;
        }
    }
}
