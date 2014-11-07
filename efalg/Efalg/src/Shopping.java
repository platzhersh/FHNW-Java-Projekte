import java.io.*;
import java.util.*;

public class Shopping
{
	
	final int max = Integer.MAX_VALUE/2;
	int w, h;
	public Vertex start, target;
	Vertex[][] mall;
	PriorityQueue<Shopping.Node> queue = new PriorityQueue<Shopping.Node>();
	
	public void init(Scanner in) {
		
		String[] first = in.nextLine().split(" ");
		w = Integer.parseInt(first[0]);
		h = Integer.parseInt(first[1]);
		mall = new Vertex[h][w];
		
		// initialize data, find S and D and replace all the X with 10 000
	    for (int i = 0; i < h; i++) {
	    	String l = in.nextLine();
	    	for(int j = 0; j < w; j++) {
	    		char v = l.charAt(j);
	    		if (v == 'S') {
	    			mall[i][j] = start = new Vertex(i, j, 0);
	    		}
	    		else if (v == 'X') mall[i][j] = new Vertex(i, j, max);
	    		else if (v == 'D') {
	    			mall[i][j] = target = new Vertex(i,j,0);
	    		}
	    		else mall[i][j] = new Vertex(i, j, v-'0');		// char to int
	    	}

	    	queue.add(new Node(start, 0));
	    	
	    }
	    
	}
	
	public void run(PrintWriter out) {
    	while(!queue.isEmpty())
    	{
    		Node n=queue.remove();
    		
    		if(n.vertex.visited)
    			continue;
    		n.vertex.visited = true;
    		n.vertex.cost = n.cost;
    		
    		if (n.vertex.posI+1 < h) {
    			Vertex down = mall[n.vertex.posI+1][n.vertex.posJ];
    			queue.add(new Node(down, n.cost + n.vertex.initCost));
    		}
    		if (n.vertex.posI-1 >= 0) {
    			Vertex up = mall[n.vertex.posI-1][n.vertex.posJ];
    			queue.add(new Node(up, n.cost + n.vertex.initCost));
    		}
    		if (n.vertex.posJ+1 < w) {
    			Vertex right = mall[n.vertex.posI][n.vertex.posJ+1];
    			queue.add(new Node(right, n.cost + n.vertex.initCost));
    		}
    		if (n.vertex.posJ-1 >= 0) {
    			Vertex left = mall[n.vertex.posI][n.vertex.posJ-1];
    			queue.add(new Node(left, n.cost + n.vertex.initCost));
    		}
    	}
    }

	
	
  public static void main(String[] args) throws Exception
  {
    Scanner in=new Scanner(new File("shopping.in"));
    PrintWriter out=new PrintWriter("shopping.out");
    
    //your code here    
    Shopping inst = new Shopping();
    inst.init(in); 
    inst.run(out);
    
    //System.out.println(inst.target.visited);
    //System.out.println(inst.target.cost);
   
    out.println(inst.target.cost);
    
    in.close();
    out.close();
  }
  
  public class Node implements Comparable<Node> {
	  int cost;
	  Vertex vertex;
	  
	  public Node(Vertex v, int c) {
		  vertex = v;
		  cost = c;
	  }

	@Override
	public int compareTo(Node n) {
		return this.cost - n.cost;
	}
  }
  
  public class Vertex {
	  public int posI, posJ, cost, initCost;
	  public boolean visited;
	  
	  public boolean equals(Vertex v) {
		  return (v.posI == this.posI && v.posJ == v.posI);
	  }
	  
	  public Vertex(int i, int j, int initC){
		  posI = i; posJ = j;
		  initCost = initC;
		  cost = max;
	  }
  }
}