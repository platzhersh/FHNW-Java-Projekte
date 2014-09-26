

import java.io.*;
import java.util.*;

public class StreetParade
{
  public static void main(String[] args) throws Exception
  {
    Scanner in=new Scanner(new File("streetparade.in"));
    PrintWriter out=new PrintWriter("streetparade.out");
    
    Stack<Integer> sidestreet = new Stack<Integer>();
    Stack<Integer> parade = new Stack<Integer>();

    // get number of lovemobiles
    Integer nofCars = in.nextInt();
    System.out.println("nofCars: " + nofCars);
    
    // Try to maneuver the lovemobiles into the parade by using the sidestreet
    while (in.hasNext()) {
    	//System.out.println("sidestreet: " + sidestreet.toString());
    	//System.out.println("parade: " + parade.toString());
    	int a=in.nextInt();
    	processStack(sidestreet, parade);
    	sidestreet.push(a);
    	
    //out.println("solution");
    }
    processStack(sidestreet,parade);
    //System.out.println("sidestreet: " + sidestreet.toString());
	//System.out.println("parade: " + parade.toString());

    // return true or false for possible or impossible
    
	out.println(sidestreet.empty() ? "yes" : "no");
	//Date stop = new Date();
	//System.out.println("time: " + (stop.getTime() - start.getTime() + "ms"));
    in.close();
    out.close();
  }
  
  public static void processStack(Stack<Integer> from, Stack<Integer> to) {
	  //System.out.println("from: " + from.toString());
	  //System.out.println("to: " + to.toString());
	  if (from.empty()) return;
	  if (to.empty() && (from.peek() == 1)) {
		  to.push(from.pop());
		  processStack(from, to);
		  
	  }
	  else if (!to.empty() && to.peek() == from.peek() -1) {
		  to.push(from.pop());
		  processStack(from, to);
	  }
	  return;
  }
  
  // TODO: Non recursive function would go here
  public static void processStack2(Stack<Integer> sidestreet, Stack<Integer> parade, Integer val) {
	  
	  if (parade.empty()) parade.push(val);
	  
	  
	  
  }
  
}