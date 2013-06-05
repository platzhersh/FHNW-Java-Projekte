public class Damenproblem {

	int n; // Grösse des Schachbrettes
	int[] y;		// Feld
	int x; 			// Spalte
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Damenproblem d = new Damenproblem(8);
		d.solve();
	}
	
	/* ------------------------------------------------- */
	
	public Damenproblem(int n) {
		this.n = n;
		y = new int[n];
		x = 0; // 1. Spalte
		y[x] = 0; // 1. Dame steht auf dem ersten Feld
	}
	
	public void solve() {
		while (x >= 0 && x < n) {
			while(y[x] < n && istBedroht()) y[x]++;	
				if (y[x] < n) {
					x++;
					if (x < n) y[x] = 0;
				} else {
						x--;
						if (x >= 0) y[x]++;
				}
			}
		for (x = 0; x < n; x++) {
			System.out.println("Dame in Spalte " + (x + 1) + " steht auf Feld " +
			(y[x] + 1));
		}	
	}
	
	private boolean istBedroht() {
		// es gibt nur Damen in den Spalten [0 bis x)
		int i = 0;
		while (i < x) {
				if (Math.abs(y[x] - y[i]) == x - i || y[x] == y[i])
				return true;
				i++;
		}
		return false;
		}

}
