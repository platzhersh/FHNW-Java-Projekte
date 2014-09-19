package tasks;
import java.io.*;
import java.util.*;

public class FizzBuzz
{
  public static void main(String[] args) throws Exception
  {
    Scanner in = new Scanner(new File("fizzbuzz.in"));
    PrintWriter out=new PrintWriter("fizzbuzz.out");
    
    //your code here
    
    int a = in.nextInt();
    int b = in.nextInt();
    
    for (int i = a; i <= b; i++) {
    	if (i % 3 == 0) out.print("Fizz");
    	if (i % 5 == 0) out.print("Buzz");
    	if ((i % 3 != 0) && (i % 5 != 0)) out.print(i);
    	out.println();
    }
    
    in.close();
    out.close();
  }
}