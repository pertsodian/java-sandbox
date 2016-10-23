import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BruteCollinearPoints {

  private final List<LineSegment> lineSegments = new ArrayList<>();

  public BruteCollinearPoints(Point[] points) {
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

    for (int i = 0; i < size-3; i++) {
      for (int j = i+1; j < size-2; j++) {
        for (int m = j+1; m < size-1; m++) {
          if (sortedPoints[i].slopeTo(sortedPoints[j]) == sortedPoints[i].slopeTo(sortedPoints[m])) {
            for (int n = m+1; n < size; n++)
              if (sortedPoints[i].slopeTo(sortedPoints[j]) == sortedPoints[i].slopeTo(sortedPoints[n]))
                lineSegments.add(new LineSegment(sortedPoints[i], sortedPoints[n]));
          }
        }
      }
    }
  }

  public int numberOfSegments() {
    return lineSegments.size();
  }

  public LineSegment[] segments() {
    return lineSegments.toArray(new LineSegment[0]);
  }
}
