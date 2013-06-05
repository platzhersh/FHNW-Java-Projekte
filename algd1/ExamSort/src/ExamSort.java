/***
 * Sortierungsalgorithmus gemäss Aufgabe 3 der algd1 Prüfung FS 2012 bei C. Stamm
 * 
 * 1. Durchlaufe das gegebene Byte-Array a und vermerke jedes Auftreten eines Wertes
 *    u in einem Hilfsarray t an der Position u', wobei u' sehr einfach aus u berechnet werden kann:
 *    d.h. t[u'] muss zu Beginn 0 sein, und wird mit jedem Auftreten von u um eins erhöht.
 * 2. Durchlaufe t und schreibe jeweils fortlaufend t[u'] mal den Wert u in das Array a.
 * 
 * @author chregi
 *
 */
public class ExamSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		byte[] b = {-128,127,-1,-3,-4,-1,-1,4,10,1,-5,20};
		printByteArray(sort(b));
	}
	
	public static byte[] sort(byte[] a) {
		int[] t = new int[256];			// -127 ... 128 -> 256 possible values
		
		byte val;
		int times;

		// go through byte array and count occurences of every possible byte
		// then store the count in a second array t[256]
		for (int i= 0; i < a.length; i++) {
			val = a[i];
			t[val+128]++;				// -128 count should be stored in t[0], val+128 ^= u'
		}

		// rewrite a[] with the count information out of t[]
		int next = 0;					// next free array pos
		for (int j= 0; j < 256; j++) {
			val = (byte) (j-128); 
			times = t[j];
			for (int k = 0; k < times; k++) {
				a[next] = val;
				next += 1;
			}
		}
		return a;
	}
	
	public static void printByteArray(byte[] b) {
		
		for (byte by : b) {
			System.out.println(by);
		}
	}
}
