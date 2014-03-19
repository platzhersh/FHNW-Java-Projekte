public class BinaryToDouble {

	public static void umrechnen(String a, String b, String c) {
		int vorzeichen=1;
		double mantisse=1;
		double exponent=0;
		double resultat=0;
		String binaryString = a + b + c;
		binaryString=binaryString.replaceAll(" ", "");

		if (binaryString.length() != 64){
			System.err.println("FEHLER, es sind keine 64 Zeichen!!!");
			if(a.replaceAll(" ", "").length()!=1) System.err.println("FEHLER, vorzeichenString != 1 Zeichen!!!");
			if(b.replaceAll(" ", "").length()!=11) System.err.println("FEHLER, mantisseString != 11 Zeichen!!!");
			if(c.replaceAll(" ", "").length()!=52) System.err.println("FEHLER,  exponentString != 52 Zeichen!!!");
		}else if(binaryString.substring(1,12).equals("11111111111")&&binaryString.substring(12,64).contains("1")){
			System.out.println("Der eingegebene Binarystring ergibt: NaN");
		}else {
			for (int i = 0; i < 64; i++) {
				// Vorzeichen Bit 0
				if (i == 0){
					if(binaryString.charAt(i)=='1') 
						vorzeichen=vorzeichen*-1;
				}
				// Exponent Bit 1-11
				if (i <= 11 && i > 0) {
					if( binaryString.charAt(i)=='1')
						exponent+=Math.pow(2,(11-i)); //2^(11-i)			
				}
				// Mantisse Bit 12-63
				if (i > 11) {
					if( binaryString.charAt(i)=='1'){
						mantisse+=Math.pow(2, (11-i));
					}	
				}
			}
			resultat=vorzeichen*mantisse*Math.pow(2,(exponent-1023));
			System.out.print("Der eingegebene Binarystring ergibt: "+resultat);
		}

	}

	public static void main(String[] args) {
		String vorzeichenString = "0"; 			// 1bit Vorzeichen
		String exponentString = "111 1111 1111"; // 11bit Exponent Leerzeichen werden autom. rausgelöscht!
		String mantisseString = "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0001"; // 52bit Mantisse Leerzeichenwerden autom rausgelöscht!

		umrechnen(vorzeichenString,  exponentString,mantisseString);
	}
}
