package ch.fhnw.algd1;

public class LinearSearch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final int n = 100000;
		int[] array = new int[n];
		for (int i = 0; i < n; i++) 
			array[i] = (int) (Math.random()*Integer.MAX_VALUE);
		
		System.out.println("Geben Sie den Integer an nach dem Sie suchen möchten:");
		String val = System.console().readLine();
		
		linearSearch(array, Integer.parseInt(val));

	}
	
	public static boolean linearSearch(int[] array, int val) {
		boolean found = false;
		if (array[array.length-1] != val) 
			array[array.length-1] = val;
		else
			found = true;
			
		//array[array.length-1] = array[array.length-1] == val ? array[array.length-1] : val;
		int i = 0;
		while (array[i] != val) i++;
		found = i < array.length || found;
		System.out.println(val + " found at array["+i+"]");
		return found;
	}
	
	/***
	 * Lineare Suche mit Wächter
	 * @param array
	 * @param val
	 * @return
	 */
	public static boolean linearSearch2(int[] array, int value) {
		boolean found;
		int last = array.length -1;
		if (array[last]==value) {
				found = true;
		}
		else {
			int v = array[last], i = 0;
			array [last] = value;
			while (array[i] != value) i++;
			found = i<last;
			array[last]=v;
		}
		return found;

	}
}