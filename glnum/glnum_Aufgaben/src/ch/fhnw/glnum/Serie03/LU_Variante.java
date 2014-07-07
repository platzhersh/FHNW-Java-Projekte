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
 *           der Pseudo-Code f�r den Algorithmus direkt �bernommen werden !
 */

/**
 * Variante : Bei dieser Variante der LU-Zerlegung werden die beiden 
 *            resultierenden Dreiecksmatrizen direkt in die urspr�ngliche
 *            Matrix A zur�ckgeschrieben. Dabei werden die Elemente Uii = 1 
 *            als bekannt vorausgesetzt und nicht abgespeichert.
 */  
  
public class LU_Variante {

  static int n = 4;

  public static void main(String[] args) {

    double[][] A = { { 9 , 45 , -9 , -18},
                     {45 ,234 ,-48 , -93},
                     {-9 ,-48 , 19 ,  19},
                     {-18,-93 , 19 ,  46} };
    
    printMatrix(A);

    double sum;

    for (int j = 2; j <= n; j++) A[0][j-1] = A[0][j-1] / A[0][0];
    
    for (int i = 2; i <= n - 1; i++) {
      sum = 0;
      for (int k = 1; k <= i - 1; k++) sum += A[i-1][k-1] * A[k-1][i-1];
      A[i-1][i-1] -= sum;
      for (int j = i + 1; j <= n; j++) {
        sum = 0;
        for (int k = 1; k <= i - 1; k++) sum += A[j-1][k-1] * A[k-1][i-1];

        A[j-1][i-1] -= sum;
        sum = 0;
        for (int k = 1; k <= i - 1; k++) sum += A[i-1][k-1] * A[k-1][j-1];
        A[i-1][j-1] = 1.0/A[i-1][i-1] *( A[i-1][j-1] - sum);
      }
    }
    sum=0;
    for (int k=1; k<=n-1; k++)  sum += A[n-1][k-1] * A[k-1][n-1];
    A[n-1][n-1] -= sum;

    printMatrix(A);
  }
  
  static void printMatrix(double[][] matrix) {     //  Ausgabe-Routine f�r Matrizen 
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
