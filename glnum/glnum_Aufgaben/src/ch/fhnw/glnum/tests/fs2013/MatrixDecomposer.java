/**
 * 
 * Autor:	Christian Glatthard
 * Schule:	FHNW Informatik
 * Fach:	glnum, FS 2014
 * 
 * http://platzh1rsch.ch
 * 
 * Helps you to complete "decompose-matrices" F, G of A
 * 
 * A = F * G
 * 
 * Mark all unknown elements in F and G as Double.POSITIVE_INFINITY.
 * 
 * Have fun.
 * 
 */

package ch.fhnw.glnum.tests.fs2013;

import java.util.ArrayList;
import java.util.List;

import org.la4j.matrix.Matrix;

public class MatrixDecomposer {
	
	private Matrix m_a;	// Result Matrix when m_a = m_f * m_g
	private Matrix m_f;	// mark unknowns as Double.POSITIVE_INFINITY
	private Matrix m_g;	// mark unknowns as Double.POSITIVE_INFINITY
	private List<Equation> eqList;
	
	private int countf;
	private int countg;
	
	private boolean verbose;
	
	public class Equation {
		private Matrix m_f, m_g;
		public double result;
		private List<Tupel> tupels;
		
		
		public Equation(Matrix f, Matrix g) {
			m_f = f; m_g = g;
			tupels = new ArrayList();
		}
		
		public void addTupel(int fx, int fy, int gx, int gy){
			tupels.add(new Tupel(fx,fy,gx,gy));
		}		
		public void setResult(double r) {
			result = r;
		}
		public int countUnknowns() {
			int count = 0;
			for (Tupel t : tupels) {
				if (m_f.get(t.fx,t.fy) == Double.POSITIVE_INFINITY) count++;
				if (m_g.get(t.gx,t.gy) == Double.POSITIVE_INFINITY) count++;
			}
			return count;
		}
		/* Ignore Unknowns in Tuples together with 0 */
		public int countRelevantUnknowns() {
			int count = 0;
			for (Tupel t : tupels) {
				if (m_f.get(t.fx,t.fy) == Double.POSITIVE_INFINITY && m_g.get(t.gx,t.gy) != 0) count++;
				if (m_g.get(t.gx,t.gy) == Double.POSITIVE_INFINITY && m_f.get(t.fx,t.fy) != 0) count++;
			}
			return count;
		}
		
		public String toString() {
			String r = result + " = ";
			for (Tupel t : tupels) {
				r += " + (";
				r += m_f.get(t.fx, t.fy) == Double.POSITIVE_INFINITY ? "f("+t.fx+", "+t.fy+")" : m_f.get(t.fx, t.fy);
				r += "*";
				r += m_g.get(t.fx, t.fy) == Double.POSITIVE_INFINITY ? "g("+t.fx+", "+t.fy+")" : m_g.get(t.fx, t.fy);
				r += ")";
			}
			return r;
		}
		
		/* Tupel Class */
		private class Tupel {
			private int fx, fy;	// Indexes of f
			private int gx, gy;	// Indexes of g
			
			public Tupel(int fx, int fy, int gx, int gy) {
				this.fx = fx;
				this.fy = fy;
				this.gx = gx;
				this.gy = gy;
				
			}
		}
	}
	
	public void verbose() {
		verbose = true;
	}
	public void unverbose() {
		verbose = false;
	}
	
	public MatrixDecomposer(Matrix a, Matrix f, Matrix g) {
		m_a = a;
		m_f = f;
		m_g = g;
		eqList = new ArrayList();
	}
	
