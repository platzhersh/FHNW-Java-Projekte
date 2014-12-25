package ch.fhnw.efalg.test;

import java.io.FileNotFoundException;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import ch.fhnw.efalg.Simplex;


public class SimplexTest {

	Simplex s1, s2, s3, s4;
	double epsilon = 0.0000001;
	
	@Before
	public void setup() throws FileNotFoundException {
		s1 = new Simplex();
		s1.readCSV("BasicExample.csv");
		s2 = new Simplex();
		s2.readCSV("InfiniteSolutions.csv");
		s3 = new Simplex();
		s3.readCSV("NegSchlupf.csv");
		s4 = new Simplex();
		s4.readCSV("ScriptExample.csv");
	}
	
	@Test
	public void testReadCSVVariables()  {
		Assert.assertEquals(s1.numOfX, 2);
		Assert.assertEquals(s1.numOfY, 3);
		Assert.assertEquals(s1.max, true);
	}
	
	@Test
	public void testReadCSVArrays()  {
		
		
	}
	
	@Test
	public void testInequationToEquation() {
		
		// <= to Equation
		String[] ineq1 = { "<=", "40", "120", "2400" };
		double[] eq1 = {-40, -120, 2400 };
		
		double[] eq1Computed = s1.transformInequationToEquation(ineq1);
		for (int i = 0; i < eq1.length; i++) {
				Assert.assertEquals(eq1[i], eq1Computed[i], epsilon);
		}
		
		// >= to Equation
		String[] ineq2 = { ">=", "40", "120", "2400" };
		double[] eq2 = {40, 120, -2400 };
		
		double[] eq2Computed = s1.transformInequationToEquation(ineq2);
		for (int i = 0; i < eq2.length; i++) {
				Assert.assertEquals(eq2[i], eq2Computed[i], epsilon);
		}
		
		// = to Equation
		String[] ineq3 = { "=", "40", "120", "2400" };
		double[] eq3 = {40, 120, 2400 };
	
		double[] eq3Computed = s1.transformInequationToEquation(ineq3);
		for (int i = 0; i < eq3.length; i++) {
				Assert.assertEquals(eq3[i], eq3Computed[i], epsilon);
		}
	}
	
	@Test
	public void testReadCSV_BasicExample()  {
				
		double[][] compArray1 = {
				
				{ 0.0, 0.0, 1.0, 0.0 },
				{ 2.0, -2.0, -1.0, 100.0},
				{ 3.0, -1.0, -1.0, 80.0},
				{ 4.0, -1.0, -0.0, 40.0},
				{ 0.0, 3.0, 2.0, 0.0}	
				
		};
		
		Assert.assertEquals(compArray1.length, s1.simplexTable.length);
		Assert.assertEquals(compArray1[0].length, s1.simplexTable[0].length);
		
		for (int i = 0; i < compArray1.length; i++) {
			for (int j = 0; j < compArray1[0].length; j++) {
				Assert.assertEquals(compArray1[i][j], s1.simplexTable[i][j], epsilon);
			}
		}
	}
	
	@Test
	public void testReadCSV_InfiniteSolutions() {
		double[][] compArray = {
						
						{ 0.0, 0.0, 1.0, 0.0 },
						{ 2.0, -2.0, -1.0, 100.0},
						{ 3.0, -1.0, -1.0, 80.0},
						{ 4.0, -1.0, -0.0, 40.0},
						{ 0.0, 2.0, 1.0, 0.0}	
						
		};
		
		Assert.assertEquals(compArray.length, s2.simplexTable.length);
		Assert.assertEquals(compArray[0].length, s2.simplexTable[0].length);
		
		for (int i = 0; i < compArray.length; i++) {
			for (int j = 0; j < compArray[0].length; j++) {
				Assert.assertEquals(compArray[i][j], s2.simplexTable[i][j], epsilon);
			}
		}
	}
	
	@Test
	public void testReadCSV_NegSchlupf() {
		double[][] compArray = {
						
						{ 0.0, 0.0, 1.0, 0.0 },
						{ 2.0, 2.0, 1.0, -12.0},
						{ 3.0, 1.0, 2.0, -12.0},
						{ 4.0, 1.0, 1.0, -10.0},
						{ 5.0, -3.0, -4.0, 60.0},
						{ 0.0, -11.0, -8.0, 0.0}	
						
		};
		
		Assert.assertEquals(compArray.length, s3.simplexTable.length);
		Assert.assertEquals(compArray[0].length, s3.simplexTable[0].length);
		
		for (int i = 0; i < compArray.length; i++) {
			for (int j = 0; j < compArray[0].length; j++) {
				Assert.assertEquals(compArray[i][j], s3.simplexTable[i][j], epsilon);
			}
		}
	}
	
	@Test
	public void testReadCSV_ScriptExample() {
		double[][] compArray = {
						
				{ 0.0, 0.0, 1.0, 0.0 },
				{ 2.0, -1.0, -1.0, 40.0 },
				{ 3.0, -40, -120, 2400 },
				{ 4.0, -7.0, -12.0, 312 },
				{ 0.0, 100.0, 250.0, 0.0 }	
						
		};
		
		Assert.assertEquals(compArray.length, s4.simplexTable.length);
		Assert.assertEquals(compArray[0].length, s4.simplexTable[0].length);
		
		for (int i = 0; i < compArray.length; i++) {
			for (int j = 0; j < compArray[0].length; j++) {
				Assert.assertEquals(compArray[i][j], s4.simplexTable[i][j], epsilon);
			}
		}
	}
	
	@Test
	public void testMultiply() {
		double[] vals = { 1, 2, 3};
		double[] resExp = { 2, 4, 6};
		double[] resEff = s4.multiply(vals, 2);
		
		for (int i = 0; i < vals.length; i++) {
			Assert.assertEquals(resExp[i], resEff[i], epsilon);
		}
	}
	
	@Test
	public void testSummate() {
		double[] vals1 = { 1, 2, 3};
		double[] vals2 = { 3, 2, 1};
		double[] resExp = { 4, 4, 4};
		double[] resEff = s4.summate(vals1, vals2);
		
		for (int i = 0; i < resEff.length; i++) {
			Assert.assertEquals(resExp[i], resEff[i], epsilon);
		}
	}
	
	@Test
	public void testPivot() {
		// Beispiel aus dem Skript
		
		double[][] simplexExpectedAfterPivot = {
				{ 0.0, 2.0, 1.0, 0.0 },
				{ 0.0, -1.0, -1.0, 40.0 },
				{ 3.0, 40, -80, 800.0 },
				{ 4.0, 7.0, -5.0, 32.0 },
				{ 0.0, -100.0, 150.0, 4000.0 }
		};
		s4.printTable();
		s4.pivot();
		s4.printTable();
		for (int i = 0; i < simplexExpectedAfterPivot.length; i++) {
			for (int j = 0; j < simplexExpectedAfterPivot[0].length; j++) {
				Assert.assertEquals(simplexExpectedAfterPivot[i][j], s4.simplexTable[i][j], epsilon);
			}
		}
		
	}

}
