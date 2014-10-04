package ch.fhnw.edu.efficientalgorithms.chessboard.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import ch.fhnw.edu.efficientalgorithms.chessboard.Position;
import ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolder;

/**
 * Detects which field was selected, if the user clicks on the chessboard.
 * 
 * @author Martin Schaub
 */
public final class ChessBoardClickDetector implements MouseListener {

	/**
	 * Which position is observed by this detector.
	 */
	private final Position observedPosition;
	/**
	 * To update the currently clicked position.
	 */
	private final StatusHolder statusHolder;

	/**
	 * Constructor
	 * 
	 * @param statusHolder to update the currently clicked position.
	 * @param observedPosition which position is observed
	 */
	public ChessBoardClickDetector(final StatusHolder statusHolder, final Position observedPosition) {
		if (observedPosition == null || statusHolder == null) {
			throw new NullPointerException();
		}
		this.statusHolder = statusHolder;
		this.observedPosition = observedPosition;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(final MouseEvent e) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(final MouseEvent e) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(final MouseEvent e) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(final MouseEvent e) {
		statusHolder.setSelectedPosition(observedPosition);
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(final MouseEvent e) {
		// nothing to do
	}

}
