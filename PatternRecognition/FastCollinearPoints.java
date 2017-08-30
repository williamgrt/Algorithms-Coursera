import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.Arrays;

/**
 * Created by 55086 on 2017/8/20.
 */
public class FastCollinearPoints {

    private final Point[] points;
    private int segmentNumber;
    private final int length;
    private LineSegment[] segments;

    /**
     * Check the corner cases of input arguments:
     * If there is a null point, throw a java.lang.IllegalArgumentException
     * If there are repeated points, throw a java.lang.IllegalArgumentException
     */
    private void checkArgs() {
        int length = points.length;
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
     *  Find all collinear by using a much faster method
     */
    private void fastFindCollinear() {
        LinkedList<LineSegment> findSegments = new LinkedList<>();
        int[] marked = new int[length];
        for (int number: marked) {
            number = 0;
        }
        
        for (int i = 0; i < length; i++) {
            // Sort the array by comparing the slope to the base point
            Point basePoint = points[i];
            Point[] handlePoints = Arrays.copyOfRange(points, i + 1, length);
            Arrays.sort(handlePoints, basePoint.slopeOrder());

            //
            int handleLength = length - i - 1;
            double[] slopesToBase = new double[handleLength];
            for (int j = 0; j < handleLength; j++) {
                slopesToBase[j] = basePoint.slopeTo(handlePoints[j]);
            }
            int j = 1;
            while (j < handleLength) {
                int count = 1;
                while (j < handleLength && slopesToBase[j] == slopesToBase[j - 1]) {
                    count++;
                    j++;
                }
                if (count >= 3) {
                    Point[] allPoints = new Point[count + 1];
                    allPoints[0] = basePoint;
                    for (int k = 0; k < count; k++) {
                        allPoints[k + 1] = handlePoints[j - count + k];
                    }
                    Point max = findMax(allPoints);
                    Point min = findMin(allPoints);
                    findSegments.add(new LineSegment(min, max));
                    segmentNumber++;
                }
                j++;
            }
        }

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

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new java.lang.IllegalArgumentException();
        this.points = points;
        length = points.length;
        segmentNumber = 0;
        checkArgs();
        fastFindCollinear();
    }

    public int numberOfSegments() {
        return segmentNumber;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }
}
