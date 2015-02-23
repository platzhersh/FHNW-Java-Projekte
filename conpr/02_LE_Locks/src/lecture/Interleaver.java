package lecture;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Interleaver {

    /**
     * Returns a Seq containing all possible interleavings of Seqs.
     * The order of the elements within the input Seqs is preserved.
     * 
     * (n*m)!/(m!^n) where n= #threads and m  #atomic actions
     */
	private static <A> List<List<A>> interleave(List<A> a, List<A> b) {
		if(b.size() == 0) return Collections.singletonList(a);
		if(a.size() == 0) return Collections.singletonList(b);
		List<List<A>> res = new LinkedList<List<A>>();
		List<A> left = new LinkedList<A>();
		List<A> right = new LinkedList<A>(a);
		A bHead = b.get(0);
		List<A> bTail = new LinkedList<A>(b);
		bTail.remove(0);
		do {
			for(List<A> list : interleave(right, bTail)) {
				LinkedList<A> interleaving = new LinkedList<A>(left);
				interleaving.add(bHead);
				interleaving.addAll(list);
				res.add(interleaving);
			}
			if(right.size() == 0) break;
			left.add(right.remove(0));
		} while(true);
		return res;
	}
	
	private static List<List<String>> createInterleavings(List<String> prog) {
		List<String> a = new LinkedList<String>();
		List<String> b = new LinkedList<String>();
		for(String statement : prog) {
			a.add("A: " + statement);
			b.add("B: " + statement);
		}
		return interleave(a, b);
    }
	
	public static void main(String[] args) {
	    // (2*3)! /(3!^2) = 20 interleavings
		
		List<String> prog = new LinkedList<String>();
		prog.add("read i onto stack;");
		prog.add("top of stack + 1;");
		prog.add("write top to i");
		
		for(List<String> interleaving : createInterleavings(prog)) {
			for(String statement : interleaving) {
				System.out.println(statement);
			}
			System.out.println();
		}
	}

}

