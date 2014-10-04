package ch.fhnw.edu.efficientalgorithms.chessboard.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoardAlgorithm;
import ch.fhnw.edu.efficientalgorithms.chessboard.Position;
import ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolder;
import ch.fhnw.edu.efficientalgorithms.chessboard.impl.PositionImpl;

/**
 * Executes an algorithm in a separate thread.
 * 
 * @author Martin Schaub
 */
public final class AlgorithmExecutionAction extends AbstractAction implements Runnable {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 3751061855771070898L;

	/**
	 * For getting the current chessboard.
	 */
	private final StatusHolder holder;
	/**
	 * Algorithm that will be executed.
	 */
	private final Class<? extends ChessBoardAlgorithm> algorithm;

	/**
	 * To start the algorithm.
	 */
	private ChessBoardAlgorithm algorithmInstance;

	/**
	 * Constructor
	 * 
	 * @param holder status holder for the algorithm.
	 * @param algorithm algorithm to execute
	 */
	public AlgorithmExecutionAction(final StatusHolder holder, final Class<? extends ChessBoardAlgorithm> algorithm) {
		if (algorithm == null || holder == null) {
			throw new NullPointerException();
		}
		this.holder = holder;
		this.algorithm = algorithm;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {
		synchronized (holder) {
			if (holder.isAlgorithmRunning()) {
				JOptionPane.showMessageDialog(null, "An algorithm is currently running. Therefore no new one can be started.",
						"Cannot start algorithm", JOptionPane.ERROR_MESSAGE);
			}
			else {
				try {
					algorithmInstance = algorithm.newInstance();
					holder.algorithmStarted(algorithmInstance);
					Thread executor = new Thread(this);
					executor.start();
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Position pos = holder.getSelectedPosition();
		if (pos == null) {
			pos = new PositionImpl(0, 0);
		}
		algorithmInstance.execute(holder.getChessBoard(), pos);

		synchronized (holder) {
			holder.algorithmStopped();
		}
	}
}