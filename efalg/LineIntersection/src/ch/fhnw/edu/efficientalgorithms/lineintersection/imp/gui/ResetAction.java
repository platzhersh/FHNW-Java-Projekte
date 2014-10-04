package ch.fhnw.edu.efficientalgorithms.lineintersection.imp.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ch.fhnw.edu.efficientalgorithms.lineintersection.Drawer;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Plane;

/**
 * 
 * @author Martin Schaub
 */
public final class ResetAction extends AbstractAction {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -1643099863353217894L;

	/**
	 * Plane to reset.
	 */
	private final Plane plane;
	/**
	 * Remove all drawings because they are not needed anymore.
	 */
	private final Drawer drawer;
	/**
	 * Object to lock before the reset can happen
	 */
	private final Object lock;

	/**
	 * Constructor
	 * 
	 * @param plane plane to reset
	 * @param drawer drawer to reset
	 * @param lock lock to obtain before the reset
	 */
	public ResetAction(final Plane plane, final Drawer drawer, final Object lock) {
		if (plane == null || drawer == null || lock == null) {
			throw new NullPointerException();
		}
		this.plane = plane;
		this.drawer = drawer;
		this.lock = lock;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		synchronized (lock) {
			drawer.clearAllDrawables();
			plane.clear();
		}
	}

}
