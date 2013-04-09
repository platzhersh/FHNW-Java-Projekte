package ch.fhnw.algd1;

public class BinarySearch {
	
	public static int find(double[] a, double value) {
		
		int first = 0, last = a.length -1, m;
		
		while (last >= first) {
			m = (last-first) /2 + first;
			if (a[m]<value) {
				first = m+1;
			} else if (a[m] > value) {
				last = m-1;
			} else {
				return m;
			}
		}
		
		return -1;

	}
}
