package ch.fhnw.glnum.Serie03;

import ch.fhnw.glnum.InOut;
/**

 *    @author:    Manfred Vogel
 *    @date:      March 2014
 *
 *           Numerik FS14
 *
 *  -------  Programm LU-Decomposition               ----------------
 *  -------  Uebung 3  Aufgabe 3.)                   ----------------
 *
 */


/**
 * Hinweis : Mit der Verwendung der Indices i-1 etc. in den Matrizen kann
 *           der Pseudo-Code für den Algorithmus direkt übernommen werden !
 */
 
public class LU {

  static int n = 4;
  static double[][] L = new double[n][n];
  static double[][] U = new double[n][n];

  public static void main(String[] args) {
      
    double[][] A = {
			{4,8,12,16},
			{-2,-1,0,1},
			{6,11,18,25},
			{5,12,23,35}				
	};
    
    printMatrix(A);

    double sum;

    U[0][0] = 1.0;
    L[0][0] = A[0][0];
    for (int j = 2; j <= n; j++) {
      L[j-1][0] = A[j-1][0];
      U[0][j-1] = A[0][j-1] / L[0][0];
      U[j-1][j-1] = 1.0;
    }
    
    for (int i = 2; i <= n - 1; i++) {
      sum = 0;
      for (int k = 1; k <= i - 1; k++) sum += L[i-1][k-1] * U[k-1][i-1];
      L[i-1][i-1] = A[i-1][i-1] - sum;
      for (int j = i + 1; j <= n; j++) {
        sum = 0;
        for (int k = 1; k <= i - 1; k++) sum += L[j-1][k-1] * U[k-1][i-1];

        L[j-1][i-1] = A[j-1][i-1] - sum;
        sum = 0;
        for (int k = 1; k <= i - 1; k++) sum += L[i-1][k-1] * U[k-1][j-1];
        U[i-1][j-1] = 1.0/L[i-1][i-1] *( A[i-1][j-1] - sum);
      }
    }
    sum=0;
    for (int k=1; k<=n-1; k++)  sum += L[n-1][k-1] * U[k-1][n-1];
    L[n-1][n-1]=A[n-1][n-1]-sum;

    printMatrix(L);
    printMatrix(U);
  }
  
  static void printMatrix(double[][] matrix) {     //  Ausgabe-Routine für Matrizen 
    System.out.print("\n");
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) { 
        InOut.print(matrix[i][j], 5, 3);
        System.out.print("  ");
      }
      System.out.print("\n");
    }
    System.out.print("\n");
  }
}
