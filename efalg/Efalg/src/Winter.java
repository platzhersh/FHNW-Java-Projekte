import java.io.*;
import java.util.*;


public class Winter
{
	final int max = Integer.MAX_VALUE/2;
	int cityCount, connectionCount;
	List<Vertex>[] cities;
	public Vertex start, target;
	Vertex[][] mall;
	PriorityQueue<Winter.Visit> queue = new PriorityQueue<Winter.Visit>();
	
	public void init(Scanner in) {
		
		String[] first = in.nextLine().split(" ");
		cityCount = Integer.parseInt(first[0]);
		connectionCount = Integer.parseInt(first[1]);
		
		
		// initialize data, find S and D and replace all the X with 10 000
	    for (int i = 0; i < connectionCount; i++) {
	    
	    	int depTime = in.nextInt();
	    	int fromCity = in.nextInt();
	    	int arrTime = in.nextInt();
	    	int toCity = in.nextInt();
	    	
	    	 
	    
	    }

	    	queue.add(new Visit(start, 0));
	    	
	}
	    
	
	
	public void run(PrintWriter out) {
    	while(!queue.isEmpty())
    	{
    		Visit n=queue.remove();
    		
    		if(n.vertex.visited)
    			continue;
    		n.vertex.visited = true;
    		/*n.vertex.cost = n.cost;
    		
    		if (n.vertex.posI+1 < h) {
    			Vertex down = mall[n.vertex.posI+1][n.vertex.posJ];
    			queue.add(new Visit(down, n.cost + n.vertex.initCost));
    		}
    		if (n.vertex.posI-1 >= 0) {
    			Vertex up = mall[n.vertex.posI-1][n.vertex.posJ];
    			queue.add(new Visit(up, n.cost + n.vertex.initCost));
    		}
    		if (n.vertex.posJ+1 < w) {
    			Vertex right = mall[n.vertex.posI][n.vertex.posJ+1];
    			queue.add(new Visit(right, n.cost + n.vertex.initCost));
    		}
    		if (n.vertex.posJ-1 >= 0) {
    			Vertex left = mall[n.vertex.posI][n.vertex.posJ-1];
    			queue.add(new Visit(left, n.cost + n.vertex.initCost));
    		}*/
    	}
    }

	
	
  public static void main(String[] args) throws Exception
  {
    Scanner in=new Scanner(new File("Winter.in"));
    PrintWriter out=new PrintWriter("Winter.out");
    
    //your code here    
    Winter inst = new Winter();
    inst.init(in); 
    //inst.run(out);
    
    //System.out.println(inst.target.visited);
    //System.out.println(inst.target.cost);
   
    //out.println(inst.target.cost);
    
    in.close();
    out.close();
  }
  
  
  public class Cities extends HashMap<CityID, Vertex>{
	  
  }
  
  private class CityID {
	  int city, time;
  }
  
  public class Visit implements Comparable<Visit> {
	  int cost;
	  Vertex vertex;
	  
	  public Visit(Vertex v, int c) {
		  vertex = v;
		  cost = c;
	  }

	@Override
	public int compareTo(Visit n) {
		return this.cost - n.cost;
	}
  }
  
  public class Vertex {
	  public int city, time;
	  List<Vertex> destinations;
	  public boolean visited;
	  
	  public boolean equals(Vertex v) {
		  return (this.city == v.city && this.time == v.time);
	  }
	  
	  public Vertex(int _city, int _time){
		  city = _city;
		  time = _time;
	  }
  }
}