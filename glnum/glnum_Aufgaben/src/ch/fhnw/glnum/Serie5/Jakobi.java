package ch.fhnw.glnum.Serie5;

import ch.fhnw.glnum.InOut;

/** 
 *    @author:    Manfred Vogel
 *    @date:      21. March 201
 *    @filename:  Jakobi
 *
 *    Grundlagen der Numerik /  Übung 5
 *
 * 
 */

public class Jakobi {

  static int maxCount = 25;
  static double eps = 1.0e-6;

  public static void main(String[] args) {
/*
    int n = 3;
    double[][] A = { { 3 , -1 , 1 },
                     { 3 ,  6 , 2 },
                     { 3 ,  3 , 7 }};
             
    double[] b   =   { 1 , 0 , 4 };
  /*
    int n = 4;
    double[][] A = { { 10 , 1 , -2 , 0 },
                     {  1 , 10, -1 , 3 },
                     { -2 ,-1 ,  8 ,-1 },
                     {  0 , 3 , -1 , 5 }};
    double[] b   =   {  6 , 25 , -11 , -11 };
    
  /*/
    int n = 4;
    double[][] A = { {  2 , 1 , -1 , 1 },
                     {  1 ,-2 ,  1 ,-2 },
                     {  2 , 2 ,  5 ,-1 },
                     {  1 ,-1 ,  1 , 4 }};
    double[] b   =   {  4 , 5 ,  6 , 7 };
    
/*      
    int n = 6;
    double[][] A = { { 4 , -1 , 0 , -1 , 0 , 0 },
                     { -1,  4 , -1,  0 ,-1 , 0 },
                     { 0 , -1 , 4 ,  0 , 0 , -1},
                     { -1,  0 , 0 ,  4 ,-1 , 0 },
                     { 0 , -1 , 0 , -1 , 4 , -1},
                     { 0 ,  0 , -1,  0 ,-1 , 4 }};
    double[] b   =   { 0 ,  5 , 0 ,  6 ,-2 , 6 };
*/

    double[][] T = new double [n][n];
    double[] xOld= new double [n];
    double[] x   = new double [n];
    
    for (int i = 0; i < n ; i++) {
      for (int k = 0; k < n ; k++)  T[i][k] = (-1.0)*A[i][k]/A[i][i];
      T[i][i]=0.0;
      b[i]=b[i]/A[i][i];
      xOld[i]=b[i];
    }
    
    int count=0;
    double maxi;
    System.out.println();
    do  {
      count++;
      maxi=0;
      for (int i = 0; i < n ; i++) {
        x[i] = b[i];
        for (int k = 0; k < n ; k++) x[i] +=T[i][k]*xOld[k];
      }
      
      for (int i = 0; i < n ; i++) {
        maxi=Math.max(maxi, Math.abs(xOld[i]-x[i]) );
        xOld[i]=x[i];
      }

      InOut.print(count, 6, 0);
      System.out.print(" Iteration :");
      printVector(xOld);
    } while(count < maxCount &&  maxi > eps  );

    System.out.println();
    if(count >= maxCount)
    System.out.println("        ******* Jacobi NOT converged *******");
  }
  
  static void printVector(double[] vec) {
    for (int i = 0; i < vec.length; i++) {
      InOut.print(vec[i], 6, 7);
      System.out.print("  ");
    }
    System.out.print("\n");
  }

}
