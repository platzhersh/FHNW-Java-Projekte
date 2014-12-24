package ch.fhnw.efalg;
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
	
	// TODO: determine pivot element
	// TODO: Abbruchkriterium
	// TODO: Auswertung
	// TODO: Spezialfälle
	// TODO: int -> double
	
	public static int equals = 0;
	public static int greaterthan = 1;
	public static int smallerthan = -1;
	
	public int[] varRow, varCol;
	public int[][] simplexTable;
	public boolean[] varNotNegative;
	public int[] equationType;	// 0 = equals, 1 = greater than or equal, -1 = smaller than or equal
	public int numOfX, numOfY;
	public boolean max;
	
	public static void main(String[] args) throws FileNotFoundException {
		Simplex s = new Simplex();
		s.readCSV("BasicExample.csv");
		
		s.printTable();
		
		s.pivot();
		
		//System.out.println(m2[1][0]);
	}
	
		
	public int[][] readCSV(String filePath) throws FileNotFoundException {
		
		String csvFile = "lp_problems/" + filePath;
		BufferedReader br = null;
		String line = "";
		String csvSplitBy = ";";
			
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
			for (int i = 0; i < numOfX; i++) {
				simplexTable[0][1+i] = i;
				varRow[i] = 0;
				varCol[i] = 1+i;
			}
			
			// initialise target function (z = b1 + b2 ... + bq)
			String[] line1 = lines.get(1).split(csvSplitBy);
			
			// TODO: min in max umwandeln indem Zielfunktion mit -1 multipliziert wird
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
				
				// inequation to equation
				int[] eq = transformInequationToEquation(l);
				for (int j = 0; j <= numOfX; j++) {
					//simplexTable[i+1][j+1] = Integer.parseInt(l[j+1]);
					simplexTable[i+1][j+1] = eq[j];
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
	
	
	/***
	 * 
	 * @param inequation	form { = | <= | >=, a1, a2, a3 ... an }
	 * @return
	 */
	public int[] transformInequationToEquation(String[] inequation) {
		int[] equation = new int[inequation.length-1];
		for (int i = 1; i < inequation.length; i++) {
			equation[i-1] = Integer.parseInt(inequation[i]);
		}
				
		switch (inequation[0]) {
			case "<=":
				// multiply all a with -1, leave c untouched
				for (int i = 0; i+1 < equation.length; i++) {
					equation[i] *= -1;
				}
				break;
			case ">=":
				// multiply c with -1
				equation[equation.length-1] *= -1;
				break;
			default:
			case "=":
				break;
			
		}
		
		return equation;
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
		
		// get pivot element col
		int col = 1;
		while (simplexTable[numOfY+1][1] < 0) {
			col++;
		}
		
		// get pivot element row (only consider a < 0) where abs(c/a) is the smallest
		double minVal = Double.MAX_VALUE;
		int row = 0;
		double[] q = new double[numOfY];
		for (int j = 0; j < numOfY; j++) {
			if (simplexTable[j+1][col] < 0) {
				q[j] =  Math.abs(simplexTable[j+1][numOfX+1] / simplexTable[j+1][col]);
				if (q[j]<minVal) {
					minVal = q[j];
					row = j;
				}
			}
		}
		
		System.out.println("Pivot-Element: " + row + "/" + col +": " + simplexTable[row][col]);
		
		
		// TODO: pivot
		// 1. Gleichung nach xn umformen
		// 2. xn in allen yn einsetzen
		
		
	}
	
	
	public double[] multiply(double[] arr, double scalar) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] *= scalar;
		}
		return arr;
	}
	
	public double[] summate(double[] arr1, double[] arr2) {
		
		double[] result = new double[arr1.length];
		for (int i = 0; i < result.length; i++){
			result[i] = arr1[i] + arr2[i];
		}
		
		return result;
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
