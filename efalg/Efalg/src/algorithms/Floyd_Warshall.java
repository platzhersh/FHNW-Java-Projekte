package algorithms;
/**
 *    @author:    Manfred Vogel
 *    @date:      October 2014
 *    @filename:  Floyd-Warshall
 *
 *    Effiziente Algorithmen HS2014
 *        
 *    all pairs shortest paths Floyd-Warshall Algorithmus
 */

class Floyd_Warshall {
  
  static final int n = 12;            // Anzahl Knoten
  static final int max = 100;         // Unendlich
                                      // Adjazenz-Matrix
  static int[][] E = { { 0, 3, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                       { 3, 0, 1, 0, 0, 2, 4, 0, 0, 0, 0, 0},
                       { 0, 1, 0, 1, 0, 0, 3, 1, 0, 0, 0, 0},
                       { 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0},
                       { 1, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 0},
                       { 0, 2, 0, 0, 1, 0, 6, 0, 0, 4, 0, 0},
                       { 0, 4, 3, 0, 0, 6, 0, 1, 0, 2, 1, 0},
                       { 0, 0, 1, 2, 0, 0, 1, 0, 0, 0, 0, 2},
                       { 0, 0, 0, 0, 2, 0, 0, 0, 0, 4, 0, 0},
                       { 0, 0, 0, 0, 0, 4, 2, 0, 4, 0, 2, 0},
                       { 0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 3},
                       { 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 3, 0} };

  /*
  static int[][] E = { { 0, 6, 3, 0, 0, 4, 0, 0, 0},
                       { 6, 0, 5, 4, 3, 0, 0, 0,10},
                       { 3, 5, 0, 8, 0, 0, 5, 0, 0},
                       { 0, 4, 8, 0, 2, 0, 0, 3, 0},
                       { 0, 3, 0, 2, 0, 0, 0, 0, 8},
                       { 4, 0, 0, 0, 0, 0, 4, 0, 0},
                       { 0, 0, 5, 0, 0, 4, 0, 6, 0},
                       { 0, 0, 0, 3, 0, 0, 6, 0, 2},
                       { 0,10, 0, 0, 8, 0, 0, 2, 0} };
  */

  public static void main(String[] args) {
    int[][]  A = new int[n][n];         //  Hilfsmatrix
  
    for (int i = 0; i<n; i++)           //  Adjazenz-Matrix 
      for (int k = 0; k<n; k++)         //  vorbereiten
        if (E[i][k] == 0 ) E[i][k] = max; 
      
    for (int i = 0; i<n; i++)         //  Hilfs-Matrix
        for (int k = 0; k<n; k++)     //  vorbereiten  
        {A[i][k] = E[i][k];
          if ( i == k ) A[i][k] = 0; 
        }
//  printMatrix(A); 
     
    for (int k = 0; k<n; k++)  {        //  Loop über alle Rest-Knoten
      for (int i = 0; i<n; i++)         //  Loop über alle bisherigen Knoten
        for (int j = 0; j<n; j++) 
          E[i][j] = Math.min(A[i][j], A[i][k]+A[k][j]);
      
      for (int i = 0; i<n; i++)         //  Copy matrix
        for (int j = 0; j<n; j++)  A[i][j] =E[i][j];     //
        printMatrix(A);
    }
    printMatrix(A);
  }
  
  
  static void printMatrix(int[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) { 
        InOut.print(matrix[i][j], 4);
        System.out.print("  ");
      }
      System.out.print("\n");
    }
    System.out.print("\n");
    System.out.print("\n");
  }
  
  
}
  