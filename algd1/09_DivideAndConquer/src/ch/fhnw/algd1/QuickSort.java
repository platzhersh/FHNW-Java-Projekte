package ch.fhnw.algd1;

import de.vogella.algorithms.sort.quicksort.QuickSortVogella;

public class QuickSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] a = new int[1000000];
		for (int i = 0; i < a.length; i++) {
			a[i] = (int) (Math.random()*100000);
		}
		
		long startTime = System.nanoTime();
		quicksort(a);
		long endTime = System.nanoTime();
		
		long duration = endTime - startTime;
		System.out.println("Done after "+duration/1000000000.0+"s");
		
		
		
		startTime = System.nanoTime();
		quicksort(a);
		endTime = System.nanoTime();
		duration = endTime - startTime;
		System.out.println("Done after "+duration/1000000000.0+"s");
		
		// reverse a
		for(int i = 0; i < a.length /2 ; i++)
		{
		    int temp = a[i];
		    a[i] = a[a.length - i - 1];
		    a[a.length - i - 1] = temp;
		}
		startTime = System.nanoTime();
		quicksort(a);
		endTime = System.nanoTime();
		duration = endTime - startTime;
		System.out.println("Done after "+duration/1000000000.0+"s");
		
		
		int[] b = new int[1000000];
		for (int i = 0; i < b.length; i++) {
			b[i] = 1;
		}
		
		startTime = System.nanoTime();
		quicksort(b);
		endTime = System.nanoTime();
		duration = endTime - startTime;
		System.out.println("Done after "+duration/1000000000.0+"s");
		
	}
	
	public static int[] quicksort(int[] array) {
		
		if (array.length > 1) return sort(array, 0, array.length -1);
		else return array;
	}
	
	public static int[] sort(int[] a, int l, int r) {
		
		// Phase 1
		int p = a[l + (r-l)/2];;
		int i = l, j = r;
		
		do {
			while (a[i] < p) i++;
			while (a[j] > p) j--;
			if (i<=j) {
				a = swap(a, i, j);
				i++; j--;
			}
		} while (i <j);

		// Phase 2		
		if (j>l) sort(a,l,j);
		if (r>i) sort(a,i,r);
		
		// Phase 3
		
		return a;
	}

	private static int[] swap (int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
		return a;
	}
	
	public static void printArray(int[] a) {
		System.out.print("[");
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i]+", ");
		}
		System.out.print("]\n");
	}
}
