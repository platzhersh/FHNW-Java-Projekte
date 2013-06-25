package ch.fhnw.algd1;

public class Skyscrapers2 {
	
	int n;			// Gr�sse der n*n Matrix
	int[][] map;	// (n+2)*(n+2) Matrix - Skyscrapers & Z�hler
	int stepCount;	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Skyscrapers2 sky = new Skyscrapers2();
		System.out.println(sky.toString());
		sky.solve();
		System.out.println("\nNumber of solveField() calls: "+sky.getStepCount());
	}
	/***
	 * Initialisiert ein 6*6 Array und schreibt die vorgegebenen Z�hler in die erste und letzte Zeile / Spalte 
	 */
	public Skyscrapers2() {
		n = 6;
		map = new int[][] {
			{0, 1, 3, 2, 2, 0},
			{1, 0, 0, 0, 0, 2},
			{3, 0, 0, 0, 0, 2},
			{2, 0, 0, 0, 0, 1},
			{2, 0, 0, 0, 0, 3},
			{0, 3, 1, 2, 2, 0}
		};
		stepCount = 0;
	}

	public int getStepCount() {
		return this.stepCount;
	}
	/***
	 * überall wo nur 1 Skyscraper gesehen wird, muss gleich anliegend ein Skyscraper der maximalen Höhe stehen
	 */
	public void setOnes() {
		int x = 0; int y = 0;

		// Left Column
		while (map[0][y] != 1 && y < (n - 1)) {
			y++;
		}
		if (y < n-1) map[1][y] = n-2;
		y = 0;
		
		// Right Column
		while (map[n-1][y] != 1 && y < (n - 1)) {
			y++;
		}
		if (y < n-1) map[n-2][y] = n-2;
		
		// Top Column
		while (map[x][0] != 1 && x < (n - 1)) {
			x++;
		}
		if (y < n-1) map[x][1] = n-2;
		x = 0;
		
		// Bottom Column
		while (map[x][n-1] != 1 && x < (n - 1)) {
			x++;
		}
		if (x < n-1) map[x][n-2] = n-2;
		
		System.out.println(this.toString());
	}

	
	/**
	 * Backtracking Hauptmethode
	 * L�st die Matrix Feld umd Feld, Zeile um Zeile
	 */
	public void solve () {
		
		this.setOnes();
		
		for (int posY = 1; posY < n-1; posY++) {
			solveRowField(1,posY);
		}
	}

	/**
	 * Wird jeweils rekursiv f�r jede Spalte aufgerufen um die Zeile y zu l�sen
	 * Im Fehlerfalle springt der Compiler zur�ck in die Oberfunktion (vorheriges Feld)
	 * @param x Spaltennummer des aktuellen Feldes
	 * @param y Zeilennummer des aktuellen Feldes
	 * @return gibt TRUE zur�ck wenn die Zeile korrekt ist
	 */
	public boolean solveRowField(int x, int y) {
		int t = n;
		boolean nextField = false;
		while (!nextField) {
			if (solveField(x,y,--t) == false) return false;
			else {
				boolean topdown = true;
				if (y == n-2) {
					System.out.println("Test top-down / down-top");
					topdown = (countLeft(y) && countRight(y));
					}
				if (x == n-2) {
					if (!(countLeft(y) && countRight(y))) { 
						map[x][y] = 0;
						return false; 
					} else return topdown;
				} else { t--; nextField = solveRowField(x+1,y); }
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param x Spaltennummer des aktuellen Feldes
	 * @param y Zeilennummer des aktuellen Feldes
	 * @param t maximaler Wert den es zu testen gibt, solveField probiert f�r alle Werte < t bis ein g�ltiger gefunden wird
	 * @return gibt TRUE zur�ck wenn ein g�ltiger Wert gefunden wurde, FALSE wenn kein g�ltiger Wert gefunden werden konnte (t=0)
	 */
	public boolean solveField(int x, int y, int t) {
		this.stepCount++;
		while (t > 0) {
			t--; map[x][y] = t;
			System.out.println("Row: "+y+", Col: "+x+", t="+t);
			System.out.println(this.toString());
			if (validateRow(x,y) && validateCol(x,y)) return true;
		}
		return false;

	}
	
	/**
	 * Checkt ob der Feldwert in der Zeile eindeutig ist
	 * @param x Spaltennummer des zu pr�fenden Feldes
	 * @param y Zeilennumer des zu pr�fenden Feldes
	 * @return
	 */
	public boolean validateRow(int x, int y) {
		int validateMe = map[x][y];
		int xEnd = n-2;
		int posX = 1;
		int posY = y;
		
		while (posX <= xEnd) {
			// jede Zahl darf pro Zeile nur einmal vorkommen
			if (map[posX][posY] == validateMe && posX != x) return false;
			posX++;
		}
		return true;	
	}
	
	public boolean countLeft(int row) {
		int posX = 1;
		int endX = n-2;
		int highest = 0;		// speichert das h�chste passierte Geb�ude
		int count = 0;
		int ref = map[0][row];	// Referenzwert. Wie viele Skyscraper m�ssen gesehen werden?
		
		while (posX <= endX) {
			int cur = map[posX][row];
			if (cur > highest) { count++; highest = cur; }
			posX++;
		}
		return count == ref;
	}
	public boolean countRight(int row) {
		int posX = n-2;
		int endX = 1;
		int highest = 0;		// speichert das h�chste passierte Geb�ude
		int count = 0;
		int ref = map[n-1][row];	// Referenzwert. Wie viele Skyscraper m�ssen gesehen werden?
		
		while (posX >= endX) {
			int cur = map[posX][row];
			if (cur > highest) { count++; highest = cur; }
			posX--;
		}
		return count == ref;
	}
	
	/**
	 * Checkt ob der Feldwert in der Spalte eindeutig ist
	 * @param x Spaltennummer des zu pr�fenden Feldes
	 * @param y Zeilennumer des zu pr�fenden Feldes
	 * @return
	 */
	public boolean validateCol(int x, int y) {
		int validateMe = map[x][y];
		int yEnd = n-2;
		int posX = x;
		int posY = 1;
		
		while (posY <= yEnd) {
			// jede Zahl darf pro Zeile nur einmal vorkommen
			if (map[posX][posY] == validateMe && posY != y) return false;
			posY++;
		}
		return true;
	}
	
	public boolean countTop(int col) {
		int posY = 1;
		int endY = n-2;
		int highest = 0;		// speichert das h�chste passierte Geb�ude
		int count = 0;
		int ref = map[col][0];	// Referenzwert. Wie viele Skyscraper m�ssen gesehen werden?
		
		while (posY <= endY) {
			int cur = map[col][posY];
			if (cur > highest) { count++; highest = cur; }
			posY++;
		}
		return count == ref;
	}
	public boolean countBottom(int col) {
		int posY = n-2;
		int endY = 1;
		int highest = 0;		// speichert das h�chste passierte Geb�ude
		int count = 0;
		int ref = map[col][n-1];	// Referenzwert. Wie viele Skyscraper m�ssen gesehen werden?
		
		while (posY >= endY) {
			int cur = map[col][posY];
			if (cur > highest) { count++; highest = cur; }
			posY--;
		}
		return count == ref;
	}
	
	
	@Override
	public String toString() {
	String ret = "";
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			ret += map[j][i]+" ";
		}
		ret += "\n";
	}
	return ret;
	}	

}
