import java.util.LinkedList;

/**
 * Created by 55086 on 2017/8/20.
 */
public class BruteCollinearPoints {

    private Point[] points;
    private int segmentNumber;
    private LineSegment[] segments;
    private final int length;

    /**
     * Check the corner cases of input arguments:
     * If there is a null point, throw a java.lang.IllegalArgumentException
     * If there are repeated points, throw a java.lang.IllegalArgumentException
     */
    private void checkArgs() {
        for (int i = 0; i < length; i++) {
            if (points[i] == null)
                throw new java.lang.IllegalArgumentException();
            for (int j = 0; j < i; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new java.lang.IllegalArgumentException();
            }
        }
    }

    /**
     * Count the number of Collinear and get all collinear
     */
    private void findCollinear() {
        // Iterating through all 4-tuples and check if the 4 points are collinear.
        LinkedList<LineSegment> findSegments = new LinkedList<>();
        for (int p = 0; p < length; p++) {
            for (int q = p + 1; q < length; q++) {
                for (int r = q + 1; r < length; r++) {
                    for (int s = r + 1; s < length; s++) {
                        double firstSlope = points[p].slopeTo(points[q]);
                        double secondSlope = points[p].slopeTo(points[r]);
                        double thirdSlope = points[p].slopeTo(points[s]);
                        if (firstSlope == secondSlope && secondSlope == thirdSlope) {
                            Point[] result = {points[p], points[q], points[r], points[s]};
                            Point max = findMax(result);
                            Point min = findMin(result);
                            findSegments.add(new LineSegment(min, max));
                            segmentNumber++;
                        }
                    }
                }
            }
        }

        // Saving the result to a LineSegment array
        segments = new LineSegment[segmentNumber];
        int i = 0;
        for (LineSegment segment: findSegments)
            segments[i++] = segment;

    }

    /**
     * Find the maximum point in a set of points
     * @param points the set of points
     * @return the maximum point
     */
    private Point findMax(Point[] points) {
        int length = points.length;
        Point max = points[0];
        for (int i = 1; i < length; i++) {
            if (points[i].compareTo(max) > 0)
                max = points[i];
        }
        return max;
    }

    /**
     * Find the minimum point in a set of points
     * @param points the set of points
     * @return the minimum point
     */
    private Point findMin(Point[] points) {
        int length = points.length;
        Point min = points[0];
        for (int i = 1; i < length; i++) {
            if (points[i].compareTo(min) < 0)
                min = points[i];
        }
        return min;
    }

    /**
     * Initialize the BruteCollinearPoints class
     * @param points the set of points
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new java.lang.IllegalArgumentException();
        this.points = points;
        segmentNumber = 0;
        length = points.length;
        checkArgs();
        findCollinear();
    }

    /**
     * Get the number of the collinear consisted by the points in the set
     * @return the number of segments
     */
    public int numberOfSegments() {
        return segmentNumber;
    }

    /**
     * Get the set of segments consisted by points
     * @return the array of LineSegment
     */
    public LineSegment[] segments() {
        return segments.clone();
    }
}
