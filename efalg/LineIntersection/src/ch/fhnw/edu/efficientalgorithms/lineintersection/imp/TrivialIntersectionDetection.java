package ch.fhnw.edu.efficientalgorithms.lineintersection.imp;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import ch.fhnw.edu.efficientalgorithms.lineintersection.Line;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Plane;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Point;

/**
 * Trivial O(n^2) implementation of a intersection finding algorithm.
 * 
 * @author Martin Schaub
 */
public final class TrivialIntersectionDetection extends AbstractAlgorithm {

	/**
	 * Constructor
	 * 
	 * @param plane plane for the base class
	 */
	public TrivialIntersectionDetection(final Plane plane) {
		super(plane);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.Algorithm#start()
	 */
	@Override
	public void start() {
		notifyListenersProcessStarted();

		PlaneAlgorithms algorithms = new PlaneAlgorithms(getPlane());
		Set<Point> intersections = new TreeSet<Point>();

		Set<Line> lines = new HashSet<Line>(getPlane().getLines().size() * 2);
		synchronized (getPlane()) {
			lines.addAll(getPlane().getLines());
		}

		for (final Line line1 : lines) {
			for (final Line line2 : lines) {
				if (!line1.equals(line2) && algorithms.doesIntersect(line1, line2)) {
					Point intersection = algorithms.getIntersectionPoint(line1, line2);
					if (!intersections.contains(intersection)) {
						notifyListenersReportObject(intersection);
						intersections.add(intersection);
					}
				}
			}
		}

		notifyListenersProcessEnded();
	}

}
