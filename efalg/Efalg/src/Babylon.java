import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;


public class Babylon {
	
	// try to keep x > y, that way we can cut cost in half
	public class Block implements Comparable<Block> {
		int x,y,z;
		public Block(int _x, int _y, int _z) {
			y = _y;		// height
			
			// width always > depth:
			if (_x > _z) {
				x = _x;		// width
				z = _z;		// depth
			} else {
				x = _z;		// height
				z = _x;		// depth
			}
		}
		
		// smaller x = smaller block
		public int compareTo(Block b) {
			return this.x - b.x;
		}
		
		boolean fitsOnTop(Block bottom) {
		    return ((bottom.x > this.x) && (bottom.z > this.z)) || ((bottom.x > this.z) && (bottom.z > this.x));
		}
	}

	int n;
	LinkedList<Block> blocks;
	
	public Babylon(int noOfBlocks) {
		n = noOfBlocks;
		blocks = new LinkedList<Block>();
	}
	
	public static void main(String[] args) throws Exception {
		Scanner in=new Scanner(new File("babylon.in"));
	    PrintWriter out=new PrintWriter("babylon.out");
	    
	    int n = in.nextInt();
	    Babylon b = new Babylon(n);
	    	    
	    for (int i = 0; i < n; i++) {
	    	// add 3 variations per block
	    	int x = in.nextInt(); int y = in.nextInt(); int z = in.nextInt(); 
	    	b.blocks.add(b.new Block(x, y, z));
	    	b.blocks.add(b.new Block(y, z, x));
	    	b.blocks.add(b.new Block(z, x, y));
	    }
	    
	    Collections.sort(b.blocks);
	    
	    int[] memoize = new int[b.blocks.size()];
	    int maxGlobal = 0;
	    
	    for (int i = 0; i < b.blocks.size(); i++) {
	    	int maxLocal = b.blocks.get(i).y;
	    	for (int j = 0; j < i; j++) {
	    		if (b.blocks.get(j).fitsOnTop(b.blocks.get(i)))
	    			if (maxLocal < memoize[j] + b.blocks.get(i).y) {
	    				maxLocal = memoize[j] + b.blocks.get(i).y;
	    			}
	    	}
	    	if (maxLocal>maxGlobal) maxGlobal = maxLocal;
	    	memoize[i] = maxLocal;
	    }
	    System.out.println(maxGlobal);
	}
	
	

}
