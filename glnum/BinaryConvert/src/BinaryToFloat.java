public class BinaryToFloat {

	public static void umrechnen(String a, String b, String c) {
		int vorzeichen=1;
		float mantisse=1;
		float exponent=0;
		float resultat=0;
		String binaryString = a + b + c;
		binaryString=binaryString.replaceAll(" ", "");

		if (binaryString.length() != 32){
			System.err.println("FEHLER, es sind keine 32 Zeichen!!!");
			if(a.replaceAll(" ", "").length()!=1) System.err.println("FEHLER, vorzeichenString != 1 Zeichen!!!");
			if(b.replaceAll(" ", "").length()!=8) System.err.println("FEHLER, mantisseString != 8 Zeichen!!!");
			if(c.replaceAll(" ", "").length()!=23) System.err.println("FEHLER,  exponentString != 23 Zeichen!!!");
		}else if(binaryString.substring(1,9).equals("11111111")&&binaryString.substring(9,32).contains("1")){
			System.out.println("Der eingegebene Binarystring ergibt: NaN");
		}else {
			for (int i = 0; i < 32; i++) {
				// Vorzeichen Bit 0
				if (i == 0){
					if(binaryString.charAt(i)=='1') 
						vorzeichen=vorzeichen*-1;
				}
				// Exponent Bit 1-8
				if (i <= 8 && i > 0) {
					if( binaryString.charAt(i)=='1')
						exponent+=Math.pow(2,(8-i)); //2^(8-i)		
				}
				// Mantisse Bit 9-31
				if (i > 8) {
					if( binaryString.charAt(i)=='1')
						mantisse+=Math.pow(2, (8-i));
				}
			}
			resultat=vorzeichen*mantisse*(float)Math.pow(2,((double)exponent-127));
			System.out.print("Der eingegebene Binarystring ergibt: "+resultat);
		}

	}

	public static void main(String[] args) {
		String vorzeichenString = "1"; 			// 1bit Vorzeichen
		String  exponentString= "0000 0000"; // 8bit Exponent Leerzeichen werden autom. rausgelöscht!
		String  mantisseString= "111 1111 1111 1111 1111 1111"; // 23bit Mantisse Leerzeichenwerden autom rausgelöscht!

		umrechnen(vorzeichenString, exponentString,mantisseString);
	}
}
