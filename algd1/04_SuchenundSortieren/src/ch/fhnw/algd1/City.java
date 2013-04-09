package ch.fhnw.algd1;

public class City {

	int[][] buildings;
	
	public City(int n) {
		buildings = new int[n][n];
		
		//rows
		for (int i = 0; i < n; i++) {

			//cols
			for (int j = 0; j < n; j++) {
				int h = i+j+1;
				buildings[i][j] = h > n ? h - n : h; 
			}
		}
	}
	
	public int countBuildingsNORTH (int pos) {
		int count = 1;
		int y = buildings.length-1;
		if (pos < y){
			int temp = buildings[pos][y];
			while (temp < buildings.length && y >= 0) {
				if (temp < buildings[pos][y--]) {
					temp = buildings[pos][y];
					count++;
				}
			}
			
		}
		return count;
	}
	
	public int countBuildingsSOUTH (int pos) {
		int count = 1;
		int y = 0;
		if (pos > y){
			int temp = buildings[pos][y];
			while (temp < buildings.length && y >= 0) {
				if (temp < buildings[pos][y--]) {
					temp = buildings[pos][y];
					count++;
				}
			}
			
		}
		return count;
	}
	public int countBuildingsWEST (int pos) {
		int count = 0;
	}
	public int countBuildingsEAST (int pos) {
		int count = 0;
	}
	*/
	@Override
	public String toString() {
	String ret = "";
	int n = this.buildings.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				ret += buildings[i][j]+" ";
			}
			ret += "\n";
		}
	return ret;
	}
}
