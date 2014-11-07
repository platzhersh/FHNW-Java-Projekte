package algorithms;
/**
 *    @author:    Manfred Vogel
 *    @date:      October 2014
 *    @filename:  Dijkstra
 *
 *    Effiziente Algorithmen HS2014
 *        
 *    source-sink shortest path Dijkstra Algorithmus
 */

class Dijkstra {
  
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
//*/
/*    static int[][] E = { { 0, 6, 3, 0, 0, 4, 0, 0, 0},
                   { 6, 0, 5, 4, 3, 0, 0, 0,10},
                   { 3, 5, 0, 8, 0, 0, 5, 0, 0},      
                   { 0, 4, 8, 0, 2, 0, 0, 3, 0},
                   { 0, 3, 0, 2, 0, 0, 0, 0, 8},
                   { 4, 0, 0, 0, 0, 0, 4, 0, 0},          
                   { 0, 0, 5, 0, 0, 4, 0, 6, 0},
                   { 0, 0, 0, 3, 0, 0, 6, 0, 2},
                   { 0,10, 0, 0, 8, 0, 0, 2, 0} };                      
//*/

  public static void main(String[] args) {
    int[]  V = new int[n];              //  Knotenvektor
    int[]  L = new int[n];              //  Weglängen
    int Omin, Vmin;                     //  Hilfsgrössen
  
    for (int i = 0; i<n; i++) {         //  Adjazenz-Matrix und
      V[i] = L[i] = max;                //  Knotenvektor
      for (int k = 0; k<n; k++)         //  vorbereiten
        if (E[i][k] == 0 ) E[i][k] = max;
    }

    V[0]=4;                             //  Startknoten wählen
    L[0]=0;                             //
    for (int i = 1; i<n; i++) {         //  Loop über alle Knoten
      Omin = Vmin = max;
      for (int j = 0; j<i; j++) {       //  Loop über alle Knoten in V
        for (int m = 0; m<n; m++) {     //  alle Knoten nicht in V
          if ( E[V[j]][m] + L[j] < Omin ) {
            Omin = E[V[j]][m] + L[j];
            Vmin = m;
          }
        }
      }
      V[i] = Vmin;
      L[i] = Omin;
      for (int j = 0; j<i; j++) E[V[j]][Vmin] = E[Vmin][V[j]] = max;   //  Kanten löschen
    }
    
    System.out.println(" Weglängen vom Startknoten "+V[0]);     
    for (int j = 1; j<n; j++) 
      System.out.println("                zum Knoten "+V[j]+" : "+L[j]);
  }
}
  