import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Bus
{
	
	boolean[] knownSecrets;
	  int id;
	  int nofStops;
	  List<Integer> stops;
	  
	  public Bus(int i, int nofBusses, int numberofStops) {
		  id = i;
		  nofStops = numberofStops;
		  stops = new ArrayList<Integer>();
		  
		  // initialise Secrets Array with false
		  knownSecrets = new boolean[nofBusses];
		  knownSecrets[id] = true;
	  }
	  public void addStop(int s){
		  stops.add(s);
	  }
	  public int getCurrentStop(int min) {
		  int index = min == 0 ? 0 : min % nofStops;
		  return stops.get(index);
	  }
	  
	  public String toString() {
		  return "Bus " + id + " " + stops.toString();
	  }
	  
	  public boolean knowsAll() {
		  boolean ret = true;
		  int i = 0;
		  while(ret == true && i < knownSecrets.length) {
			  ret = ret && knownSecrets[i];
			  i++;
		  }
		  return ret;
	  }
	  
	  
  public static void main(String[] args) throws Exception
  {
    Scanner in=new Scanner(new File("bus.in"));
    PrintWriter out=new PrintWriter("bus.out");
    List<Bus> busses = new ArrayList<Bus>();
    
    //your code here
    int nofBusses = in.nextInt();
    
    // parse busses
    int i = 0;
//    while(in.hasNext()) {
//    	int nofStops = in.nextInt();
//    	Bus b = new Bus(i,nofBusses,nofStops);
//    	
//    	for (int j = 0; j < nofStops; j++) {
//    		b.addStop(in.nextInt());
//    	}
//    	busses.add(b);
//    	i++;
//    }
    for(int l = 0; l < nofBusses; l++){
    	int nofStops = in.nextInt();
    	Bus b = new Bus(i,nofBusses,nofStops);
    	
    	for (int j = 0; j < nofStops; j++) {
    		b.addStop(in.nextInt());
    	}
    	busses.add(b);
    	i++;
    }
    System.out.println(busses.toString());
    
    // simulate
    String t = simulate(busses);
    out.println(t);
    
    // show secrets
    for (Bus b : busses) {
    	for (boolean s : b.knownSecrets){
    		System.out.print(s +",");
    	}
    	System.out.println();
    }
    
    out.close();
  }
  
  public static void syncKnownSecrets(Bus b1, Bus b2) {
	  for (int i = 0; i < b1.knownSecrets.length; i++) {
		  b1.knownSecrets[i] = b1.knownSecrets[i] || b2.knownSecrets[i];
		  b2.knownSecrets[i] = b1.knownSecrets[i];
	  }
  }
  
  public static String simulate(List<Bus> busses) {
	  for (int t = 0; t < 20000; t++) {
	    	// chat about secrets
	    	for (Bus b : busses) {
	    		for (int k = b.id; k < busses.size(); k++) {
	    			if (b.getCurrentStop(t)==busses.get(k).getCurrentStop(t)) {
	    				syncKnownSecrets(b, busses.get(k));
	    			}
	    		}
	    	}
	    	// check if everyone knows all the secrets
	    	Iterator<Bus> b = busses.listIterator();
	    	boolean ret = true;
	    	while (b.hasNext() && ret) {
	    		ret = ret && b.next().knowsAll();
	    	}
	    	if (ret) return Integer.toString(t);
	    	
	    }
	  return "NEVER";
  }
  

}