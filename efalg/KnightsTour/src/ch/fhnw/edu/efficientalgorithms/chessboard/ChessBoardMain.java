package ch.fhnw.edu.efficientalgorithms.chessboard;

import ch.fhnw.edu.efficientalgorithms.chessboard.gui.ChessBoardGUI;
import ch.fhnw.edu.efficientalgorithms.chessboard.impl.ChessBoardImpl;
import ch.fhnw.edu.efficientalgorithms.chessboard.impl.StatusHolderImpl;

/**
 * Starts the Knight Tour program.
 * 
 * @author Martin Schaub
 */
public final class ChessBoardMain {

	/**
	 * Main method.
	 * 
	 * @param args command line parameters
	 */
	public static void main(final String[] args) {

		StatusHolder holder = new StatusHolderImpl();
		ChessBoard board = new ChessBoardImpl(8, 8);
		holder.setChessBoard(board);
		new ChessBoardGUI(holder);

		// correctly initialize the components
		holder.setChessBoard(new ChessBoardImpl(8, 8));
	}
}
