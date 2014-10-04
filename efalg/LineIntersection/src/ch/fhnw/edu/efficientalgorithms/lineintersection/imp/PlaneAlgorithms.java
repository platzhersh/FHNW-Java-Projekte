package ch.fhnw.edu.efficientalgorithms.lineintersection.imp;

import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

import ch.fhnw.edu.efficientalgorithms.lineintersection.Line;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Plane;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Point;

/**
 * Stores some algorithms for calculating certain properties of the plane.
 * 
 * @author Martin Schaub
 */
public final class PlaneAlgorithms {

	/**
	 * Plane used for the calculation.
	 */
	private final Plane plane;

	/**
	 * 
	 * Constructor
	 * 
	 * @param plane for calculations.
	 */
	public PlaneAlgorithms(final Plane plane) {
		this.plane = plane;
	}

	/**
	 * Gets all lines at a given point.
	 * 
	 * @param point point to check
	 * @param tolerance how far a line can be to still be in the result list. (a rectangle is used to match)
	 * @return all lines containing the point
	 */
	public List<Line> getLinesAtPoint(final Point point, final double tolerance) {
		double x1 = point.getX() - tolerance / 2;
		double y1 = point.getY() - tolerance / 2;
		double x2 = point.getX() + tolerance / 2;
		double y2 = point.getY() + tolerance / 2;

		Rectangle2D rect = new Rectangle2D.Double(x1, y1, x2, y2);

		List<Line> lines = new LinkedList<Line>();
		synchronized (plane) {
			for (final Line line : plane.getLines()) {
				if (rect.intersectsLine(line.getStartPoint().getX(), line.getStartPoint().getY(), line.getEndPoint().getX(),
						line.getEndPoint().getY())) {
					lines.add(line);
				}
			}
		}
		return lines;
	}

	/**
	 * Tests if line1 or line2 intersects with each other.
	 * 
	 * @param line1 first line for test
	 * @param line2 second line for test
	 * @return true, if they intersect and false otherwise
	 */
	public boolean doesIntersect(final Line line1, final Line line2) {
		return getIntersectionPoint(line1, line2) != null;
	}

	/**
	 * Calculates the intersection point of two lines if it exists.
	 * 
	 * @param line1 first line for the calculation
	 * @param line2 second line for the calculation
	 * @return the intersection point or null, if they don't intersect at all
	 */
	public Point getIntersectionPoint(final Line line1, final Line line2) {
		// See both lines as vectors -> the function approach does not work, because vertical
		// lines are allowed and solving the linear equation system produces too much cases. Therefore the
		// formula from http://en.wikipedia.org/wiki/Line-line_intersection is used.
		// line1: (x1,y1) <--> (x2,y2)
		// line2: (x3,y3) <--> (x4,y4)
		double x1 = line1.getStartPoint().getX();
		double y1 = line1.getStartPoint().getY();
		double x2 = line1.getEndPoint().getX();
		double y2 = line1.getEndPoint().getY();
		double x3 = line2.getStartPoint().getX();
		double y3 = line2.getStartPoint().getY();
		double x4 = line2.getEndPoint().getX();
		double y4 = line2.getEndPoint().getY();

		double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (denominator == 0) {
			return null;
		}
		double xNumerator = (x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4);
		double yNumerator = (x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4);

		int x = (int) (xNumerator / denominator);
		int y = (int) (yNumerator / denominator);

		// The point must be in the interval
		if (line1.getStartPoint().getX() <= x && line1.getEndPoint().getX() >= x) {
			if (line2.getStartPoint().getX() <= x && line2.getEndPoint().getX() >= x) {
				// y coordinates are not ordered, therfore both possibilites have to be checked.
				if (line1.getStartPoint().getY() <= y && line1.getEndPoint().getY() >= y || line1.getStartPoint().getY() >= y
						&& line1.getEndPoint().getY() <= y) {
					if (line2.getStartPoint().getY() <= y && line2.getEndPoint().getY() >= y || line2.getStartPoint().getY() >= y
							&& line2.getEndPoint().getY() <= y) {
						return new ImmutablePoint(x, y);
					}
				}
			}
		}

		return null;
	}

	/**
	 * Calculates the slope of the line.
	 * 
	 * @param line to calculate slope from
	 * @return line's slope
	 */
	public double getSlope(final Line line) {
		double xdiff = line.getEndPoint().getX() - line.getStartPoint().getX();
		double ydiff = line.getEndPoint().getY() - line.getStartPoint().getY();
		return ydiff / xdiff;
	}

	/**
	 * Calculates the y intersect of a line if it would be a linear function.
	 * @param line line to calculate the y intersect from
	 * @return line's y intersect
	 */
	public double getYIntersect(final Line line) {
		// use the startpoint to determine the y intersect
		return line.getStartPoint().getY() - line.getStartPoint().getX() * getSlope(line);
	}

	/**
	 * Calculates the y value a line has at a given point x.
	 * 
	 * @param line input line
	 * @param x input point
	 * @return the line value at x
	 */
	public double getValueAtXCoordinate(final Line line, final int x) {
		// f(x) = mx + b
		double m = getSlope(line);
		double b = getYIntersect(line);
		double y = m * x + b;

		if (Double.isInfinite(y)) {
			return line.getEndPoint().getY();
		}

		return y;
	}
}
