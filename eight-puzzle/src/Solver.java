import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

  private static final int NOT_SOLVABLE_MOVES = -1;
  private Move lastMove;

  public Solver(Board initial) {
    MinPQ<Move> moves = new MinPQ<Move>();
    moves.insert(new Move(initial, null));

    MinPQ<Move> twinMoves = new MinPQ<Move>();
    twinMoves.insert(new Move(initial.twin(), null));

    while (!moves.isEmpty() && !twinMoves.isEmpty()) {
      lastMove = validateNextMove(moves);
      if (lastMove != null) // found goal
        break;
      if (validateNextMove(twinMoves) != null) // found goal in twin board
        break;
    }
  }

  /**
   * Remove next best move. If reach goal, then return.
   * Otherwise, all its neighbors into the queue.
   */
  private Move validateNextMove(MinPQ<Move> moves) {
    if (moves.isEmpty())
      return null;
    
    Move bestMove = moves.delMin();
    if (bestMove.board.isGoal())
      return bestMove;
    
    for (Board neighbor : bestMove.board.neighbors())
      if (bestMove.previous == null || !neighbor.equals(bestMove.previous.board))
        moves.insert(new Move(neighbor, bestMove));
    return null;
  }

  public boolean isSolvable() {
    return lastMove != null;
  }

  public int moves() {
    return isSolvable() ? lastMove.numMoves : NOT_SOLVABLE_MOVES;
  }

  public Iterable<Board> solution() {
    if (!isSolvable())
      return null;

    Stack<Board> moves = new Stack<Board>();
    Move move = lastMove;
    while (move != null) {
      moves.push(move.board);
      move = move.previous;
    }
    return moves;
  }

  private static class Move implements Comparable<Move> {
    private Move previous;
    private Board board;
    private int numMoves;
    private int priority;

    private Move(Board board, Move previous) {
      this.board = board;
      this.previous = previous;
      
      this.numMoves = previous == null ? 0 : (previous.numMoves + 1);
      this.priority = board.manhattan() + this.numMoves;
    }

    public int compareTo(Move another) {
      return Integer.compare(this.priority, another.priority);
    }
  }
}