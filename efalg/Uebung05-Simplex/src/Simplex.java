import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/***
 *
 * @author chregi
 * 
 * simplex tableau has the following form:
 * 
 *		x1	x2	..	xn	c
 * y1	a11	a12	..	a1n	c1
 * y2	a21	a22	..	a1n	c1
 * y3	a31	a32	..	a1n	c1
 * z	b1	b2	..	bn	d
 *
 * 
 * variableArrays will save positions of base and non-base variables (xq and yq)
 * the index is the id of the variable, that will be used in the simplex tableau as a reference
 * 
 * varRow, varCol
 * 
 * numX, numY indicate how many x and y there are
 *
 */
public class Simplex {
	
	// TODO: read CSV
	// TODO: build proper matrix
	// TODO: determine pivot element
	// TODO: Abbruchkriterium
	// TODO: Auswertung
	// TODO: Spezialfälle
	
	int[] varRow, varCol;
	int[][] simplexTable;
	boolean[] varNotNegative;
	int numOfX, numOfY;
	boolean max;
	
	public static void main(String[] args) throws FileNotFoundException {
		Simplex s = new Simplex();
		s.readCSV("BasicExample.csv");
		
		s.printTable();
		
		//System.out.println(m2[1][0]);
	}
	
		
	public int[][] readCSV(String filePath) throws FileNotFoundException {
		
		String csvFile = "lp_problems/" + filePath;
		BufferedReader br = null;
		String line = "";
		String csvSplitBy = ";";
		
		Scanner in=new Scanner(new File("lp_problems/" + filePath));
		
		try {
			br = new BufferedReader(new FileReader(csvFile));
			
			LinkedList<String> lines = new LinkedList<String>();
			
			while ((line = br.readLine()) != null) {
				lines.add(line); 
			}
			
			String [] n = lines.get(0).split(csvSplitBy);
			numOfX = Integer.parseInt(n[0]);
			numOfY = Integer.parseInt(n[1]);
			
			varRow = new int[numOfX+numOfY];
			varCol = new int[numOfX+numOfY];
			
			// height = number of y + "Zielfunktion" + header row
			// width = number of x + constants + header column
			simplexTable = new int[numOfY+2][numOfX+2];

			varNotNegative = new boolean[numOfX];
			
			// initialise x1 ... xq
			for (int i = 0; i <= numOfX; i++) {
				simplexTable[0][1+i] = i;
				varRow[i] = 0;
				varCol[i] = 1+i;
			}
			
			// initialise target function (z = b1 + b2 ... + bq)
			String[] line1 = lines.get(1).split(csvSplitBy);
			max = line1[0].equals("max");
			for (int i = 1; i < numOfX+1; i++) {
				simplexTable[numOfY+1][i] = Integer.parseInt(line1[i]);
			}	
			
			String[] line2 = lines.get(2).split(csvSplitBy);

			for (int i = 0; i < numOfX; i++) {
				varNotNegative[i] = line2[i].equals("true");
			}
					
			// read restrictions, Y rows so to say
			for (int i = 0; i < numOfY; i++) {
				System.out.println(lines.get(3+i));
				String [] l = lines.get(3+i).split(csvSplitBy);
				
				varCol[numOfX+i] = 0;
				varRow[numOfX+i] = 1+i;
				simplexTable[i+1][0] = numOfX+i;
				for (int j = 0; j <= numOfX; j++) {
					simplexTable[i+1][j+1] = Integer.parseInt(l[j+1]);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return new int[1][1];
	}
	
	
	public void printTable() {
		for (int i = 0; i < simplexTable.length; i++) {
			for (int j = 0; j < simplexTable[0].length; j++) {
				System.out.print(simplexTable[i][j] + " ");
			}
			System.out.println();
		}
	}
	/***
	 * Find pivot element and modify simplexTable accordingly
	 * 
	 * @param matrix
	 * @return
	 */
	public void pivot() {
		
	}
	
	/***
	 * different implementation
	 * @param matrix
	 * @param posI
	 * @param posJ
	 * @return
	 */
	public static int[][] pivot2(int[][] matrix, int posI, int posJ) {
		
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
