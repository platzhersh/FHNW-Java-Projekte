package test;

import java.util.Arrays;
import java.util.HashSet;

public class HashSetTest {
	
	public static class NoteState {
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
	
	public static class blubb {
		
	}
	
	public static void main(String[] args) throws Exception {
		HashSet<NoteState> visited = new HashSet<NoteState>();
		
		NoteState s1 = new NoteState(new int[] {1,2,3,4});
		NoteState s2 = new NoteState(new int[] {1,2,3,4});
		
		visited.add(s1);
		System.out.println(visited.size());
		System.out.println(visited.contains(s2));
		visited.add(s2);
		System.out.println(visited.size());
	}
	
}
