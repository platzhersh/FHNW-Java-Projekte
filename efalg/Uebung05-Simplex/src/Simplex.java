
public class Simplex {
	
	public static void main(String[] args) {
		int[][] m = new int[][] {
				{2, 1, 100},
				{1, 1, 80},
				{1, 0, 40}
		};
		
		int[][] m2 = pivot(m,1,0);
		
		for (int i = 0; i < m2.length; i++) {
			for (int j = 0; j < m2[0].length; j++) {
				System.out.print(m2[i][j] + " ");
			}
			System.out.println();
		}
		//System.out.println(m2[1][0]);
	}
	
	// TODO: determine pivot element
	// TODO: Abbruchkriterium
	// TODO: Auswertung
	// TODO: Spezialfälle
	
	public static int[][] pivot(int[][] matrix, int posI, int posJ) {
		
		int pivotRow = posI;
		int pivotCol = posJ;
		int pivot = matrix[pivotRow][pivotCol];
		int rowLength = matrix[0].length;
		int colLength = matrix.length;
		
		// pivotize row to make pivot == 1
		for (int i = 0; i < rowLength; i++) {
			matrix[pivotRow][i] /= pivot;
		}
		
		for (int i = 0; i < colLength; i++) {
			if (i != pivotRow) {
				int diff = matrix[i][pivotCol] / matrix[pivotRow][pivotCol];
				for (int j = 0; j < rowLength; j++) {
					matrix[i][j] -= diff*matrix[pivotRow][j];
				}
			}
		}
		
		return matrix;
	}
}
