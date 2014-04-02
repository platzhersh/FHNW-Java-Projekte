using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Remoting.Messaging;
using System.Text;
using System.Threading.Tasks;

namespace Fhnw.Ecnf.RoutePlanner.RoutePlannerLib
{
    public class WayPoint
    {
        public string Name { get; set; }
        public double Longitude { get; set; }
        public double Latitude { get; set; }

        public WayPoint(string name, double latitude, double longitude)
        {
            this.Name = name;
            this.Latitude = latitude;
            this.Longitude = longitude;
        }

        public override string ToString()
        {
            return "WayPoint: " + (Name == null ? "" : Name + " ") + Math.Round(Latitude, 2) + " / " + Math.Round(Longitude, 2);
        }

        private static double ConvertToRadians(double angle)
        {
            return (Math.PI / 180) * angle;
        }

        public Double Distance(WayPoint target)
        {
            double R = 6371;    // earth radius
            double slat = ConvertToRadians(this.Latitude), slong = ConvertToRadians(this.Longitude);
            double tlat = ConvertToRadians(target.Latitude), tlong = ConvertToRadians(target.Longitude);
            double d = R*
                       Math.Acos(Math.Sin(slat)*Math.Sin(tlat) +
                                 Math.Cos(slat) * Math.Cos(tlat) *
                                 Math.Cos(slong - tlong));

            return d;
        }

    // Operatoren
    public static WayPoint operator +(WayPoint wpl, WayPoint wpr)
    {
        return new WayPoint(wpl.Name,wpl.Latitude+wpr.Latitude,wpl.Longitude+wpr.Longitude);
    }
    public static WayPoint operator -(WayPoint wpl, WayPoint wpr)
    {
        return new WayPoint(wpl.Name, wpl.Latitude - wpr.Latitude, wpl.Longitude - wpr.Longitude);
    }

    }

}
