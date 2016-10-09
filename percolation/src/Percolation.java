import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private final int size;
  private final boolean[][] sites;
  private int virtualTopSite;
  private boolean isPercolated;
  private final WeightedQuickUnionUF unionFind;
  private final boolean[] isConnectedToBottom;

  /**
   * create n-by-n grid, with all sites blocked
   * @param n
   */
  public Percolation(int n) {
    if (n <= 0)
      throw new IllegalArgumentException("Grid size must be positive");

    size = n;
    sites = new boolean[size][size];
    virtualTopSite = 0;
    unionFind = new WeightedQuickUnionUF(size*size+1);
    isConnectedToBottom = new boolean[size*size+1];
  }

  /**
   * open site (row, column) if it is not open already
   * @param row in range [1,n]
   * @param col in range [1,n]
   */
  public void open(int row, int col) {
    validateRange(row, col);
    if (sites[row-1][col-1])
      return;
    sites[row-1][col-1] = true;

    int unionFindIndex = getUnionFindIndex(row, col);
    boolean connectedToBottom = false;
    
    if (row == 1)
      unionFind.union(virtualTopSite, unionFindIndex);
    if (row == size)
      connectedToBottom = true;
    
    if (col > 1 && isOpen(row, col-1))
      connectedToBottom = unionWithNeighbor(unionFindIndex, row, col-1) || connectedToBottom;
    if (col < size && isOpen(row, col+1))
      connectedToBottom = unionWithNeighbor(unionFindIndex, row, col+1) || connectedToBottom;
    if (row > 1 && isOpen(row-1, col))
      connectedToBottom = unionWithNeighbor(unionFindIndex, row-1, col) || connectedToBottom;
    if (row < size && isOpen(row+1, col))
      connectedToBottom = unionWithNeighbor(unionFindIndex, row+1, col) || connectedToBottom;
    
    isConnectedToBottom[unionFind.find(unionFindIndex)] = connectedToBottom;
    if (connectedToBottom && isFull(row, col))
      isPercolated = true;
  }

  /**
   * is site (row, column) open?
   * @param row in range [1,n]
   * @param col in range [1,n]
   * @return
   */
  public boolean isOpen(int row, int col) {
    validateRange(row, col);
    return sites[row-1][col-1];
  }

  /**
   * is site (row, column) full?
   * @param row in range [1,n]
   * @param col in range [1,n]
   * @return
   */
  public boolean isFull(int row, int col) {
    validateRange(row, col);
    return unionFind.connected(virtualTopSite, getUnionFindIndex(row, col));
  }

  /**
   * does the system percolate?
   * @return
   */
  public boolean percolates() {
    return isPercolated;
  }

  private void validateRange(int row, int col) {
    if (!isInRange(row))
      throw new IndexOutOfBoundsException("Row is out-of-range");
    if (!isInRange(col))
      throw new IndexOutOfBoundsException("Column is out-of-range");
  }

  private boolean isInRange(int i) {
    return i >= 1 && i <= size;
  }

  private boolean unionWithNeighbor(int current, int row, int col) {
    int neighbor = getUnionFindIndex(row, col);
    boolean connectedToBottom = isConnectedToBottom[unionFind.find(neighbor)] 
        || isConnectedToBottom[unionFind.find(current)];
    unionFind.union(current, neighbor);
    return connectedToBottom;
  }
  
  private int getUnionFindIndex(int row, int col) {
    return (row-1)*size + col;
  }
}
