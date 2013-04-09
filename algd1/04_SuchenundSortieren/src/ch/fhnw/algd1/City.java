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
