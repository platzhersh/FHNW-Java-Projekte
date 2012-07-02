package ch.fhnw.chregi.algd1;

/***
 * @author chregi
 * 
 * KMP - Knuth-Morris-Pratt
 * 
 * Dieses Programm sucht einen String in einem grösseren String.
 *
 */

public class KPM_unfinished {

	String m_text;
	String m_pattern;
	int[] m_next;

	public KPM_unfinished(String text, String pattern) {
		m_text = text;
		m_pattern = pattern;
		m_next = new int[pattern.length()];
	}
	/***
	 * Diese Funktion analysiert das Pattern auf doppelte Buchstaben(folgen)
	 */
	public void initnext() {
		int i = 0; // wird das Pattern einmal durchlaufen
		int j = -1; // wird zum Vergleich mit dem Anfangsstück verwendet

		m_next[i] = j;
		while (i < m_pattern.length() - 1) {
			if ( j < 0 || m_pattern.charAt(j) == m_pattern.charAt(i)){
				i++;
				j++;
				m_next[i] = j;
			}
			else {
				// setzt j wieder auf den Wert des vorhergehenden Characters
				j = m_next[j];
			}
			// TODO: implementieren Sie diesen Loop
			//i++; // das ist nur ein Platzhalter
			//if (m_pattern.charAt(i) == m_pattern.charAt(0)) {
			//m_next[i] = ;
			//}
		}
		printPatternNext();
	}

	public void search_kmp() {
		int t = 0;	// Textpointer
		int p = 0;	// Patternpointer

		while (t < m_text.length()) {
			if ((p < 0) || m_text.charAt(t) == m_pattern.charAt(p)) {
				t++;
				p++;
			} else {
				p = m_next[p];
			}
			if (p == m_pattern.length()) {
				System.out.println("Gefunden, Uebereinstimmung startet bei "
						+ (t - p + 1) + ". Zeichen");
				p = 0;
			}
		}
	}	
	
	public static void main(String[] args) {
		 KPM_unfinished kpm = new KPM_unfinished("aaaaaaab", "aabaab");
		 kpm.initnext();
		 kpm.search_kmp();
	}

	private void printPatternNext() {
		System.out.print("--");
		for (int k = 0; k < m_pattern.length(); k++) {
			System.out.print("----");
		}
		System.out.println();
		System.out.print("|");
		System.out.print(" ");
		for (int k = 0; k < m_pattern.length(); k++) {
			System.out.print(" " + k + " |");
		}
		System.out.println("  Array-Index");
		System.out.print("--");
		for (int k = 0; k < m_pattern.length(); k++) {
			System.out.print("----");
		}
		System.out.println();
		System.out.print("|");
		System.out.print(" ");
		for (int k = 0; k < m_pattern.length(); k++) {
			System.out.print(" " + m_pattern.charAt(k) + " |");
		}
		System.out.println("  Pattern");
		System.out.print("--");
		for (int k = 0; k < m_pattern.length(); k++) {
			System.out.print("----");
		}
		System.out.println();
		System.out.print("|");
		for (int k = 0; k < m_next.length; k++) {
			System.out.print(" " + m_next[k] + " |");
		}
		System.out.println("  hier wird mit dem Vergleich weitergefahren");
		System.out.print("--");
		for (int k = 0; k < m_pattern.length(); k++) {
			System.out.print("----");
		}
		System.out.println();
	}
}
