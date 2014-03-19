public class FloatToBinary {
	
	public static void umrechnen(float f){
		int floatToIntBits = Float.floatToIntBits(f);
		String binaryString = Integer.toBinaryString(floatToIntBits);
		int nullsLeft = (32 - binaryString.length());

		for (int i = 0; i < nullsLeft; i++) 
			binaryString = "0".concat(binaryString);
		
		System.out.println("1bit Vorzeichen, 8bit Exponent, 23bit Mantisse");
		for(int i=0; i<binaryString.length(); i++){
			if(i==1) System.out.print("   ");
			if(i==9) System.out.print("   ");
			if(i>9&&i%4==0) System.out.print(" ");
			
			System.out.print(binaryString.charAt(i));
		}
		
	}
	
	public static void main(String[] args) {
		float f = (float) 1.42423423;
		umrechnen(f);
	}
}

