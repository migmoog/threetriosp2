package model.card;

/**
 * <p>A cardinal direction on the board.
 * Can face North, East, South, or West.</p>
 */
public enum Direction {
  NORTH, EAST, SOUTH, WEST;

  /**
   * Get the direction that faces this one. (i.e. If a north called this, you will get a south)
   *
   * @return the facing direction to this one
   */
  public Direction getNeighbor() {
    switch (this) {
      case NORTH:
        return SOUTH;
      case SOUTH:
        return NORTH;
      case WEST:
        return EAST;
      case EAST:
        return WEST;
      default:
        throw new IllegalStateException("This is unreachable");
    }
  }
}
