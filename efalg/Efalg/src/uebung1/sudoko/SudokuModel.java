package uebung1.sudoko;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

public class SudokuModel {


	int[][] fields;
	int size;
	
	
	/* Konstruktor */
	public SudokuModel(int size) {
		
		if (size % 3 != 0) throw new IllegalArgumentException("Size has to be a multiple of 3.");
		fields = new int[size][size];
		this.size = size;
	}
	
	/* Getter & Setter */
	public int get(int i, int j) {
		return fields[i][j];
	}
	public void set(int i, int j, int v) {
		fields[i][j] = v;
	}
	public String getText(int i, int j) {
		String s = fields[i][j] > 0 ? Integer.toString(fields[i][j]) : ""; 
		return s;
	}
	public int getSize() {
		return this.size;
	}
	
	public int getAreaCount(){
		return size/3;
	}
	
	public int getAreaCoord(int i) {
		return i / size;
	}
	
	public List<Integer> getAreaUsedInts(int i, int j){
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int k = getAreaCoord(i); k < getAreaCoord(i+1); k++) {
			for (int l = getAreaCoord(i); l < getAreaCoord(i+1); l++) {
				if (fields[k][l] != 0) list.add(fields[k][l]);
			}
		}
		return list;
		
	}
	
	public List<Integer> getRowUsedInits(int i, int j) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int k = getAreaCoord(i); k < getAreaCoord(i+1); k++) {
				if (fields[k][j] != 0) list.add(fields[k][j]);
		}
		return list;
	}
	
	public List<Integer> getColumnUsedInits(int i, int j) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int k = getAreaCoord(j); k < getAreaCoord(j+1); k++) {
				if (fields[i][k] != 0) list.add(fields[i][k]);
		}
		return list;
	}
	
}
