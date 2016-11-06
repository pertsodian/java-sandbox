import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

  private static final RectHV UNIT_CONTAINER = new RectHV(0, 0, 1, 1);

  private Node root;
  private int size;

  public KdTree() { }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public void insert(Point2D p) {  
    root = insert(root, p, true);  
  }  

  private Node insert(Node node, Point2D point, boolean isVertical) {
    // reach the leaf, insert new node
    if (node == null) {  
      size++;  
      return new Node(point, isVertical);  
    }  

    // point already exists
    if (node.point.equals(point))
      return node;  

    // otherwise, recursively call insert in the corresponding subtree with inverted orientation
    if (belongsToLeftSubtree(node, point))
      node.leftSubtree = insert(node.leftSubtree, point, !node.isVertical);  
    else
      node.rightSubtree = insert(node.rightSubtree, point, !node.isVertical);  

    return node;  
  } 

  public boolean contains(Point2D p) {
    validateInput(p);
    return contains(root, p);
  }

  private boolean contains(Node node, Point2D point) {
    // reach the leaf and find no match
    if (node == null)
      return false;  

    // found a match
    if (node.point.equals(point))
      return true;

    // otherwise, recursively call contains in the corresponding subtree
    if (belongsToLeftSubtree(node, point))
      return contains(node.leftSubtree, point);  
    else  
      return contains(node.rightSubtree, point);  
  }

  public void draw() {  
    draw(root, UNIT_CONTAINER);  
  }  

  private void draw(final Node node, final RectHV container) {
    // reach the leaf, simply return
    if (node == null)
      return;

    // draw current point
    setupDrawPoint();
    node.point.draw();  

    // calculate and draw the current line based on current orientation and container
    RectHV line;
    if (node.isVertical) {
      StdDraw.setPenColor(StdDraw.RED);
      line = new RectHV(node.point.x(), container.ymin(), node.point.x(), container.ymax());
    }
    else {
      StdDraw.setPenColor(StdDraw.BLUE);  
      line = new RectHV(container.xmin(), node.point.y(), container.xmax(), node.point.y());
    }
    setupDrawLine();
    line.draw();

    // recursively call draw for the subtrees
    draw(node.leftSubtree, getLeftSubtreeContainer(node, container));  
    draw(node.rightSubtree, getRightSubtreeContainer(node, container));  
  }  

  private void setupDrawPoint() {
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(0.01);
  }

  private void setupDrawLine() {
    StdDraw.setPenRadius();
  }

  public Iterable<Point2D> range(RectHV rect) {
    validateInput(rect);
    return range(root, UNIT_CONTAINER, rect);
  }  

  private List<Point2D> range(Node node, RectHV container, RectHV rect) {
    List<Point2D> inRangePoints = new ArrayList<>();
    // reach the leaf, simply return
    if (node == null)  
      return inRangePoints;

    // if the container of current node doesn't intersect with the query range, simply return
    if (!container.intersects(rect))
      return inRangePoints;

    // otherwise, add current point if it is in the query range
    if (rect.contains(node.point))
      inRangePoints.add(node.point);

    // then recursively check for both the subtrees
    inRangePoints.addAll(range(node.leftSubtree, getLeftSubtreeContainer(node, container), rect));
    inRangePoints.addAll(range(node.rightSubtree, getRightSubtreeContainer(node, container), rect));

    return inRangePoints;
  } 

  public Point2D nearest(Point2D p) {
    validateInput(p);
    return nearest(root, UNIT_CONTAINER, p, null);
  }

  private Point2D nearest(Node node, RectHV container, Point2D point, Point2D nearest) {
    // reach the leaf, simple return current nearest
    if (node == null)
      return nearest;
    
    // prune if current nearest is nearer to the query point than the container of current node
    if (nearest != null && nearest.distanceSquaredTo(point) < container.distanceSquaredTo(point))
      return nearest;
    
    // update current nearest if applicable
    if (nearest == null || node.point.distanceSquaredTo(point) < nearest.distanceSquaredTo(point))
      nearest = node.point;

    // then recursively check for both the subtrees
    // however, prioritize the subtree in which the query point falls into
    if (belongsToLeftSubtree(node, point)) {
      nearest = nearest(node.leftSubtree, getLeftSubtreeContainer(node, container), point, nearest);
      nearest = nearest(node.rightSubtree, getRightSubtreeContainer(node, container), point, nearest);
    }
    else {
      nearest = nearest(node.rightSubtree, getRightSubtreeContainer(node, container), point, nearest);
      nearest = nearest(node.leftSubtree, getLeftSubtreeContainer(node, container), point, nearest);
    }
    
    return nearest;
  }

  private void validateInput(Object obj) {
    if (obj == null)
      throw new NullPointerException();
  }
  
  private boolean belongsToLeftSubtree(Node node, Point2D point) {
    return (node.isVertical && Point2D.X_ORDER.compare(point, node.point) < 0) ||
        (!node.isVertical && Point2D.Y_ORDER.compare(point, node.point) < 0);
  }
  
  private RectHV getLeftSubtreeContainer(Node node, RectHV container) {
    if (node.isVertical)
      return new RectHV(container.xmin(), container.ymin(), node.point.x(), container.ymax());
    else
      return new RectHV(container.xmin(), container.ymin(), container.xmax(), node.point.y());
  }
  
  private RectHV getRightSubtreeContainer(Node node, RectHV container) {
    if (node.isVertical)
      return new RectHV(node.point.x(), container.ymin(), container.xmax(), container.ymax());
    else
      return new RectHV(container.xmin(), node.point.y(), container.xmax(), container.ymax());
  }

  private static class Node {
    private Point2D point;
    private boolean isVertical;
    private Node leftSubtree;
    private Node rightSubtree;

    private Node(Point2D point, boolean isVertical) {
      this.point = point;
      this.isVertical = isVertical;
    }
  }
}