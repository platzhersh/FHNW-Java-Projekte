import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
 
public class Main {
 
  public static void main(String[] args) {
 
    // some helper variables
    String input = null;
    int counter = 0;
    Scanner scanner = new Scanner (System.in);
 
    // initialize data structure
    ArrayList arrayList = new ArrayList();
    TreeSet treeSet = new TreeSet();
    HashMap hashMap = new HashMap();
 
    do {
      // get the name of the person
      System.out.print("Bitte gebe den Namen ein: ");
      input = scanner.nextLine();
 
      // stop here if the input is empty
      if (!input.isEmpty()) {
 
        // create a person
        Person current = new Person();
        String[] inputArray = input.split(" ");
        Integer id = ++counter;
        current.setId(id);
        current.setFirstname(inputArray[0]);
        current.setLastname(inputArray[1]);
 
        // add the person to the data structures
        arrayList.add(current);
        treeSet.add(current);
        hashMap.put(id.toString(), current);
      }
 
    // repeat the name input until the input is empty
    } while (!input.isEmpty());
 
    // display the content of the Array List
    System.out.println();
    System.out.println("ArrayList:");
    for (Iterator i = arrayList.iterator(); i.hasNext(); ){
      System.out.println(i.next());
    }
 
    // display the content of the TreeSet
    System.out.println();
    System.out.println("TreeSet:");
    for (Iterator i = treeSet.iterator(); i.hasNext(); ){
      System.out.println(i.next());
    }
 
    // display the content of the HashMap
    System.out.println();
    System.out.println("HashMap:");
    for (Iterator i = hashMap.values().iterator(); i.hasNext(); ){
      System.out.println(i.next());
    }
  }
}