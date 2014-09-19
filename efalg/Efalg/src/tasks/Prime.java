package tasks;
import java.io.*;
import java.util.*;

public class Prime
{
  public static void main(String[] args) throws Exception
  {
    Scanner in = new Scanner(new File("prime.in"));
    PrintWriter out=new PrintWriter("prime.out");
    
    int a = in.nextInt();
    int b = in.nextInt();
    
    for (int i = a; i <= b; i++) {
    	if (isPrime(i)) out.println(i);
    }
    
    
    
    in.close();
    out.close();
  }
  
  public static boolean isPrime(int i) {
	  
	  int j = 2;
	  while (j < i) {
		  if (i % j == 0) return false;
		  j++;
	  }
	  
	  return true;
  }
}