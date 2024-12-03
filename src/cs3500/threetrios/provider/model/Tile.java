package cs3500.threetrios.provider.model;

/**
 * Behaviours for Tiles on a TripleTriad board.
 * Tiles can hold a single card, unless the Tile is a hole.
 */
public interface Tile {

  /**
   * Returns whether this tile represents a hole, where no cards can be placed.
   *
   * @return true if this tile is a hole, false otherwise
   */
  boolean isHole();

  /**
   * Returns the card that is placed on this tile. If the tile is a hole or no card has been
   * placed, will return null.
   *
   * @return The card placed on this tile, or null if no card is placed or the tile is a hole
   */
  Card getCard();

  /**
   * Places the given card at this tile, so long as the tile is not a hole. Cards can be placed
   * even if another card is already placed, in which case the new card will replace it.
   *
   * @param card The card to place on this tile
   * @throws IllegalStateException if this tile is a hole
   */
  void setCard(Card card);

  /**
   * Returns a copy of this tile, so that mutations do not affect the original.
   *
   * @return a copy of this tile
   */
  Tile copy();
}
