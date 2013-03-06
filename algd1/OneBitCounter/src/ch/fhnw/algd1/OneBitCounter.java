package ch.fhnw.algd1;

public class OneBitCounter {

	static int intSize = 32;
	/***
	 * takes ~8*32 operations to count
	 * @param x
	 * @return
	 */
	public static int count(int x) {
		int y = 0;	// Anzahl 1-Bits einer Integerzahl
		for (int i=0; i< intSize; i++) {
			if (x%2 == 1) y++;
			x >>>= 1;
		}
		return y;
	}
	/***
	 * takes ~8*32 operations to count
	 * @param x
	 * @return
	 */
	public static int count2(int x) {
		int y = 0;	// Anzahl 1-Bits einer Integerzahl
		for (int i=0; i< intSize; i++) {
			if (x<0) y++;
			x <<= 1;
		}
		return y;
	}
	/***
	 * takes 5*10 operations to count bits of Integer
	 * @param x
	 * @return
	 */
	public static int countEfficiently(int x) {
		int iEven, iOdd, d = 1;
		iEven = x & 0x55555555; x >>= d; iOdd = x & 0x55555555;
		x = iOdd + iEven; d <<= 1 ;
		iEven = x & 0x33333333; x >>= d; iOdd = x & 0x33333333;
		x = iOdd + iEven; d <<= 1 ;
		iEven = x & 0x0F0F0F0F; x >>= d; iOdd = x & 0x0F0F0F0F;
		x = iOdd + iEven; d <<= 1 ;
		iEven = x & 0x00FF00FF; x >>= d; iOdd = x & 0x00FF00FF;
		x = iOdd + iEven; d <<= 1 ;
		iEven = x & 0x0000FFFF; x >>= d; iOdd = x & 0x0000FFFF;
		x = iOdd + iEven; d <<= 1 ;
		return x;
		}

	public static void main (String[] args) {
		System.out.println("Anzahl 1-er Bits von  17341 = "+count(17341));
		System.out.println("Anzahl 1-er Bits von  17341 = "+count2(17341));
		System.out.println("Anzahl 1-er Bits von  17341 = "+countEfficiently(17341));
	}

}
