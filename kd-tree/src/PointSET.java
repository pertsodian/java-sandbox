import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
  
  private final TreeSet<Point2D> points;
  
  public PointSET() {
    points = new TreeSet<>();
  }
  
  public boolean isEmpty() {
    return points.isEmpty();
  }
  
  public int size() {
    return points.size();
  }
  
  public void insert(Point2D p) {
    validateInput(p);
    points.add(p);
  }
  
  public boolean contains(Point2D p) {
    validateInput(p);
    return points.contains(p);
  }
  
  public void draw() {
    setupDrawPoint();
    points.forEach(Point2D::draw);
  }
  
  private void setupDrawPoint() {
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(0.01);
  }
  
  public Iterable<Point2D> range(RectHV rect) {
    validateInput(rect);
    return points.stream().filter(rect::contains).collect(Collectors.toList());
  }
  
  public Point2D nearest(Point2D p) {
    validateInput(p);
    
    if (isEmpty())
      return null;
    
    Comparator<Point2D> distanceComparator = new Comparator<Point2D>() {
      @Override
      public int compare(Point2D o1, Point2D o2) {
        return Double.compare(o1.distanceSquaredTo(p), o2.distanceSquaredTo(p));
      }
    };
    return points.stream().min(distanceComparator).get();
  }
  
  private void validateInput(Object obj) {
    if (obj == null)
      throw new NullPointerException();
  }
}