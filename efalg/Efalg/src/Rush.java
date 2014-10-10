import java.io.*;
import java.util.*;


// shit doesn't work..
public class Rush
{
	
  char[][] field;
  int count = 0;
  
  public void printField(){
	  for (int i = 0; i < 6; i++) {
		  for (int j = 0; j < 6; j++) {
			  System.out.print(field[i][j]+ " ");
		  }
		  System.out.println();
	  }
  }
	
  public static void main(String[] args) throws Exception
  {
    Scanner in = new Scanner(new File("rush.in"));
    PrintWriter out=new PrintWriter("rush.out");
    
    HashMap<Character, Car> cars = new HashMap<Character, Car>();
    
    Rush r = new Rush();
    
    //your code here
    r.field = new char[6][6];
    for (int i = 0; i < 6; i++) {
    	char[] line = in.nextLine().toString().replace(" ", "").toCharArray();
    	for (int j = 0; j < 6; j++) {
        	char c = line[j];
    		r.field[i][j] = c;
        	if (!(c == '.')) {
        		if (!cars.containsKey(c)) {
        			cars.put(c, r.new Car(c, i,j));
        		} else {
        			cars.get(c).setEndCord(i, j);
        		}
        	}
        }
    }
    
    //out.println("solution");
    Car yoshi = cars.get('A');
    Car d = cars.get('D');
    Car i = cars.get('I');
    
    yoshi.move(true);
    r.printField();
    
    i.move(false);
    r.printField();
    
    d.move(false);
    r.printField();
    
    yoshi.move(true);
    r.printField();
    
    yoshi.move(true);
    r.printField();
    
    yoshi.move(true);
    r.printField();
    
    in.close();
    //out.print("5");
    out.close();
  }
  
  public boolean success(int i, int j) {
	  if (i > 5 || j > 5) {
		  System.out.println("SUCCESS!");
		  return true;
	  } else return false;
  }
  
  public boolean isFieldFree(int i, int j){
	  
	  return field[i][j] == '.';
  }
  
  public class Car {
	  boolean orientationX;	// true = horizontal, false = vertical
	  int originI;		// verticalPos
	  int originJ;		// horizontalPos
	  int endI;
	  int endJ;
	  int length;
	  char id;
	  
	  public Car(char c, int origI, int origJ) {
		  originI = origI;
		  originJ = origJ;
		  endI = origI;
		  endJ = origJ;
		  id = c;
	  }
	  public void setEndCord(int i, int j) {
		  endI = i;
		  endJ = j;
		  
		  orientationX = originI == endI ? true : false;
		  length = orientationX ? endJ - originJ : endI - originI;
	  }
	  
	  /***
	   * right and down is true
	   * left and up is false
	   * depends on orientation
	   * @param dir
	   */
	  public void move(boolean dir) {
		  
		  // horizontal
		  if (orientationX) {
			  // temporary new coordinates
			  int originJnew, endJnew, nextFieldI, nextFieldJ;
			  if (dir) {
				  originJnew = originJ + 1;
				  endJnew = endJ + 1;
				  nextFieldI = endI;
				  nextFieldJ = endJnew;
			  } else {
				  originJnew = originJ - 1;
				  endJnew = endJ - 1;
				  nextFieldI = originI;
				  nextFieldJ = originJnew;
			  }
			  			  
			  if (!success(nextFieldI, nextFieldJ)) {
				  if (isFieldFree(nextFieldI, nextFieldJ)) {
					  if (dir) field[originI][originJ] = '.';
					  else field[endI][endJ] = '.';

					  // update coordinates
					  originJ = originJnew;
					  endJ = endJnew;
					  
					  if (dir) field[endI][endJ] = id;
					  else field[originI][originJ] = id;
					  
					  System.out.println(id + " moved");
					  field[endI][endJ] = id;
					  count++;
				  } else {
					  System.out.println("sorry, way is blocked " + id);
				  }
			  }
			  System.out.println("StepCount: " + count);
		  }
		  // vertical
		  else {
			  
			  // temporary new coordinates
			  int originInew, endInew, nextFieldI, nextFieldJ;
			  if (dir) {
				  originInew = originI + 1;
				  endInew = endI + 1;
				  nextFieldI = endInew;
				  nextFieldJ = endJ;
			  } else {
				  originInew = originI - 1;
				  endInew = endI - 1;
				  nextFieldI = originInew;
				  nextFieldJ = originJ;
			  }
			  
			  if (!success(nextFieldI, nextFieldJ)) {
				  if (isFieldFree(nextFieldI, nextFieldJ)) {
					  if (dir) field[originI][originJ] = '.';
					  else field[endI][endJ] = '.';
					  
					  // update coordinates
					  originI = originInew;
					  endI = endInew;
					  
					  if (dir) field[endI][endJ] = id;
					  else field[originI][originJ] = id;
					  
					  field[originI][originJ] = id;
					  System.out.println(id + " moved");
					  count++;
				  } else {
					  System.out.println("sorry, way is blocked "+ id);
				  }
			  }
			  System.out.println("StepCount: " + count);
		  }
	  }
  }
}