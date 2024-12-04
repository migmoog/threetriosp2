package model.card;

/**
 * <p>A cardinal direction on the board.
 * Can face North, East, South, or West.</p>
 */
public enum Direction {
  NORTH, EAST, SOUTH, WEST, NORTH_EAST, SOUTH_EAST;

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

  public cs3500.threetrios.provider.model.Direction toProvider() {
    for (cs3500.threetrios.provider.model.Direction providerD :
            cs3500.threetrios.provider.model.Direction.values()) {
      if (providerD.name().equals(this.name())) {
        return providerD;
      }
    }

    throw new IllegalStateException("Direction can not be converted.");
  }

  public static Direction fromProvider(cs3500.threetrios.provider.model.Direction providerD) {
    for (Direction d : Direction.values()) {
      if (providerD.name().equals(d.name())) {
        return d;
      }
    }
    throw new IllegalStateException("Direction can not be converted.");
  }
}
