import java.io.*;
import java.util.*;

public class Stadion
{
  public static void main(String[] args) throws Exception
  {
    //Scanner in=new Scanner(new File("stadion.in"));
    PrintWriter out=new PrintWriter("stadion.out");
    BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("stadion.in"))));
    
//    System.out.println("In2, Line1: " + in2.readLine());
//    System.out.println("In2, Line2: " + in2.readLine());
    
    //your code here
    String[] line1 = in.readLine().split(" ");
    int stadionWidth = Integer.valueOf(line1[0]);
    int stadionHeight = Integer.valueOf(line1[1]);
    
    String[] line2 = in.readLine().split(" ");
    int areaWidth = Integer.valueOf(line2[0]);
    int areaHeight = Integer.valueOf(line2[1]);
    
    int[][] area = new int[areaWidth][areaHeight];
    
    for(int i = 0; i < areaHeight; i++) {
    	String[] line = in.readLine().split(" ");
    	for(int j = 0; j < areaWidth; j++) {
    		area[i][j] =  Integer.valueOf(line[j]); 
    	}
    }
    
    int bestMin = Integer.MAX_VALUE;
    
    // find best Place
    for (int i = 0; i+stadionHeight <= areaHeight; i++) {
    	for (int j = 0; j+stadionWidth <= areaWidth; j++) {
    		// calc min
    		int min = 0;
    		for (int k = i; k < stadionHeight+i; k++) {
    			for (int l = j; l < stadionWidth+j; l++) {
    				min += area[k][l];
    			}
    		}
        	
    		if (min < bestMin) {
    			bestMin = min;
    		}
    		//System.out.println("currentPos: "+i+","+j+"("+min+")");
        }
    }
   
    
    out.println(bestMin);
    
    out.close();
  }
}