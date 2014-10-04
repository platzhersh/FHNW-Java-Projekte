package ch.fhnw.edu.efficientalgorithms.lineintersection.imp.scanline;

import ch.fhnw.edu.efficientalgorithms.lineintersection.Plane;
import ch.fhnw.edu.efficientalgorithms.lineintersection.imp.AbstractAlgorithm;

/**
 * Finds line intersections using a scan line algorithm.
 */
public final class ScanLineAlgorithm extends AbstractAlgorithm {

	/**
	 * Constructor
	 *
	 * @param plane plane for
	 */
	public ScanLineAlgorithm(final Plane plane) {
		super(plane);
	}

	/**
	 * Scan Line Algorithm.
	 *
	 * Implementation according to
	 * "Algorithmen und Datenstrukturen, T. Ottmann & P. Widmayer, 4. Auflage, Spektrum Verlag, 2002" page 432
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void start() {
		notifyListenersProcessStarted();

		// Implmentieren Sie hier den Scan Line Algorithmus

		notifyListenersProcessEnded();
	}
}