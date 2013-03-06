package ch.fhnw.algd1;

public class IntegerLimits {

	public static void main(String[] args) {
		int val = 1;
		
		for (int i = 0; i <4; i++) {
			 val *= 256;
			System.out.println(val);
		}
		
		System.out.println("MaxINT: "+Integer.MAX_VALUE);
		
		long val2 = 1;
	
		for (int i = 0; i < 8; i++) {
			val2 *= 256;
			System.out.println(val2);
		}
		System.out.println("MaxLong: "+Long.MAX_VALUE);
		
		
	}
	
}
