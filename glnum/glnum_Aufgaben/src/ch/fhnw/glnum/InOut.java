package ch.fhnw.glnum;
// ---------------  Console Input/Output  ---------------------
//                                        E. Gutknecht Nov. 99    
import java.io.*;
import java.text.*;
import java.util.*;

public class InOut
{

   // ---------------  Input  ---------------------------------

   public static int getInt()                    // Integer-Value
   {  Integer wert;
      readData();
      wert = Integer.valueOf(new String(inpString, 0, dataLen));
      return wert.intValue(); 
   }


   public static float getFloat()                // Float-Value
   {  Float wert;
      readData();
      wert = Float.valueOf(new String(inpString, 0, dataLen)); 
      return wert.floatValue();
   }


   public static double getDouble()              // Double-Value
   {  Double wert;
      readData();
      wert = Double.valueOf(new String(inpString, 0, dataLen)); 
      return wert.doubleValue();
   }


   public static char getChar()                   // Character-Value
   {  char ch;
      try
      {  ch = (char) keyboard.read();
         if (ch == '\r')                         // Carriage Return ueberspringen
             ch = (char) keyboard.read();
         return ch;
      }
      catch (IOException e)
      {  System.out.println("Input-Error");
         System.exit(0);
      }
      return (char) 0;            // wegen Compiler      
   }

  
   public static String getLine()                //  ganze Zeile einlesen  
   {  char ch;
      int i = 0;
      ch = getChar();
      while (ch != '\n')
      {  inpString[i] = ch;
         i++;
         ch = getChar();
      }
      return new String(inpString, 0, i);
   }


   public static void skipLine()                  // Skip Line-Terminator 
   { 
     try
     {  while ( (char) keyboard.read() != '\n');
     }
     catch (IOException e)
     {  System.out.println("Input-Error");
        System.exit(0);
     }
   }


   static void readData()                          // interne Hilfsroutine
   {  int i;
      char ch;
      System.out.flush();                          // Output-Buffer ausgeben
      dataLen = 0;
      try
      {  //  ------ Whitespace ueberspringen ------
         ch = (char) keyboard.read();
         while ( ch == '\n'  || ch == '\r' || ch == ' ')
            ch = (char) keyboard.read();
         //  ------ Characters der Zahl einlesen  -------
         i = 0;
         while (ch != ' ' && ch != '\n' && ch != '\r' && ch >= 0)
         {  inpString[i] = ch;
            i++;
            ch = (char) keyboard.read();
         }     
         dataLen = i;
      }
      catch (IOException e)
      {  System.out.println("Input-Error");
         System.exit(0);
      }
   }


   // ---------------  Output  ----------------------------

   public static void print(int n, int width)
   {  
       System.out.print(toString(n, width));
   }


   public static void print(int n)               
   {  
       System.out.print(n);
   }


   public static void println(int n, int width)
   {  
       System.out.println(toString(n, width));
       System.out.flush();
   }


   public static void prinlnt(int n)               
   {  
       System.out.println(n);
   }


   public static void print(long n, int width)
   {  
       System.out.print(toString(n, width));
   }


   public static void print(long n)
   {  
       System.out.print(n);
   }


   public static void println(long n, int width)
   {  
       System.out.println(toString(n, width));
       System.out.flush();
   }


   public static void println(long n)
   {  
       System.out.println(n);
   }


   public static void print(double x, int fore, int aft)
   {   
       System.out.print(toString(x, fore, aft));
   }


   public static void print(double x)
   {   
       System.out.print(x);
   }


   public static void println(double x, int fore, int aft)
   {   
       System.out.println(toString(x, fore, aft));
       System.out.flush();
   }


   public static void println(double x)
   {   
       System.out.println(x);
   }


   public static void print(float x, int fore, int aft)
   {  
       System.out.print(toString(x, fore, aft));
   }


   public static void print(float x)
   {   
       System.out.print(x);
   }


   public static void println(float x, int fore, int aft)
   {  
       System.out.println(toString(x, fore, aft));
       System.out.flush();
   }


   public static void println(float x)
   {   
       System.out.print(x);
   }


   public static void print(char ch)
   {
        System.out.print(ch);
   }


   public static void println(char ch)
   {
       System.out.println(ch);
       System.out.flush();
   }


   public static void print(String s)
   {
        System.out.print(s);
   }


   public static void println(String s)
   {
        System.out.println(s);
        System.out.flush();
   }


   public static void newLine()
   {
        System.out.println();
        System.out.flush();
   }


   // ---------------  formatierte Ausgabe in Strings  -----------------------

   public static String toString(int n, int width)
   {    int diff; 
        String s = String.valueOf(n);
        diff = width - s.length();
        if (diff <= 0)
           return s;
        else
           return blanks.substring(0, diff) + s;
   }


   public static String toString(long n, int width)
   {    int diff; 
        String s = String.valueOf(n);
        diff = width - s.length();
        if (diff <= 0)
           return s;
        else
           return blanks.substring(0, diff) + s;
   }


   public static String toString(double x, int fore, int aft)
   {    int diff;
        String s;
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
        df.applyPattern("0." + pattern.substring(0, aft));
        s = df.format(x);
        diff = fore + 1 + aft - s.length();
        if (diff <= 0)
           return s;
        else
           return blanks.substring(0, diff) + s;
   }



   // ------------  Variablen  ------------------------

   static  InputStreamReader keyboard = new InputStreamReader(System.in);
   static char[] inpString = new char[256];
   static int dataLen;

   static String pattern = "0000000000000000000000000000000000000000";
   static String blanks  = "                                        ";
   static DecimalFormat df = new DecimalFormat();

}
