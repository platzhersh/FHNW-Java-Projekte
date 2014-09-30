package uebung1.warnsdorf;

public class WarnsdorfModel {
	
	ChessBoard board;
	
	
	/* Konstruktoren */
	public WarnsdorfModel(int boardSize) {
		generateChessBoard(boardSize);
	}
	
	/* Getter & Setter */
	
	public int getSize() {
		return board.size;
	}
	
	public boolean allVisited() {
		
		for(ChessBoardField[] i : board.fields) {
			for (ChessBoardField j : i) {
				if (!j.visited()) return false;
			}
		}
		
		return true;
	}
	
	public void generateChessBoard(int boardSize) {
		if (boardSize <= 2) {
			throw new IllegalArgumentException("Schachbrettgrösse muss mindestens 3 sein.");
		}
		
		board = new ChessBoard(boardSize);

		for (int i = 0; i < getSize(); i++) {
			
			for (int j = 0; j < getSize(); j++) {
				
				// erste und letzte Reihe des Schachbretts
				if (i == 0 || i == getSize()-1) {
					if (j == 0 || j == getSize()-1) {
						board.set(i, j, 2);
					} 
					else if (j == 1 || j == getSize()-2) {
						int value = getSize() > 3 ? 3 : 2;
						board.set(i, j, value);
					}
					else if (j == 2 || j == getSize()-3) {
						int value = getSize() > 4 ? 4 : 3;
						board.set(i,j,value);
					} 
					else if (j > 2 && j < getSize()-3) {
						board.set(i,j,4);
					}
				}
				// zweite und zweitletzte Reihe des Schachbretts
				else if (i == 1 || i == getSize()-2) {
					if (j == 0 || j == getSize()-1) {
						int value = getSize() > 3 ? 3 : 2;
						board.set(i,j, value);
					} 
					else if (j == 1 || j == getSize()-2) {
						int value = getSize() > 3 ? 4 : 0;
						board.set(i,j, value); 
					}
					else if (j > 1 && j < getSize()-2) {
						board.set(i,j, 6);
					}
				}
				// alle anderen Reihen
				else {
					if (j == 0 || j == getSize()-1) {
						board.set(i,j, 4);
					} 
					else if (j == 1 || j == getSize()-2) {
						board.set(i,j, 6); 
					}
					else  {
						board.set(i,j, 8);
					}
				}
				
			}
			
		}

	}
		
}
