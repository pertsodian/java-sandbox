import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

  private final int x;
  private final int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Draws this point to standard draw.
   */
  public void draw() {
    StdDraw.point(x, y);
  }

  /**
   * Draws the line segment between this point and the specified point
   * to standard draw.
   *
   * @param that the other point
   */
  public void drawTo(Point that) {
    StdDraw.line(this.x, this.y, that.x, that.y);
  }

  /**
   * Returns the slope between this point and the specified point.
   * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
   * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
   * +0.0 if the line segment connecting the two points is horizontal;
   * Double.POSITIVE_INFINITY if the line segment is vertical;
   * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
   *
   * @param  that the other point
   * @return the slope between this point and the specified point
   */
  public double slopeTo(Point that) {
    int dx = that.x - this.x;
    int dy = that.y - this.y;

    if (dx == 0 && dy == 0)
      return Double.NEGATIVE_INFINITY;
    if (dx == 0)
      return Double.POSITIVE_INFINITY;
    if (dy == 0)
      return +0d;
    else
      return (double) dy / dx;  
  }

  /**
   * Compares two points by y-coordinate, breaking ties by x-coordinate.
   * Formally, the invoking point (x0, y0) is less than the argument point
   * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
   *
   * @param  that the other point
   * @return the value <tt>0</tt> if this point is equal to the argument
   *         point (x0 = x1 and y0 = y1);
   *         a negative integer if this point is less than the argument
   *         point; and a positive integer if this point is greater than the
   *         argument point
   */
  public int compareTo(Point that) {
    if (this.y == that.y)
      return Integer.compare(this.x, that.x);
    return Integer.compare(this.y, that.y);
  }

  /**
   * Compares two points by the slope they make with this point.
   * The slope is defined as in the slopeTo() method.
   *
   * @return the Comparator that defines this ordering on points
   */
  public Comparator<Point> slopeOrder() {
    return new Comparator<Point>() {
      @Override
      public int compare(Point o1, Point o2) {
        return Double.compare(slopeTo(o1), slopeTo(o2));
      }
    };
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}