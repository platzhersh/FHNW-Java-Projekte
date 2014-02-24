import java.io.*;
import java.util.*;



public class Gardena
{
  public static void main(String[] args) throws Exception {
	  Scanner in=new Scanner(new File("gardena.in"));
	  PrintWriter out=new PrintWriter("gardena.out");

	  int knotenpunkte;
	  int hauptwasseranschlussnr;
	  
	  knotenpunkte = in.nextInt();
	  hauptwasseranschlussnr = in.nextInt();
	  
	  Tree tree = new Tree();
	  
	  while (in.hasNextLine()) {
		  if (in.nextInt() == hauptwasseranschlussnr) {
			  tree.root.setId(hauptwasseranschlussnr);
			  tree.treeIndex.put(hauptwasseranschlussnr, tree.root);
			  tree.addChildTube(tree.new Tube(in.nextInt(),in.nextInt()), tree.root);
		  }
		  else {
			  int parentid = in.nextInt();
			  tree.addChildTube(tree.new Tube(parentid,in.nextInt()), tree.getTubeById(parentid));
		  }
	  }
	  
	  out.write(tree.getTime());
	  
	  out.close();
  }
  
  public static class Tree
  {
	private Tube root;	// Hauptwasseranschluss
	private Map<Integer, Tube> treeIndex;
	
	
	public Tree() {	
		root = new Tube();
		treeIndex = new HashMap<Integer, Tube>();
		}
	
	public void addChildTube(Tube child, Tube parent) {
		parent.addChildTube(child);
		treeIndex.put(child.id, child);
	}
	public Tube getTubeById(int id) {
		return treeIndex.get(id); 
	}
	
	public int getTime() {
		return root.getTime();
	}
	
	  
	public class Tube {
		private int time;
		private int id;
		private Tube parent;
		private List<Tube> children;
		  
		public Tube() {
			children = new ArrayList<Tube>();
		}

		public Tube(int i, int t) {
			id = i;
			time = t;
		}
		
		public void setId(int i) {
			id = i;
		}
		
		public void addChildTube(Tube child) {
			children.add(child);
		}
		
		private int getTime() {
			int childTime = 0;
			for (Tube t : children) {
				childTime += t.getTime();
			}
			return this.time > childTime ? childTime : this.time;
		}
	  }
  }
  
}