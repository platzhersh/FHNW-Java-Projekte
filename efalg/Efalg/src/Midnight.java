import java.io.File;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.Scanner;


public class Midnight {
	final int MAX_VAL = Integer.MAX_VALUE/2;
	int w, h, walls;
	public Vertex start, target;
	Vertex[][] mall;
	PriorityQueue<Midnight.Visit> queue = new PriorityQueue<Midnight.Visit>();
	
	public void init(Scanner in) {
		
		String[] first = in.nextLine().split(" ");
		w = Integer.parseInt(first[0]);
		h = Integer.parseInt(first[1]);
		walls = Integer.parseInt(first[2]);
		mall = new Vertex[h][w];
		
		// initialize data, find S and D and replace all the X with 10 000
	    for (int i = 0; i < h; i++) {
	    	String l = in.nextLine();
	    	for(int j = 0; j < w; j++) {
	    		char v = l.charAt(j);
	    		if (v == 'B') {
	    			mall[i][j] = start = new Vertex(i, j, 0, false);
	    		}
	    		else if (v == '#') mall[i][j] = new Vertex(i, j, 1, true);
	    		else if (v == 'K') {
	    			mall[i][j] = target = new Vertex(i,j,0, false);
	    		}
	    		else mall[i][j] = new Vertex(i, j, 1, false);		// standard cost
	    	}	    	
	    }
	    queue.add(new Visit(start, 0, walls));
	    
	}
	
	public void run(PrintWriter out) {
    	while(!queue.isEmpty())
    	{
    		Visit n=queue.remove();
    		
    		if(n.vertex.visited)
    			continue;
    		n.vertex.visited = true;
    		n.vertex.cost = n.cost;
    		n.vertex.doors = n.doors;
    		
    		if (n.vertex.posI+1 < h) {
    			Vertex down = mall[n.vertex.posI+1][n.vertex.posJ];
    			int doors = n.doors;
    			if (down.isWall && doors >= 1) {
    				doors-=1;
    				queue.add(new Visit(down, n.cost + 1, doors));
    			} else if (!down.isWall) {
    				queue.add(new Visit(down, n.cost + 1, doors));
    			}
    		}
    		if (n.vertex.posI-1 >= 0) {
    			int doors = n.doors;
    			Vertex up = mall[n.vertex.posI-1][n.vertex.posJ];
    			if (up.isWall && doors >= 1) {
    				doors-=1;
    				queue.add(new Visit(up, n.cost + 1, doors));
    			} else if (!up.isWall) {
    				queue.add(new Visit(up, n.cost + 1, doors));
    			}
    		}
    		if (n.vertex.posJ+1 < w) {
    			int doors = n.doors;
    			Vertex right = mall[n.vertex.posI][n.vertex.posJ+1];
    			if (right.isWall && doors >= 1) {
    				doors-=1;
    				queue.add(new Visit(right, n.cost + 1, doors));
    			} else if (!right.isWall) {
    				queue.add(new Visit(right, n.cost + 1, doors));
    			}
    		}
    		if (n.vertex.posJ-1 >= 0) {
    			int doors = n.doors;
    			Vertex left = mall[n.vertex.posI][n.vertex.posJ-1];
    			if (left.isWall && doors >= 1) {
    				doors-=1;
    				queue.add(new Visit(left, n.cost + 1, doors));
    			} else if (!left.isWall) {
    				queue.add(new Visit(left, n.cost + 1, doors));
    			}
    		}
    	}
    }

	
	
  public static void main(String[] args) throws Exception
  {
    Scanner in=new Scanner(new File("midnight.in"));
    PrintWriter out=new PrintWriter("midnight.out");
    
    //your code here    
    Midnight inst = new Midnight();
    inst.init(in); 
    inst.run(out);
    
    //System.out.println(inst.target.visited);
    //System.out.println(inst.target.cost);
   
    out.println(inst.target.cost);
    
    in.close();
    out.close();
  }
  
  public class Visit implements Comparable<Visit> {
	  int cost, doors;
	  Vertex vertex;
	  
	  public Visit(Vertex v, int c, int d) {
		  vertex = v;
		  cost = c;
		  doors = d;
	  }

	@Override
	public int compareTo(Visit n) {
		if (this.cost - n.cost == 0) {
			return n.doors - this.doors;
		} else {
			return (this.cost - n.cost);
		}
	}
	
	public String toString() {
		return vertex.posI + ", " + vertex.posJ;
	}
  }
  
  public class Vertex {
	  public int posI, posJ, cost, initCost, doors;
	  public boolean visited, isWall;
	  
	  public boolean equals(Vertex v) {
		  return (v.posI == this.posI && v.posJ == v.posI);
	  }
	  	  
	  public Vertex(int i, int j, int initC, boolean wall){
		  posI = i; posJ = j;
		  initCost = initC;
		  cost = MAX_VAL;
		  isWall = wall;
		  doors = 0;
	  }
  }
}
