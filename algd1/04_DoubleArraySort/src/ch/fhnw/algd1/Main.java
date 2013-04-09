package ch.fhnw.algd1;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double[] a = {4,1,2,3,10,9,8,6,7};
		
		double[] b = InsertionSort.sort(a);
		
		int c = BinarySearch.find(b,3);
		System.out.println(c);
	}

}
