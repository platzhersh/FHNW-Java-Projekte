import java.io.*;
import java.util.*;

public class Chocolate
{
  public static void main(String[] args) throws Exception
  {
    Scanner in=new Scanner(new File("chocolate.in"));
    PrintWriter out=new PrintWriter("chocolate.out");
    
    // 1. read input file
    
    Chocolate c;
    
    String[] line1 = in.nextLine().split(" ");
    int width = Integer.valueOf(line1[0]);
    int height = Integer.valueOf(line1[1]);
    
    c = new Chocolate(width, height);
    
    for(int i = 0; i < height; i++) {
    	String[] line = in.nextLine().split(" ");
    	for(int j = 0; j < width; j++) {
    		c.setWeight(i, j, Integer.parseInt(line[j])); 
    	}
    }
    
    in.close();
    
    // 2. algorithm
    int result = c.cut(0,0,height,width);
    System.out.println(result);
    
    // 3. write output file
    out.write(Integer.toString(result));
    
    out.close();
  }
  
  int[][] chocolate;
  int[][][][] cache;
  public Chocolate(int w, int h) {
	  chocolate = new int[h][w];
	  cache = new int[h][w][h+1][w+1];
  }
  
  public void setWeight(int i, int j, int val) {
	  chocolate[i][j] = val;
  }
  public int getWeight(int i, int j) {
	  return chocolate[i][j];
  }
  
  public int cut(int posI, int posJ, int h, int w) {
	  if (w == 1 && h == 1) {
		  return 0;
	  }
	  int min = Integer.MAX_VALUE;
	  //System.out.println("loop1:");
	  for (int i = 1; i < h; i++) {
		  min = Math.min(min, cut(posI,posJ,i,w) + cut(posI+i,posJ,h-i,w));	  
	  }
	  for (int j = 1; j < w; j++) {
		  min = Math.min(min, cut(posI,posJ,h,j) + cut(posI,posJ+j,h,w-j));
	  }	
	  
	  // add self-weight
//	  if (cache[posI][posJ][h][w] != 0) {
//		  min += cache[posI][posJ][h][w];
//	  } else {
		  for (int i = posI; i < posI+h; i++) {
			  for (int j = posJ; j < posJ+w; j++) {
				  //System.out.println(min);
				  min+=getWeight(i,j);
			  }
		  //}
		  //cache[posI][posJ][h][w] = min;
	  }
	  return min;
  }
}