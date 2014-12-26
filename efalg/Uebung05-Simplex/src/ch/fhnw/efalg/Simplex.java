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
 * simplex table has the following form:
 * 
 *		x1	x2	..	xn	c
 * y1	a11	a12	..	a1n	c1
 * y2	a21	a22	..	a1n	c1
 * y3	a31	a32	..	a1n	c1
 * z	b1	b2	..	bn	d
 *
 * 
 * variableArrays will save positions of base and non-base variables (xq and yq)
 * the index is the id of the variable, that will be used in the simplex table as a reference
 * 
 * varRow, varCol
 * 
 * numOfX, numOfY indicate how many x and y there are
 * 
 * implemented special cases:
 * - Satz von Bland - to avoid infinite loops
 * - all pivot candidates > 0
 *
 */
public class Simplex {
	
	// TODO: Spezialfälle:
	// TODO: all b < 0
	// TODO: any c < 0
	// TODO: Degeneration
	// TODO: Zweiphasenmethode

	
	public static int equals = 0;
	public static int greaterthan = 1;
	public static int smallerthan = -1;
	
	public int[] varRow, varCol;
	public double[][] simplexTable;
	public boolean[] varNotNegative;
	public int numOfX, numOfY;
	public boolean max;
	public boolean solved;
	
	public static void main(String[] args) throws FileNotFoundException {
		Simplex s = new Simplex();
		
		if (args.length > 0) {
			s.readCSV(args[0]);
			//s.readCSV("ScriptExample.csv");
			//s.readCSV("BasicExample.csv");
			//s.readCSV("ZweiSaefte.csv");
			//s.readCSV("InfiniteSolutions.csv");
			//s.readCSV("Eisenbahnproblem.csv");
			//s.readCSV("NegSchlupf.csv");
			s.readCSV("NichtNegativitaet_1.csv");
			//s.readCSV("NichtNegativitaet_2.csv");
			s.printTable();
			System.out.println("-------------------");
			
			//s.zweiPhasenMethode();
			s.run();
			
		} else {
			System.out.println("Bitte den Namen der einzulesenden CSV Datei als ersten Parameter angeben.");
			System.out.println("z.B. \"Simplex BasicExample.csv\"");
		}
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
			simplexTable = new double[numOfY+2][numOfX+2];

			varNotNegative = new boolean[numOfX];
			
			// initialise x1 ... xq
			for (int i = 0; i < numOfX; i++) {
				simplexTable[0][1+i] = i;
				varRow[i] = 0;
				varCol[i] = 1+i;
			}
			
			// initialise target function (z = b1 + b2 ... + bq)
			String[] line1 = lines.get(1).split(csvSplitBy);
			
			// min in max umwandeln indem gesamte Zielfunktion mit -1 multipliziert wird
			max = line1[0].equals("max");
			for (int i = 1; i <= numOfX+1; i++) {
				simplexTable[numOfY+1][i] = Double.parseDouble(line1[i]);
			}

			// DEBUG:
			printArray("z",simplexTable[numOfY+1]);
			
			if (!max) {
				for (int i = 1; i <= numOfX+1; i++) {
					simplexTable[numOfY+1][i] *= -1;
				}
			}
			
			String[] line2 = lines.get(2).split(csvSplitBy);

			for (int i = 0; i < numOfX; i++) {
				varNotNegative[i] = line2[i].trim().equals("true");
			}
					
			// read restrictions, Y rows so to say
			for (int i = 0; i < numOfY; i++) {
				//System.out.println(lines.get(3+i));
				String [] l = lines.get(3+i).split(csvSplitBy);
				
				varCol[numOfX+i] = 0;
				varRow[numOfX+i] = 1+i;
				simplexTable[i+1][0] = numOfX+i;
				
				// inequation to equation
				double[] eq = transformInequationToEquation(l);
				
				// fill simplex Table
				// TODO: notice if any c < 0
				
				boolean[] negativeC = new boolean[numOfY];
				
				for (int j = 0; j <= numOfX; j++) {
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
	 * normalize inequation to equation form
	 * 
	 * @param inequation	form { = | <= | >=, a1, a2, a3 ... an }
	 * @return equation
	 */
	public double[] transformInequationToEquation(String[] inequation) {
		double[] equation = new double[inequation.length-1];
		for (int i = 1; i < inequation.length; i++) {
			equation[i-1] = Double.parseDouble(inequation[i]);
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
	
	/***
	 * nicely print an arbitrary array with sysout
	 * @param name name of the array
	 * @param arr array
	 */
	public void printArray(String name, double[] arr) {
		System.out.print(name+": [");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]+", ");
		}
		System.out.println("]");
	}
	
	/***
	 * print the simplexTable with sysout
	 */
	public void printTable() {
		for (int i = 0; i < simplexTable.length; i++) {
			for (int j = 0; j < simplexTable[0].length; j++) {
				System.out.print(simplexTable[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	/***
	 * does the whole simplex, all in one
	 * and prints the final solutions
	 */
	public void run() {
		
		double[] originalZ = simplexTable[numOfY+1].clone();
		
		zweiPhasenMethode();
		solved = false;
		solve();
		printArray("results",getResults());
		System.out.println("target function: " +getTargetfunction());
		
		for (int i = 0; i < numOfX+1; i++) {
			
		}
	}
	public void solve() {
			
		// Satz von Bland
		int maxIterations = (int) choose(numOfX+numOfY, numOfY);
		System.out.println("max iterations: " + maxIterations);
				
		int iterations = 0;
		while (!solved) {
			solved = pivot();
			printTable();
			iterations++;
			if (iterations >= maxIterations) { 
				System.out.println("Will not terminate, execution stopped after "+ iterations +" iterations.");
				return;
			}
		}
	}
	
	private int getCRow() {
		int cRow = 0;
		int cmin = Integer.MAX_VALUE;
		// take the smallest !
		for (int i = 0; i < numOfY; i++) {
			if ((simplexTable[i+1][numOfX+1] < 0) && (simplexTable[i+1][numOfX+1] < cmin)){
				cRow = i+1;
			}
		}
		return cRow;
	}
	
	/***
	 * 
	 * @return true if all c > 0
	 */
	public boolean zweiPhasenMethode() {
		int cRow = getCRow();
		if (cRow == 0) {
			System.out.println("keine Zweiphasenmethode nötig");
			return true;
		}
			
		
		
		// TODO: simplexTable, varCol, varRow kopieren
		double[][] simplexTableBAK = simplexTable.clone();
		int[] varColBAK = varCol.clone();
		int[] varRowBAK = varRow.clone();
		int numOfXBAK = numOfX;
		// TODO: x0 einfügen, varCol, varRow erweitern
		numOfX++;
		varCol = new int[numOfX+numOfY];
		varRow = new int[numOfX+numOfY];
		varCol[0] = 1;
		varRow[0] = 0;
		for(int i = 1; i < varCol.length; i++) {
			varCol[i] = varColBAK[i-1];
			if (i<numOfX) varCol[i]++;
			varRow[i] = varRowBAK[i-1];
		}
		
		// create new simplexTable
		simplexTable = new double[numOfY+2][numOfX+2];
		for (int i = 0; i < numOfY+2; i++) {
			for (int j = 0; j < numOfX+2; j++) {

				// first row special
				if (i == 0) {
					if ((j > 1) && (j < numOfX+1)) {
						simplexTable[i][j] = j-1;
					}
				} else {
					if (j == 0)	simplexTable[i][j] = simplexTableBAK[i][j]+1;
					else if (j == 1) simplexTable[i][j] = 1;
					else simplexTable[i][j] = simplexTableBAK[i][j-1];
				}
			}
		}
		
		// TODO: Hilfszielfunktion einfügen
		for (int i = 0; i < numOfX+2; i++) {
			if (i == 1) simplexTable[numOfY+1][i] = -1;
			else simplexTable[numOfY+1][i] = 0;
		}
		
		System.out.println("after insert:");
		printTable();
		System.out.println();
		//printTable();
		

		// TODO: swap
		do { 			
			swap(cRow,1);
			System.out.println("after swap:");
			printTable();
			System.out.println();
			
			cRow = getCRow();
			
		} while (cRow != 0);

		
		
		solve();
		System.out.println("after solve:");
		printTable();
		System.out.println();
		
		// TODO: x0 entfernen
		if (varRow[0] != 0) System.err.println("x0 is no base variable!");
		else {
			
			
			numOfX = numOfXBAK;
			
			// create new simplexTable
			double[][] simplexTableNew = new double[numOfY+2][numOfX+2];
			for (int i = 0; i < numOfY+2; i++) {
				for (int j = 0; j < numOfX+2; j++) {
					if (j < varCol[0])	simplexTableNew[i][j] = simplexTable[i][j];
					else simplexTableNew[i][j] = simplexTable[i][j+1];
				}
			}

			// x1 becomes index 0 again
			for(int i = 0; i < varColBAK.length; i++) {
				varColBAK[i] = varCol[i+1];
				if (varColBAK[i] > varCol[0]) varColBAK[i]--;
				varRowBAK[i] = varRow[i+1];
				simplexTableNew[varRowBAK[i]][varColBAK[i]] = i;
			}
			varCol = varColBAK;
			varRow = varRowBAK;
			simplexTable = simplexTableNew;
//			System.out.println();
//			printTable();
			
			// TODO: Variablen in ursprüngliche Zielfunktion einsetzen
			
			int row = numOfY+1;
			simplexTable[row] = simplexTableBAK[row];
			double[] xEq = simplexTable[row-1];
			
//			System.out.println();
//			printTable();

			// TODO: probably is not always col 1
			double[] arr1 = multiply(xEq, simplexTable[row][1]);
			//DEBUG:
//			System.out.println(simplexTable[row][1]);
//			printArray("xEq ",xEq);
//			printArray("arr1 "+row,arr1);
			for (int j = 1; j < simplexTable[0].length; j++) {
				if (j == 1) simplexTable[row][j] = arr1[j];
				else simplexTable[row][j] += arr1[j];
			}
			
			System.out.println("after replacing:");
			printTable();
			System.out.println();
			

			solved = false;
		}
		
		return true;
	}
	
	/***
	 * Find pivot element and modify simplexTable accordingly
	 * 
	 * @return true when simplex finished
	 */
	public boolean pivot() {
		
		if (solved) {
			System.out.println("already solved");
			return true;
		}
		
		// get pivot element col
		int col = 1;
		while (simplexTable[numOfY+1][col] <= 0) {
			col++;
			if (col == simplexTable[0].length-1) {
				System.out.println("Finito!");
				return true;
			}
		}
		
		// get pivot element row (only consider a < 0) where abs(c/a) is the smallest
		double minVal = Double.MAX_VALUE;
		int row = 1;
		double[] q = new double[numOfY];
		for (int j = 0; j < numOfY; j++) {
			if (simplexTable[j+1][col] < 0) {
				q[j] =  Math.abs(simplexTable[j+1][numOfX+1] / simplexTable[j+1][col]);
				if (q[j]<minVal) {
					minVal = q[j];
					row = j+1;
				}
			}
		}
		
		if (minVal == Double.MAX_VALUE) {
			System.out.println("no pivot < 0 found - terminate");
			System.out.println("z tending to infinity");
			return true;
		}
		
		swap(row,col);
		
		return false;
		
	}
	
	/***
	 * swap around pivot at [row][col]
	 * @param row
	 * @param col
	 */
	public void swap(int row, int col) {
		System.out.println("Pivot-Element: " + row + "/" + col +": " + simplexTable[row][col]);
		// 1. Gleichung nach xn umformen 
		// -> alle a ausser an mit -1 multiplizieren
		// alle a durch an dividieren
		
		// temporary aray
		// xn Wert aus Array entfernen, yn an seiner Stelle platzieren
		// yn = x1 + x2 + x3 + c -> yn + x2 + x3 + c
		double[] xtemp = simplexTable[row].clone();
		double x = xtemp[col];
		System.out.println("x = "+x);
		
		// DEBUG:
		printArray("xtemp", xtemp);
		
		
		// TODO: fix this
		xtemp[col] = 1;
		double[] xEq = new double[numOfX+1];

		System.out.print("xEq = [");
		int signX = -1;
		int signY = 1;
		if (x < 0) {
			signX = 1;
			signY = -1;
		
		}			
		for (int i = 0; i < numOfX+1; i++) {
			if (i+1 == col) xEq[i] = xtemp[i+1] * signY;
			else xEq[i] = xtemp[i+1] * signX;
			xEq[i] /= Math.abs(x);
			System.out.print(xEq[i]+", ");
		}
		System.out.println("]");
		

		// 2. xn und yn tauschen
		int xnHead = (int) simplexTable[0][col];
		int ynHead = (int) simplexTable[row][0];
		simplexTable[0][col] = ynHead;
		varRow[ynHead] = 0;
		varCol[ynHead] = col;
		simplexTable[row][0] = xnHead;
		varRow[xnHead] = row;
		varCol[xnHead] = 0;
		
		
		// 3. xn in allen yn einsetzen
		for (int i = 1; i < simplexTable.length; i++) {
			if (i == row) {
				for (int j = 1; j < simplexTable[0].length; j++) {
					simplexTable[i][j] = xEq[j-1];
				}
			} else {
			
				double[] arr1 = multiply(xEq, simplexTable[i][col]);
				//DEBUG:
				//printArray("arr1 "+i,arr1);
				for (int j = 1; j < simplexTable[0].length; j++) {
					if (j == col) simplexTable[i][j] = arr1[j-1];
					else simplexTable[i][j] += arr1[j-1];
				}
				
			}
			
		}
	}
	
	/***
	 * get all x 
	 * @return
	 */
	public double[] getResults() {

		// check if solved
		if (!solved) {
			System.out.println("solve first!");
			return null;
		}

		// return ALL variables (x and y)
		double[] res = new double[numOfX+numOfY];
		for (int i = 0; i < numOfX+numOfY; i++){
			if (varCol[i] == 0) res[i] = simplexTable[varRow[i]][numOfX+1];
			else res[i]=0;
		}
		return res;
	}
	
	/***
	 * get target function with all header variables set to 0
	 * @return d
	 */
	public double getTargetfunction() {
		// check if solved
		if (!solved) {
			System.out.println("solve first!");
			return -1;
		}
		return simplexTable[numOfY+1][numOfX+1];
	}
	
	/***
	 * multiply each value of arr with scalar
	 * @param arr
	 * @param scalar
	 * @return
	 */
	public double[] multiply(double[] arr, double scalar) {
		double[] res = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i]*scalar;
		}
		return res;
	}
	
	/***
	 * summate two arrays per value
	 * @param arr1 array1
	 * @param arr2 array2
	 * @return
	 */
	public double[] summate(double[] arr1, double[] arr2) {
		
		double[] result = new double[arr1.length];
		for (int i = 0; i < result.length; i++){
			result[i] = arr1[i] + arr2[i];
		}
		
		return result;
	}

	/***
	 * n choose k
	 * source: http://stackoverflow.com/questions/1678690/what-is-a-good-way-to-implement-choose-notation-in-java
	 * 
	 * @param n
	 * @param k
	 * @return
	 */
	public static double choose(int n, int k) {
	    if (k < 0 || k > n) return 0;
	    if (k > n/2) {
	        // choose(n,k) == choose(n,n-k), 
	        // so this could save a little effort
	        k = n - k;
	    }

	    double denominator = 1.0, numerator = 1.0;
	    for (int i = 1; i <= k; i++) {
	        denominator *= i;
	        numerator *= (n + 1 - i);
	    }
	    return numerator / denominator;
	}
}
