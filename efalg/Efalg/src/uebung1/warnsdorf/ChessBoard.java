package uebung1.warnsdorf;

import java.util.ArrayList;
import java.util.List;

/***
 * Collection of ChessBoardFields representing a Chessboard of individual (square) dimensions.
 * @author chregi
 *
 */
public class ChessBoard {

	public int size;
	ChessBoardField[][] fields;
	
	/* Konstruktoren */
	
	public ChessBoard(int size) {
		this.size = size;
		fields = new ChessBoardField[size][size];
	}
	
	/* Getter & Setter */
	
	public ChessBoardField get(int i, int j) {
		return fields[i][j];
	}
	
	public void set(int i, int j, int value) {
		if (null == fields[i][j]) {
			fields[i][j] = new ChessBoardField(i,j,value);
		}
		else {
			fields[i][j].setCount(value);
		}
	}
	
	public void set(ChessBoardField field) {
		fields[field.i][field.j] = field; 
	}
	
	/***
	 * Returns all neighbors
	 * @param i x-coordinate
	 * @param j y-coordinate
	 * @return list of neighbors
	 */
	public List<ChessBoardField> getNeighbors(int i, int j) {
		List<ChessBoardField> neighbors = new ArrayList<ChessBoardField>();
		
		if (i-2 >= 0) {
			if (j-1 >= 0 )  {
				neighbors.add(fields[i-2][j-1]);
			}
			if (j+1 < size) {
				neighbors.add(fields[i-2][j+1]);
			}
		}
		if (i+2 < size) {
			if (j-1 >= 0) {
				neighbors.add(fields[i+2][j-1]);
			}
			if (j+1 < size) {
				neighbors.add(fields[i+2][j+1]);
			}
		}
		if (i-1 >= 0) {
			if (j-2 >= 0) {
				neighbors.add(fields[i-1][j-2]);
			}
			if (j+2 < size) {
				neighbors.add(fields[i-1][j+2]);
			}
		}
		if (i+1 < size) {
			if (j-2 >= 0) {
				neighbors.add(fields[i+1][j-2]);
			}
			if (j+2 < size) {
				neighbors.add(fields[i+1][j+2]);
			}
		}
		return neighbors;
	}
}
