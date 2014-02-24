import java.io.*;
import java.util.*;

public class Quer
{
	
	
  public static String getDivisors(Integer i) {
	  
	  if (i < 10) return i.toString();
	  else {
	  
		  StringBuilder sb = new StringBuilder();
		  if (i % 9 == 0) { sb.append(9); sb.insert(0,getDivisors(i/9)); }
		  else if (i % 8 == 0) { sb.insert(0,8); sb.insert(0,getDivisors(i/8)); }
		  else if (i % 7 == 0) { sb.insert(0,7); sb.insert(0,getDivisors(i/7)); }
		  else if (i % 6== 0) { sb.insert(0,6);  sb.insert(0,getDivisors(i/6)); }
		  else if (i % 5== 0) { sb.insert(0,5);  sb.insert(0,getDivisors(i/5)); }
		  else if (i % 4== 0) { sb.insert(0,4);  sb.insert(0,getDivisors(i/4)); }
	      else if (i % 3== 0) { sb.insert(0,3);  sb.insert(0,getDivisors(i/3)); }
	      else if (i % 2== 0) { sb.insert(0,2);  sb.insert(0,getDivisors(i/2)); }
	      else return "-1";
		  System.out.println(sb.toString());
		  
		  if (sb.charAt(0) == '-') return "-1";
		  else return sb.toString();
	  }
  }
	
  public static void main(String[] args) throws Exception
  {
    Scanner in=new Scanner(new File("quer.in"));
    PrintWriter out=new PrintWriter("quer.out");
    
    out.write(getDivisors(Integer.parseInt(in.next())));
    
    out.close();
  }
}