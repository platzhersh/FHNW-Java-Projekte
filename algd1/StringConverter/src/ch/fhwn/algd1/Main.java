package ch.fhwn.algd1;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(StringConverter.utfToLatin1("Hallo Welt! Wie gehts, du alte Sau? הצהü?$+~ג"));
		System.out.println("\u0041\u10384\u0042\u03A9");
		System.out.println(StringConverter.utfToLatin1("\u0041\u10384\u0042\u03A9"));

	}

}
