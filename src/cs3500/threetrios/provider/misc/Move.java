package cs3500.threetrios.provider.misc;

/**
 * A very basic data class for a move.
 * Contains the move's row, column, and hand index.
 */
public class Move {
  public final int handIndex;
  public final int row;
  public final int column;

  /**
   * Creates a new Move with the given index, row, and column.
   * Row and column are 0-indexed, starting at the top left of the board.
   *
   * @param index - index of the card in the hand.
   * @param row - row of the space to be placed in.
   * @param col - column of the space to be placed in.
   */
  public Move(int index, int row, int col) {
    this.handIndex = index;
    this.row = row;
    this.column = col;
  }
}