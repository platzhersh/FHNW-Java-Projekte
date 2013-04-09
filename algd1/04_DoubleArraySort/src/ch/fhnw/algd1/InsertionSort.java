package ch.fhnw.algd1;

public class InsertionSort {

	public static double[] sort(double[] a) {
		
		for (int first = 0; first < a.length; first++) {
			int i = first;
			double value = a[first];
			while (i > 0 && value < a[i-1]) {
				a[i] = a[i-1];
				i--;
			}
			a[i] = value;
		}
		
		return a;
	}

}
