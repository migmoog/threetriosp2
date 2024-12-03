package model.actor;

import java.awt.Point;
import java.util.Map;

import model.Cell;

/**
 * A move that can be set up, and then played to a game board.
 * Preferably, the details of the move are chosen on construction.
 *
 * <p> (i.e., the implementation for a move "PlayCard"
 * COULD be constructed with a Card, and grid Point.
 * When played using apply(), it places the card at that Point) </p>
 */
public interface Move {
  /**
   * Apply this move to the game board. Assume the move is completely
   * prepared and able to be played
   *
   * @param grid the grid to insert the move into.
   * @returns the point on the grid to which the move was played.
   */
  Point apply(Map<Point, Cell> grid);
}
