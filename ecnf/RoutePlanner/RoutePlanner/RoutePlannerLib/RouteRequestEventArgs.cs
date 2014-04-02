namespace Fhnw.Ecnf.RoutePlanner.RoutePlannerLib
{
	public class RouteRequestEventArgs : System.EventArgs
	{
		private City fromCity;

	    public RouteRequestEventArgs(City from, City to, TransportModes m)
	    {
	        fromCity = from;
	        toCity = to;
	        Mode = m;
	    }

		public City FromCity
		{
			get { return fromCity; }
			set { fromCity = value; }
		}

		private City toCity;

		public City ToCity
		{
			get { return toCity; }
			set { toCity = value; }
		}

		private TransportModes tmode;
		public TransportModes Mode
		{
			get { return tmode; }
			set { tmode = value; }
		}
	}
}
