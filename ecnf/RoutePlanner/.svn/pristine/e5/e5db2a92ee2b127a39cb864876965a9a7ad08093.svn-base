using System;
using System.Collections.Generic;
using System.Dynamic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Fhnw.Ecnf.RoutePlanner.RoutePlannerLib.Dynamic
{
    public class World : DynamicObject
    {
        private Cities cities;

        public override bool TryInvokeMember(
            InvokeMemberBinder binder, object[] args, out object result)
        {
            if (cities.FindCity(binder.Name) != null) result = cities.FindCity(binder.Name);
            else result = "The city <" + binder.Name + "> does not exist!";
            return true;
        }

        public World(Cities c)
        {
            this.cities = c;
        }
    }
}
