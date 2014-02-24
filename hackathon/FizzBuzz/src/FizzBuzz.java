import java.io.*;
import java.util.*;

public class FizzBuzz
{
  public static void main(String[] args) throws Exception
  {
    Scanner in = new Scanner("fizzbuzz.in");
    PrintWriter out = new PrintWriter(new File("fizzbuzz.out"));
    
    out.write(in.nextLine());
    out.write("\n");
    //your code here
    //int a=in.nextInt();
    //out.println("solution");
    
    out.close();
  }
}