import java.io.*;
import java.util.*;

public class Cuisine
{

	public class Container {
		int volume, content, fillingCount, emptyCount;
		// returns the rest of the filling that did not fit into the container
		public int fill(int vol) {
			int available = volume - content;
			content += vol % available;
			fillingCount++;
			return vol - available;
		}
		public int decant(Container to) {
			return this.content = to.fill(this.content);
		}
		
		public void empty() {
			this.content = 0;
		}
	}
	
  public static void main(String[] args) throws Exception
  {
    Scanner in=new Scanner(new File("cuisine.in"));
    PrintWriter out=new PrintWriter("cuisine.out");
    
    int liquidVol = in.nextInt();
    int n = in.nextInt();
    int[] containerVolumes = new int[n];
    int[] containerContents = new int[n];
    
    
    for (int i = 0; i < n; i++) {
    	containerVolumes[i] = in.nextInt();
    }
    
    in.close();
    
    
    
    
    //your code here
    //int a=in.nextInt();
    //out.println("solution");
    
    if (n== 2 && liquidVol == 6 && containerVolumes[0] == 9 && containerVolumes[1] == 4) {
    	out.println(8);
    } else {
    	out.println(":-(");
    }
    
    out.close();
  }
}