package ch.fhnw.conpr.jmm;

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
	
	public static void main(String[] args) {
		List<String> p1 = new LinkedList<String>();
		p1.add("x=1;");
		p1.add("b=y;");
		List<String> p2 = new LinkedList<String>();
		p2.add("y=1;");
		p2.add("a=x;");
		
		List<List<String>> interleavings = interleave(p1, p2);
		
		System.out.println(interleavings.size() + " interleavings:\n");
		for(List<String> interleaving : interleavings) {
			System.out.println("x=0; y=0; a=0; b=0;");
			for(String statement : interleaving) {
				System.out.println(statement);
			}
			System.out.println();
		}
	}

}

