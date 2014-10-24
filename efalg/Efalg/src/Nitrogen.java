import java.io.*;
import java.util.*;

public class Nitrogen
{
	
  List<Bottle> incomingBottles;
  //TreeMap<Integer, Double> volumesByDay;
  //double[] volumesByDay;
	
  public Nitrogen() {
	  incomingBottles = new ArrayList<Bottle>();
	  //volumesByDay = new TreeMap<Integer, Double>();
  }
  
  public static void main(String[] args) throws Exception
  {
    //Scanner in = new Scanner(new File("nitrogen.in"));
    BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("nitrogen.in"))));
    PrintWriter out=new PrintWriter("nitrogen.out");
    
    Nitrogen n = new Nitrogen();

    // skip first int
    String[] line1 = in.readLine().split(" ");
    int bottles = Integer.valueOf(line1[0]);
    //n.volumesByDay = new double[in.nextInt()];
    double vaporation = Integer.valueOf(line1[1]);
    
    String[] line = in.readLine().split(" ");
    for (int i = 1; i <= bottles; i++){
    	n.incomingBottles.add(n.new Bottle(i, Integer.valueOf(line[i-1]), vaporation));
    }
    
    /*for (Bottle b : n.incomingBottles) {
		System.out.println("Vap/Day: "+ (b.initVolume/n.vaporation));
		System.out.println("VolByDay(6): "+ b.getVolumeByDay(6, n.vaporation));
	}*/
    
    int bestDay = 1;
    double bestVolume = 0;
    for (int i = 1; i < bottles+vaporation; i++) {
    	double volumeTot = 0;
    	for (Bottle b : n.incomingBottles) {
    		volumeTot += b.getVolumeByDay(i);
    	}
    	//n.volumesByDay[i] = volumeTot;
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
    in.close();
  }
  
  public class Bottle {
	  int initDay;
	  double initVolume;
	  double lossPerDay;
	  
	  public Bottle(int day, double vol, double vap) {
		  initDay = day;
		  initVolume = vol;
		  lossPerDay = (initVolume /vap);
	  }
	  
	  public double getVolumeByDay(int day) {
		  if (day < initDay) return 0;
		  else {
			  // could also be solved using getLifetime
			  double vol = initVolume - lossPerDay * (day-initDay);
			  return vol >= 0 ? vol : 0;
		  }
	  }
	  public int getLifetime(double v) {
		  return (int) (initVolume / lossPerDay);
	  }
  }
   
}