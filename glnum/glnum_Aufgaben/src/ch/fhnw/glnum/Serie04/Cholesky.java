package ch.fhnw.glnum.Serie04;

import ch.fhnw.glnum.InOut;
/**

 *    @author:      Manfred Vogel
 *    @date:        14. M�rz 2014
 *    @filename:    Cholesky
 *
 *    Grundlagen der Numerik /  �bung 4
 *
 *
 */

/**
 * Hinweis : Mit der Verwendung der Indices i-1 etc. in den Matrizen kann
 *           der Pseudo-Code f�r den Algorithmus direkt �bernommen werden !
 */

public class Cholesky {

  static int n = 4;
  static double[][] L = new double[n][n];

  public static void main(String[] args) {
      
    double[][] A = { { 9 , 45 , -9 , -18},
                     {45 ,234 ,-48 , -93},
                     {-9 ,-48 , 19 ,  19},
                     {-18,-93 , 19 ,  46}};
    
    printMatrix(A);

    double sum;

    L[0][0] = Math.sqrt(A[0][0]);
    for (int j = 2; j <= n; j++) L[j-1][0] = A[j-1][0] / L[0][0];
    for (int i = 2; i <= n - 1; i++) {
      sum = 0;
      for (int k = 1; k <= i - 1; k++) sum += L[i-1][k-1] * L[i-1][k-1];
      L[i-1][i-1] = Math.sqrt(A[i-1][i-1] - sum);
      for (int j = i + 1; j <= n; j++) {
        sum = 0;
        for (int k = 1; k <= i - 1; k++) sum += L[j-1][k-1] * L[i-1][k-1];
        L[j-1][i-1] = 1.0 / L[i-1][i-1] * (A[j-1][i-1] - sum);
      }
    }
    sum=0;
    for (int k=1; k<=n-1; k++)  sum += L[n-1][k-1] * L[n-1][k-1];
    L[n-1][n-1]=Math.sqrt(A[n-1][n-1]-sum);

    printMatrix(L);
  }
  
  static void printMatrix(double[][] matrix) {
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
