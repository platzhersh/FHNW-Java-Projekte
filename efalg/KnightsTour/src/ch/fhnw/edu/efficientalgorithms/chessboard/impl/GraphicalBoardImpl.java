package ch.fhnw.edu.efficientalgorithms.chessboard.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard;
import ch.fhnw.edu.efficientalgorithms.chessboard.GraphicalBoard;
import ch.fhnw.edu.efficientalgorithms.chessboard.Position;
import ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolder;
import ch.fhnw.edu.efficientalgorithms.chessboard.gui.ChessBoardClickDetector;
import ch.fhnw.edu.efficientalgorithms.chessboard.gui.ChessBoardFieldUpdater;

/**
 * Implementation of the GraphicalBoard.
 * 
 * @author Martin Schaub
 */
public final class GraphicalBoardImpl extends JPanel implements GraphicalBoard {

	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = -2379651369689197098L;
	/**
	 *
	 */
	private static final int DOT_SIZE = 4;

	/**
	 * Status holder.
	 */
	private final StatusHolder holder;
	/***
	 * All lines
	 */
	private final Set<Line> lines = new HashSet<Line>();
	/**
	 * All updaters for later removal.
	 */
	private final List<ChessBoardFieldUpdater> updaters = new LinkedList<ChessBoardFieldUpdater>();

	/**
	 * All values of the chessboard
	 */
	private JLabel[][] labels = new JLabel[0][0];

	/**
	 * Constructor
	 * 
	 * @param holder holder for status updates
	 */
	public GraphicalBoardImpl(final StatusHolder holder) {
		if (holder == null) {
			throw new NullPointerException();
		}
		this.holder = holder;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.GraphicalBoard#init(ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard
	 * )
	 */
	@Override
	public void init(final ChessBoard newBoard) {
		if (newBoard == null) {
			throw new NullPointerException();
		}

		cleanup();

		final int numOfRows = newBoard.getNumOfRows();
		final int numOfColumns = newBoard.getNumOfColumns();

		setLayout(new GridLayout(numOfRows, numOfColumns));

		labels = new JLabel[numOfRows][numOfColumns];
		for (int i = 0; i < numOfRows; ++i) {
			for (int j = 0; j < numOfColumns; ++j) {
				Position pos = new PositionImpl(i, j);

				JLabel label = new JLabel();
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				add(label);

				// color the new label, so it looks like a chessfield
				label.setOpaque(true);
				if (((i % 2) + j) % 2 == 0) {
					label.setBackground(Color.WHITE);
				}
				else {
					label.setBackground(Color.LIGHT_GRAY);
				}

				labels[i][j] = label;

				ChessBoardFieldUpdater updater = new ChessBoardFieldUpdater(label, pos);
				newBoard.addCheeseBoardListener(updater);
				holder.addStatusHolderListener(updater);
				updaters.add(updater);

				ChessBoardClickDetector detector = new ChessBoardClickDetector(holder, pos);
				label.addMouseListener(detector);
			}
		}

		// Sometimes changes are not immediately viewable
		doLayout();
		getParent().repaint();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.GraphicalBoard#drawLine(ch.fhnw.edu.efficientalgorithms.chessboard.Position
	 * , ch.fhnw.edu.efficientalgorithms.chessboard.Position)
	 */
	@Override
	public synchronized void drawLine(final Position start, final Position end) {
		if (start == null || end == null) {
			throw new NullPointerException();
		}
		lines.add(new Line(start, end));
		repaint();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.GraphicalBoard#setText(ch.fhnw.edu.efficientalgorithms.chessboard.Position
	 * , java.lang.String)
	 */
	@Override
	public void setText(final Position pos, final String value) {
		if (pos == null || value == null) {
			throw new NullPointerException();
		}

		labels[pos.getRow()][pos.getColumn()].setText(value);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.GraphicalBoard#removeLine(ch.fhnw.edu.efficientalgorithms.chessboard
	 * .Position, ch.fhnw.edu.efficientalgorithms.chessboard.Position)
	 */
	@Override
	public synchronized void removeLine(final Position start, final Position end) {
		if (start == null || end == null) {
			throw new NullPointerException();
		}
		lines.remove(new Line(start, end));
		repaint();
	}

	/**
	 * To draw the lines.
	 */
	@Override
	public synchronized void paint(final Graphics g) {
		super.paint(g);
		Graphics2D graphics = (Graphics2D) g;
		Stroke oldStroke = graphics.getStroke();
		graphics.setStroke(new BasicStroke(2));

		for (Line line : lines) {
			JLabel start = labels[line.start.getRow()][line.start.getColumn()];
			JLabel end = labels[line.end.getRow()][line.end.getColumn()];

			Rectangle startR = start.getBounds();
			Rectangle endR = end.getBounds();

			int startX = (int) (startR.getMinX() + startR.getMaxX()) / 2;
			int startY = (int) (startR.getMinY() + startR.getMaxY()) / 2;
			int endX = (int) (endR.getMinX() + endR.getMaxX()) / 2;
			int endY = (int) (endR.getMinY() + endR.getMaxY()) / 2;

			graphics.drawLine(startX, startY, endX, endY);
			graphics.fillOval(startX - DOT_SIZE, startY - DOT_SIZE, 2 * DOT_SIZE, 2 * DOT_SIZE);
			graphics.fillOval(endX - DOT_SIZE, endY - DOT_SIZE, 2 * DOT_SIZE, 2 * DOT_SIZE);
		}

		graphics.setStroke(oldStroke);
	}

	/**
	 * Removes old board at reinitialization.
	 */
	private void cleanup() {

		for (ChessBoardFieldUpdater updater : updaters) {
			holder.removeStatusHolderListener(updater);
		}

		updaters.clear();
		lines.clear();
		removeAll();
	}

	/**
	 * Helper object for representing a tuple.
	 */
	private final class Line {
		/**
		 * Start position
		 */
		private final Position start;
		/**
		 * End position
		 */
		private final Position end;

		/**
		 * Constructor
		 * 
		 * @param start start position
		 * @param end end position
		 */
		public Line(final Position start, final Position end) {
			if (start == null || end == null) {
				throw new NullPointerException();
			}
			this.start = start;
			this.end = end;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((end == null) ? 0 : end.hashCode());
			result = prime * result + ((start == null) ? 0 : start.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Line other = (Line) obj;
			if (end == null) {
				if (other.end != null) {
					return false;
				}
			}
			else if (!end.equals(other.end)) {
				return false;
			}
			if (start == null) {
				if (other.start != null) {
					return false;
				}
			}
			else if (!start.equals(other.start)) {
				return false;
			}
			return true;
		}

	}
}
