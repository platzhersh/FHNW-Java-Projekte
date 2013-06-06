
public class StringBuilders {

	static void P(int i) { System.out.print(i);}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		printChars1Compact(20);
		System.out.println("\n");
		printChars1(20);
		System.out.println("\n");
		printChars2(20);
		System.out.println("\n");
		printChars3(20);
		
	}
	
	static void printChars1 (int n) {
		int round = 1;
		int i = 0;		// number of chars
		while (i < n) {
			P(1);
			i++;
			for (int j = 0; j < round && i < n ; j++) {
				P(0);
				i++;
			}
			round++;
		}
	}
	
	static void printChars1Compact (int n) {
		int pos1 = 1; // nächste Position wo 1 ausgegeben werden muss
		int count = 1; // Anzahl bereits ausgegebener 1
		for (int i = 1; i <= n; i++) {
			if (pos1 == i) { P(1); pos1 += ++count; }
			else P(0);
		}
	}
	
	static void printChars2 (int n) {
		int round = 1; 	// uneven to print 1, even to print 0
		int last = 1;	// number of chars in last round
		int bflast = 0; // number of chars in round before last round
		int i = 0;		// number of chars
		int val = 0;
		while (i < n) {
			int t = last + bflast;
			val = val == 1 ? 0 : 1;
				for (int j = 0; j < t && i <n ; j++) {
					P(val);
					i++;
				}
			bflast = last;
			last = t;
			round++;
		}
	}
	static void printChars3 (int n) {
		/* Binärzahlen
		 * 1 01 11 100 101 110 111 ..
		 */
	}
	
}
