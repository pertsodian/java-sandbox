import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Board {

  private static final int SPACE = 0;

  private final int[][] blocks;
  private int hamming = -1;
  private int manhattan = -1;

  public Board(int[][] blocks) {
    if (blocks == null || blocks.length == 0 ||
        blocks[0] == null || blocks.length != blocks[0].length)
      throw new IllegalArgumentException("Invalid input specified");

    this.blocks = copy2DArray(blocks);
  }

  private int[][] copy2DArray(int[][] arr) {
    int [][] copy = new int[arr.length][];
    for (int i = 0; i < arr.length; i++) {
      copy[i] = new int[arr[i].length];
      System.arraycopy(arr[i], 0, copy[i], 0, arr[i].length);
    }
    return copy;
  }

  public int dimension() {
    return blocks.length;
  }

  /**
   * @return numbers of out-of-place blocks
   */
  public int hamming() {
    if (hamming == -1) {
      int count = 0;
      for (int row = 0; row < blocks.length; row++)
        for (int col = 0; col < blocks.length; col++)
          if (isInWrongPosition(row, col))
            count++;
      hamming = count;
    }
    return hamming;
  }

  private boolean isInWrongPosition(int row, int col) {
    int value = blocks[row][col];
    return !isSpace(value) && value != getGoalValue(row, col);
  }

  /**
   * @return total Manhattan distances between blocks and corresponding goals
   */
  public int manhattan() {
    if (manhattan == -1) {
      int sum = 0;
      for (int row = 0; row < blocks.length; row++)
        for (int col = 0; col < blocks.length; col++)
          sum += calculateDistances(row, col);
      manhattan = sum;
    }
    return manhattan;
  }

  private int calculateDistances(int row, int col) {
    int value = blocks[row][col];
    return isSpace(value) ? 0 :
      Math.abs(row - getGoalRow(value)) + Math.abs(col - getGoalCol(value));
  }

  private boolean isSpace(int value) {
    return value == SPACE;
  }

  private int getGoalValue(int row, int col) {
    return row*dimension() + col + 1;
  }

  private int getGoalRow(int value) {
    return (value - 1) / dimension();
  }

  private int getGoalCol(int value) {
    return (value - 1) % dimension();
  }

  public boolean isGoal() {
    for (int row = 0; row < blocks.length; row++)
      for (int col = 0; col < blocks.length; col++)
        if (isInWrongPosition(row, col))
          return false;
    return true;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Board other = (Board) obj;
    if (!Arrays.deepEquals(blocks, other.blocks))
      return false;
    return true;
  }

  /**
   * @return a Board that is obtained by exchanging any pair of blocks
   */
  public Board twin() {
    for (int row = 0; row < blocks.length; row++)
      for (int col = 0; col < blocks.length - 1; col++)
        if (!isSpace(blocks[row][col]) && !isSpace(blocks[row][col+1])) {
          return swapAndCreateNewBoard(row, col, row, col+1);
        }
    throw new IllegalStateException("Cannot find any twin board");
  }

  public Iterable<Board> neighbors() {
    List<Board> neighbors = new LinkedList<Board>();

    int[] spaceLocation = getSpaceBlock();
    int spaceRow = spaceLocation[0];
    int spaceCol = spaceLocation[1];

    if (spaceRow > 0)
      neighbors.add(swapAndCreateNewBoard(spaceRow, spaceCol, spaceRow - 1, spaceCol));
    if (spaceRow < dimension() - 1)
      neighbors.add(swapAndCreateNewBoard(spaceRow, spaceCol, spaceRow + 1, spaceCol));
    if (spaceCol > 0)
      neighbors.add(swapAndCreateNewBoard(spaceRow, spaceCol, spaceRow, spaceCol - 1));
    if (spaceCol < dimension() - 1)
      neighbors.add(swapAndCreateNewBoard(spaceRow, spaceCol, spaceRow, spaceCol + 1));

    return neighbors;
  }

  private Board swapAndCreateNewBoard(int firstRow, int firstCol, int secondRow, int secondCol) {
    Board copy = new Board(blocks);
    copy.blocks[firstRow][firstCol] = blocks[secondRow][secondCol];
    copy.blocks[secondRow][secondCol] = blocks[firstRow][firstCol];
    return copy;
  }

  private int[] getSpaceBlock() {
    for (int row = 0; row < blocks.length; row++)
      for (int col = 0; col < blocks.length; col++)
        if (isSpace(blocks[row][col]))
          return new int[] {row, col};
    throw new IllegalStateException("Cannot find space block");
  }

  @Override
  public String toString() {
    int spacePerBlock =
        1 + (int) Math.log10(Arrays.stream(blocks)
            .mapToInt(row -> Arrays.stream(row).max().getAsInt())
            .max().getAsInt());
    String format = "%" + spacePerBlock + "d ";

    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append(dimension()).append("\n");
    for (int row = 0; row < blocks.length; row++) {
      for (int col = 0; col < blocks.length; col++)
        strBuilder.append(String.format(format, blocks[row][col]));
      strBuilder.append("\n");
    }
    return strBuilder.toString();
  }
}