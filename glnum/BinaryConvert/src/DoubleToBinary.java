public class DoubleToBinary {
	
	public static void umrechnen(double d){
		long doubleToLongBits = Double.doubleToLongBits(d);
		String binaryString = Long.toBinaryString(doubleToLongBits);
		int nullsLeft = (64 - binaryString.length());

		for (int i = 0; i < nullsLeft; i++) 
			binaryString = "0".concat(binaryString);
		
		System.out.println("1bit Vorzeichen, 11bit Exponent, 52bit Mantisse");
		for(int i=0; i<binaryString.length(); i++){
			if(i==1) System.out.print("   ");
			if(i==12) System.out.print("   ");
			if(i>12&&i%4==0) System.out.print(" ");
			
			System.out.print(binaryString.charAt(i));
		}
		
	}
	
	public static void main(String[] args) {
		double d = 1;
		umrechnen(d);
	}
}

