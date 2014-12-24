package ch.fhnw.efalg.test;

import java.io.FileNotFoundException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ch.fhnw.efalg.Simplex;


public class SimplexTest {

	Simplex s;
	
	@Before
	public void setup() throws FileNotFoundException {
		s = new Simplex();
		s.readCSV("BasicExample.csv");
	}
	
	@Test
	public void testReadCSVVariables()  {
		Assert.assertEquals(s.numOfX, 2);
		Assert.assertEquals(s.numOfY, 3);
		Assert.assertEquals(s.max, true);
	}
	
	@Test
	public void testReadCSVArrays()  {
		
		
	}
	
	@Test
	public void testInequationToEquation() {
		
		// <= to Equation
		String[] ineq1 = { "<=", "40", "120", "2400" };
		int[] eq1 = {-40, -120, 2400 };
		
		int[] eq1Computed = s.transformInequationToEquation(ineq1);
		for (int i = 0; i < eq1.length; i++) {
				Assert.assertEquals(eq1[i], eq1Computed[i]);
		}
		
		// >= to Equation
		String[] ineq2 = { ">=", "40", "120", "2400" };
		int[] eq2 = {40, 120, -2400 };
		
		int[] eq2Computed = s.transformInequationToEquation(ineq2);
		for (int i = 0; i < eq2.length; i++) {
				Assert.assertEquals(eq2[i], eq2Computed[i]);
		}
		
		// = to Equation
		String[] ineq3 = { "=", "40", "120", "2400" };
		int[] eq3 = {40, 120, 2400 };
	
		int[] eq3Computed = s.transformInequationToEquation(ineq3);
		for (int i = 0; i < eq3.length; i++) {
				Assert.assertEquals(eq3[i], eq3Computed[i]);
		}
	}
	
	@Test
	public void testReadCSVSimplexTable()  {
				
		int[][] compArray = {
				
				{ 0, 0, 1, 0 },
				{ 2, -2, -1, 100},
				{ 3, -1, -1, 80},
				{ 4, -1, -0, 40},
				{ 0, 3, 2, 0}	
				
		};
		
		Assert.assertEquals(compArray.length, s.simplexTable.length);
		Assert.assertEquals(compArray[0].length, s.simplexTable[0].length);
		
		for (int i = 0; i < compArray.length; i++) {
			for (int j = 0; j < compArray[0].length; j++) {
				Assert.assertEquals(compArray[i][j], s.simplexTable[i][j]);
			}
		}
	}

}
