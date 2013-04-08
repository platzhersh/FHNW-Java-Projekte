package ch.fhnw.algd1;

public class TextSearch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final String text = "aabaab";
		final String patt = "aab";
		System.out.println("Find \""+patt+"\" in \""+text+"\"");
		System.out.println("\""+patt+"\" found at position "+findPattern(text,patt));
	}
	
	public static int findPattern (String text, String pattern){
		int ret = -1;
		int zeiger = 0;
		while (zeiger < text.length() - pattern.length()){
			// Vergleiche den Textausschnitt mit dem Pattern
			int i = zeiger;
			int j = 0;
			while ( j < pattern.length() && text.charAt(i) == pattern.charAt(j)){
				j++;
				i++;
			}
			if (j == pattern.length()){
				return i;
			}
		zeiger++;
		}
		return ret;
	}
	
}
