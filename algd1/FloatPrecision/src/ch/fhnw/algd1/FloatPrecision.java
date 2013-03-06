package ch.fhnw.algd1;

public class FloatPrecision {

	public static void main(String[] args) {
		float val = 1;
		
		for (int i = 0; i < 4; i++) {
			val *= 256;
			System.out.println(val);
		}
		
		int val2 = 1000000000;
		int val3 = 1000000001;
		int diff = val3 -val2;
		System.out.println("Diff("+val3+"-"+val2+" : "+ diff);
		System.out.println("Equals: " + (val2 == val3) );
	}
	
}
