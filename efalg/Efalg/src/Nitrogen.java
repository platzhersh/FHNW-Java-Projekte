import java.io.*;
import java.util.*;

public class Nitrogen
{
	
  double vaporation;
  int bottles;
  List<Bottle> incomingBottles;
  TreeMap<Integer, Double> volumesByDay;
	
  public Nitrogen() {
	  incomingBottles = new ArrayList<Bottle>();
	  volumesByDay = new TreeMap<Integer, Double>();
  }
  
  public int getSimulationTime() {
	  return incomingBottles.size()+incomingBottles.get(incomingBottles.size()-1).getLifetime(vaporation);
  }
  
  public static void main(String[] args) throws Exception
  {
    Scanner in = new Scanner(new File("nitrogen.in"));
    PrintWriter out=new PrintWriter("nitrogen.out");
    
    Nitrogen n = new Nitrogen();
    
    n.bottles = in.nextInt();
    n.vaporation = in.nextInt();
    
    int day = 1;
    while (in.hasNextInt()) {
    	n.incomingBottles.add(n.new Bottle(day++, in.nextInt()));
    }
    
    /*for (Bottle b : n.incomingBottles) {
		System.out.println("Vap/Day: "+ (b.initVolume/n.vaporation));
		System.out.println("VolByDay(6): "+ b.getVolumeByDay(6, n.vaporation));
	}*/
    
    int bestDay = 1;
    double bestVolume = 0;
    for (int i = 1; i <= n.getSimulationTime(); i++) {
    	double volumeTot = 0;
    	for (Bottle b : n.incomingBottles) {
    		volumeTot += b.getVolumeByDay(i, n.vaporation);
    	}
    	n.volumesByDay.put(i, volumeTot);
    	//System.out.println("Day "+ i +", Volume: "+volumeTot);
    	
    	if (bestVolume < volumeTot) {
    		bestVolume = volumeTot;
    		bestDay = i;
    	}    	
    }

    //System.out.println("BestDay: "+bestDay);
    //System.out.println("BestVolume: "+bestVolume);
    
    out.println(bestDay);
    
    out.close();
  }
  
  public class Bottle {
	  int initDay;
	  double initVolume;
	  
	  public Bottle(int day, double vol) {
		  initDay = day;
		  initVolume = vol;
	  }
	  
	  public double getVolumeByDay(int day, double v) {
		  if (day < initDay) return 0;
		  else {
			  // could also be solved using getLifetime
			  double vol = initVolume - (initVolume /v) * (day-initDay);
			  return vol >= 0 ? vol : 0;
		  }
	  }
	  public int getLifetime(double v) {
		  return (int) (initVolume / (initVolume/v));
	  }
  }
   
}