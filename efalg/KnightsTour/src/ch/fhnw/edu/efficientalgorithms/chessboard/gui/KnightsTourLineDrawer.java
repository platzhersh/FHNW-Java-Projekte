package ch.fhnw.edu.efficientalgorithms.chessboard.gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard;
import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoardAlgorithm;
import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoardListener;
import ch.fhnw.edu.efficientalgorithms.chessboard.Field;
import ch.fhnw.edu.efficientalgorithms.chessboard.GraphicalBoard;
import ch.fhnw.edu.efficientalgorithms.chessboard.Position;
import ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener;
import ch.fhnw.edu.efficientalgorithms.chessboard.impl.KnightsTourField;

/**
 * Draws a path along a number path.
 * 
 * @author Martin Schaub
 */
public final class KnightsTourLineDrawer implements ChessBoardListener, StatusHolderListener {

	/**
	 * Board on which to draw the lines
	 */
	private final GraphicalBoard board;
	/**
	 * All neighbors are connected.
	 */
	private final NavigableMap<KnightsTourField, Position> fields = new TreeMap<KnightsTourField, Position>();
	/**
	 * Saves all values which are in the fields set.
	 */
	private final Map<Position, KnightsTourField> valuesInFields = new HashMap<Position, KnightsTourField>();

	/**
	 * Constructor
	 * 
	 * @param board board on which to draw the lines
	 */
	public KnightsTourLineDrawer(final GraphicalBoard board) {
		if (board == null) {
			throw new NullPointerException();
		}
		this.board = board;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoardListener#fieldChanged(ch.fhnw.edu.efficientalgorithms.chessboard
	 * .ChessBoard, ch.fhnw.edu.efficientalgorithms.chessboard.Position)
	 */
	@Override
	public void fieldChanged(final ChessBoard chessBoard, final Position position) {
		Field nf = chessBoard.getField(position);
		if (nf instanceof KnightsTourField) {

			// Remove from old position
			if (valuesInFields.containsKey(position)) {

				KnightsTourField oldField = valuesInFields.get(position);
				Entry<KnightsTourField, Position> lower = fields.lowerEntry(oldField);
				Entry<KnightsTourField, Position> higher = fields.higherEntry(oldField);
				if (lower != null) {
					board.removeLine(lower.getValue(), position);
				}
				if (higher != null) {
					board.removeLine(position, higher.getValue());
				}
				fields.remove(oldField);
				valuesInFields.remove(position);

				if (lower != null && higher != null) {
					board.drawLine(lower.getValue(), higher.getValue());
				}
			}

			// Add new lines
			KnightsTourField newField = (KnightsTourField) nf;
			if (newField.isVisited()) {
				Entry<KnightsTourField, Position> lower = fields.lowerEntry(newField);
				Entry<KnightsTourField, Position> higher = fields.higherEntry(newField);

				if (lower != null && higher != null) {
					board.removeLine(lower.getValue(), higher.getValue());
				}

				fields.put(newField, position);
				valuesInFields.put(position, newField);

				if (lower != null) {
					board.drawLine(lower.getValue(), position);
				}
				if (higher != null) {
					board.drawLine(position, higher.getValue());
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener#algorithmStarted(ch.fhnw.edu.efficientalgorithms
	 * .chessboard.ChessBoardAlgorithm)
	 */
	@Override
	public void algorithmStarted(final ChessBoardAlgorithm algorithm) {
		cleanup();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener#algorithmStopped(ch.fhnw.edu.efficientalgorithms
	 * .chessboard.ChessBoardAlgorithm)
	 */
	@Override
	public void algorithmStopped(final ChessBoardAlgorithm algorithm) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener#newChessBoardLoaded(ch.fhnw.edu.efficientalgorithms
	 * .chessboard.ChessBoard, ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard)
	 */
	@Override
	public void newChessBoardLoaded(final ChessBoard oldBoard, final ChessBoard newBoard) {
		cleanup();
		oldBoard.removeCheeseBoardListener(this);
		newBoard.addCheeseBoardListener(this);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener#positionSelected(ch.fhnw.edu.efficientalgorithms
	 * .chessboard.Position)
	 */
	@Override
	public void positionSelected(final Position position) {
		// nothing to do
	}

	/**
	 * Removes all cached data.
	 */
	private void cleanup() {
		Iterator<Entry<KnightsTourField, Position>> it = fields.entrySet().iterator();
		if (it.hasNext()) {
			Position last = it.next().getValue();
			while (it.hasNext()) {
				Position cur = it.next().getValue();
				board.removeLine(last, cur);
				last = cur;
			}
		}
		fields.clear();
		valuesInFields.clear();
	}

}