	public Matrix[] decompose() {
		
		if (m_a.columns() != m_f.rows() || m_a.columns() != m_g.columns()) 
			throw new IllegalArgumentException("Matrix Dimensions do not match");
		
		countf = countUnknownElements(m_f);
		countg = countUnknownElements(m_g);
		
		
		/* Get all Equations */
		
		for (int i = 0; i < m_a.rows(); i++) {
			for (int j = 0; j < m_a.columns(); j++) {
				
				double result = m_a.get(i, j);
				Equation e = new Equation(m_f,m_g);
				e.setResult(result);
				
				for (int k = 0; k < m_f.rows(); k++) {
					e.addTupel(i, k, k, j);
				}
				//System.out.println(e.result + ", hat "+e.countUnknowns() + " Unbekannte, davon "+e.countRelevantUnknowns()+" relevant.");
				eqList.add(e);
			}
		}
		
		
		while (eqList.size() > 0) {
			int i = 10;
			Equation nextEq = null;
			for (Equation e : eqList) {
				if (e.countRelevantUnknowns() == 1) {
					nextEq = e; 
					break;
				} else if (e.countRelevantUnknowns() < i) {
					i = e.countRelevantUnknowns();
					nextEq = e;
				}
			}
			
			if (nextEq.countRelevantUnknowns() == 1) {
				solveEq(nextEq);
				eqList.remove(nextEq);
				analyzeEquations();
			} else if (nextEq.countRelevantUnknowns() == 2) {
				
				System.out.println("Kleinste Anzahl an Unbekannten pro Gleichung ist 2, Plan B ist angesagt.");
				
				
				
				break;
				
			} else {
				System.out.println("You are screwed. ("+nextEq.countRelevantUnknowns()+").");
				break;
			}
		}
		
		return new Matrix[] {m_f,m_g};
	}
	
	public int getNumberOfUnknowns() {
		return countf + countg;
	}
	public int getNumberOfUnknownsF(){
		return countf;
	}
public int getNumberOfUnknownsG(){
		return countg;
	}
	
	private boolean solveEq(Equation e) {
		double r = e.result;
		Equation.Tupel ut = null;	// Tupel containing the unknown
		
		for (Equation.Tupel t : e.tupels) {
			if ((m_f.get(t.fx, t.fy) == Double.POSITIVE_INFINITY && m_g.get(t.gx,t.gy) != 0)
					|| (m_g.get(t.gx, t.gy) == Double.POSITIVE_INFINITY && m_f.get(t.fx,t.fy) != 0)) {
				ut = t;
			} else {
				if (!(m_f.get(t.fx, t.fy) == Double.POSITIVE_INFINITY) && !(m_g.get(t.gx, t.gy) == Double.POSITIVE_INFINITY))
				r -= m_f.get(t.fx, t.fy)*m_g.get(t.gx, t.gy);
			}
		}
		if (ut == null) System.out.println("No unknown element found for "+e.result);
		else {
			r /= m_f.get(ut.fx, ut.fy) == Double.POSITIVE_INFINITY ? m_g.get(ut.gx, ut.gy) : m_f.get(ut.fx, ut.fy);
			Matrix target = m_f.get(ut.fx, ut.fy) == Double.POSITIVE_INFINITY ? m_f : m_g;
			int tx = m_f.get(ut.fx, ut.fy) == Double.POSITIVE_INFINITY ? ut.fx : ut.gx;
			int ty = m_f.get(ut.fx, ut.fy) == Double.POSITIVE_INFINITY ? ut.fy : ut.gy;
			
			target.set(tx, ty, r);
			
			if (target == m_f) countf--;
			if (target == m_g) countf--;
		}
		
		if (verbose) System.out.println("Resultat für "+e.result+" ist "+r);
		//System.out.println("  "+eqList.size());
		return true;
	}
	
	private static int countUnknownElements(Matrix m) {
		
		int count = 0;
		
		for (int i = 0; i < m.rows(); i++) {
			for (int j = 0; j < m.columns(); j++) {
				if (m.get(i, j) == Double.POSITIVE_INFINITY) count++;
			}
		}
		
		return count;
	}
	
	public void analyzeEquations() {
		for (Equation e : eqList) {
			if (verbose)System.out.println(e.result + ", hat "+e.countUnknowns() + " Unbekannte, davon "+e.countRelevantUnknowns()+" relevant.");
		}
		if (verbose) System.out.println("---------------------------------");
	}
}
