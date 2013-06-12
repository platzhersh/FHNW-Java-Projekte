package ch.fhnw.algd1;

public class Skyscrapers2 {
	
	int n;
	int[][] map;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Skyscrapers2 sky = new Skyscrapers2();
		System.out.println(sky.toString());
		sky.solve();
	}
	
	public Skyscrapers2() {
		n = 6;
		map = new int[n][n];

		/* Counters Top */
		map[0][1] = 1;
		map[0][2] = 3;
		map[0][3] = 2;
		map[0][4] = 2;
	
		/* Counters Left */
		map[1][0] = 1;
		map[2][0] = 3;
		map[3][0] = 2;
		map[4][0] = 2;
		
		/* Counters Bottom */
		map[1][5] = 3;
		map[2][5] = 1;
		map[3][5] = 2;
		map[4][5] = 2;
		
		/* Counters Right */
		map[5][1] = 2;
		map[5][2] = 2;
		map[5][3] = 1;
		map[5][4] = 3;
		
	}
	
	public void setOnes() {
		// Überall wo nur 1 Skyscraper gesehen wird, muss gleich anliegend ein Skyscraper der maximalen Höhe stehen
		int x = 0; int y = 0;

		// Left Column
		while (map[0][y] != 1 && y < (n - 1)) {
			y++;
		}
		if (y < n-1) map[1][y] = n-2;
		y = 0;
		
		// Right Column
		while (map[5][y] != 1 && y < (n - 1)) {
			y++;
		}
		if (y < n-1) map[4][y] = n-2;
		
		// Top Column
		while (map[x][0] != 1 && x < (n - 1)) {
			x++;
		}
		if (y < n-1) map[x][1] = n-2;
		x = 0;
		
		// Bottom Column
		while (map[x][5] != 1 && x < (n - 1)) {
			x++;
		}
		if (x < n-1) map[x][4] = n-2;
		
		System.out.println(this.toString());
	}

	public void solve () {
		
		this.setOnes();
		
		for (int posY = 1; posY < n-1; posY++) {
			solveRowField(1,posY);
		}
	}
	public boolean solveRowField(int x, int y) {
		int t = n-1;
		boolean nextField = false;
		while (!nextField) {
			if (solveField(x,y,t) == false) return false;
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
				} else { nextField = solveRowField(x+1,y); t--; }
			}
		}
		return true;
	}
	public boolean solveField(int x, int y, int t) {
		while (t > 0) {
			t--; map[x][y] = t;
			System.out.println("Row: "+y+", Col: "+x+", t="+t);
			System.out.println(this.toString());
			if (validateRow(x,y) && validateCol(x,y)) return true;
		}
		return false;

	}
	
	public boolean isValid(int x, int y){
		return validateRow(x,y) && validateCol(x,y) && countLeft(y) && countRight(y) && countTop(x) && countBottom(x);
	}
	
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
		int highest = 0;		// speichert das höchste passierte Gebäude
		int count = 0;
		int ref = map[0][row];	// Referenzwert. Wie viele Skyscraper müssen gesehen werden?
		
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
		int highest = 0;		// speichert das höchste passierte Gebäude
		int count = 0;
		int ref = map[5][row];	// Referenzwert. Wie viele Skyscraper müssen gesehen werden?
		
		while (posX >= endX) {
			int cur = map[posX][row];
			if (cur > highest) { count++; highest = cur; }
			posX--;
		}
		return count == ref;
	}
	
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
		int highest = 0;		// speichert das höchste passierte Gebäude
		int count = 0;
		int ref = map[col][0];	// Referenzwert. Wie viele Skyscraper müssen gesehen werden?
		
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
		int highest = 0;		// speichert das höchste passierte Gebäude
		int count = 0;
		int ref = map[col][5];	// Referenzwert. Wie viele Skyscraper müssen gesehen werden?
		
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
