import java.io.*;
import java.util.*;

public class ATM
{

  public class NoteState {
	  int[] notesUsed;
	  
	  public NoteState(int[] a) {
		  notesUsed = a;
	  }
	  public int[] getNodesUsed() {
		  return notesUsed;
	  }
	  
	  @Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(notesUsed);
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			NoteState other = (NoteState) obj;
			if (!Arrays.equals(notesUsed, other.notesUsed))
				return false;
			return true;
		}
  }

  public int n, minVal, maxVal;
  public int[] notesAvailable;	// number of available notes by id
  public int[] notesValue;		// value of banknote by id
  public HashSet<NoteState> visited;
  public TreeSet<Integer> amounts;
  int count;
  
  
  public ATM() {
	  visited = new HashSet<NoteState>();
	  amounts = new TreeSet<Integer>();
	  count = 0;
  }
  
  public void solve(int[] notesUsed) {
	  NoteState s = new NoteState(notesUsed);
	  if (!visited.contains(s)) {
		  
		  visited.add(s);
		  int a = getAmount(notesUsed);	// könnte als parameter weitergegeben werden
		  if (a <= maxVal) {
			  count++;
			  if (a >= minVal) {
				  amounts.add(a);
			  }
			  for (int i = 0; i < notesUsed.length; i++) {
				  if (notesAvailable[i] - notesUsed[i] > 0) {
					  int[] notesUsednew = notesUsed.clone();
					  notesUsednew[i] += 1;
					  solve(notesUsednew);
				  }
			  }
		  }
	  }
  }
  
  public int getAmount(int[] notesUsed) {
	  int ret = 0;
	  for (int i = 0; i < notesUsed.length; i++) {
		  ret += notesUsed[i]*this.notesValue[i];
	  }
	  return ret;
  }
	
  public static void main(String[] args) throws Exception
  {
    Scanner in=new Scanner(new File("atm.in"));
    PrintWriter out=new PrintWriter("atm.out");
    
    ATM solver = new ATM();
    
    solver.n = in.nextInt();	// number of different notes
    solver.minVal = in.nextInt();
    solver.maxVal = in.nextInt();
    
    solver.notesAvailable = new int[solver.n];	// number of available notes by id
    solver.notesValue = new int[solver.n];	// value of banknote by id
    
    for (int i = 0; i < solver.n; i++) {
    	solver.notesAvailable[i] = in.nextInt();
    	solver.notesValue[i] = in.nextInt();
    }
    in.close();
    
    solver.solve(new int[solver.n]);
    
    // do magic here
    
    //System.out.println(solver.amounts.toString());
    //System.out.println(solver.count);
    //Collections.sort(solver.amounts);
    for (int i : solver.amounts) {
    	
    	out.println(i);
    }
    
    out.close();
  }
  
}
