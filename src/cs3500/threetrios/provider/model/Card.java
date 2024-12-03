package cs3500.threetrios.provider.model;

/**
 * Behaviors for Cards in a game of TripleTriad.
 * Cards have a damage value in each direction, and a player that they are owned by.
 */
public interface Card {

  /**
   * Takes in a cs3500.threetrios.provider.model.Direction and returns the card's damage number for that direction.
   *
   * @param dir - direction to get damage for
   * @return - damage
   * @throws IllegalArgumentException if direction is null
   */
  int getDamage(Direction dir);

  /**
   * Gets the player that the card currently belongs to - red or blue.
   *
   * @return - the player that owns this card.
   */
  PlayerColor getPlayer();

  /**
   * Changes the player that this card belongs to.
   * If Red owns this card, it will change to Blue.
   * If Blue owns this card, it will change to Red.
   */
  void swapPlayer();

  /**
   * Gets the name of this card.
   *
   * @return the card's name
   */
  String getName();

  /**
   * Returns a copy of the current card to avoid mutability
   * concerns. Modifying the returned card does not modify this
   * card object.
   *
   * @return - copy of the current card.
   */
  Card copy();

}
