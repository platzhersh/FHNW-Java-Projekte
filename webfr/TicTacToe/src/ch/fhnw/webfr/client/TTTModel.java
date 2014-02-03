package ch.fhnw.webfr.client;

public class TTTModel {
	private char[] board = new char[9];
	
	// Markierung für Feld abfragen, returnt 'x', 'o' oder ''
	public char getField(int field) {
		return board[field+1];
	}
	
	// Benutzer markiert Feld fied, f.h. mache ein 'x' in field
	// -10 		ungültiges Feld
	// >= 10 	Game Over kein Gewinner
	// 10		Benutzer gewinnt
	// 1..9		Feld das Computer markiert hat: muss mit 'o' auf Spielbrett markiert werden
	// -9...-1	Feld das Computer markiert hat um zu gewinnen, muss mit 'o' auf Spielbrett markiert werden
	public int setField(int field) {
		board[field+1] = 'x';
		if (field >= 10) return -10;
		return 0;
	}
}
