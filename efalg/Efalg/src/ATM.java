import java.io.*;
import java.util.*;

public class ATM
{
	
  public static void main(String[] args) throws Exception
  {
    Scanner in=new Scanner(new File("atm.in"));
    PrintWriter out=new PrintWriter("atm.out");
    
    int n, minVal, maxVal;
    int[] notesAvailable;	// number of available notes by id
    int[] notesValue;		// value of banknote by id

    LinkedList<Integer> amounts = new LinkedList<Integer>();
    boolean[] values = new boolean[10000001];
    
    n = in.nextInt();	// number of different notes
    minVal = in.nextInt();
    maxVal = in.nextInt();
    
    notesAvailable = new int[n];	// number of available notes by id
    notesValue = new int[n];	// value of banknote by id
    
    for (int i = 0; i < n; i++) {
    	notesAvailable[i] = in.nextInt();
    	notesValue[i] = in.nextInt();
    }
    in.close();
   
    values[0] = true;
    amounts.add(0);
    
    for (int i = 0; i < n; i++) {
    	
    	for (int j = 0; j < notesAvailable[i]; j++) {
    		
    		LinkedList<Integer> am = new LinkedList<Integer>();
    		for (int l : amounts) {
    			if (values[l] && l <= maxVal) 
    				{
    					int index = l+notesValue[i];
    					if (!values[index]) {
    						am.add(index);
    						values[index] = true;
    					}
    				}
    		}
    		amounts.addAll(am);  		
    	}
    	
    }
    
    Collections.sort(amounts);
    for (int val : amounts) {
    	if (val >= minVal && val <= maxVal) out.println(val);
    }
    /*for (int i = minVal; i <= maxVal; i++) {
    	if (values[i]) out.println(i);
    }*/
    
    //System.out.println(amounts.toString());
    out.close();
  }
  
}
