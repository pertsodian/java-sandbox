import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FastCollinearPoints {

  private final List<LineSegment> lineSegments = new ArrayList<>();

  public FastCollinearPoints(Point[] points) {
    if (points == null)
      throw new NullPointerException("Input points array is null");
    if (Arrays.stream(points).filter(Objects::isNull).findAny().isPresent())
      throw new NullPointerException("Input points array contains null items");

    int size = points.length;
    Point[] sortedPoints = Arrays.copyOf(points, size);
    Arrays.sort(sortedPoints);

    for (int i = 0; i < size-1; i++)
      if (sortedPoints[i].equals(sortedPoints[i+1]))
        throw new IllegalArgumentException("Input points array contains repeated items");

    for (Point pivot : sortedPoints)
      lineSegments.addAll(findCollinearWith(pivot, sortedPoints));
  }

  private List<LineSegment> findCollinearWith(Point pivot, Point[] sortedPoints) {
    List<LineSegment> lines = new ArrayList<>();

    Point[] otherPoints =
        Arrays.stream(sortedPoints).filter(point -> point.compareTo(pivot) != 0)
        .sorted(pivot.slopeOrder())
        .toArray(size -> new Point[size]);

    int i = 0;
    while (i < otherPoints.length-2) {
      int count = 1;
      while (i+count < otherPoints.length &&
          pivot.slopeTo(otherPoints[i]) == pivot.slopeTo(otherPoints[i+count]))
        count++;

      if (count >= 3 && pivot.compareTo(otherPoints[i]) < 0)
        lines.add(new LineSegment(pivot, otherPoints[i+count-1]));

      i += count;
    }

    return lines;
  }

  public int numberOfSegments() {
    return lineSegments.size();
  }

  public LineSegment[] segments() {
    return lineSegments.toArray(new LineSegment[0]);
  }
}
