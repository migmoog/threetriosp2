package model.card;

/**
 * A card with AttackValues associated with it.
 */
public interface Card {
  /**
   * Calculates the value of this card.
   *
   * @return the sum of all the card's attack values
   */
  public int getScore();

  /**
   * Gets the value of the card at a specified direction.
   *
   * @param dir Direction of the card to retrieve
   * @return integer value
   */
  public AttackValue getValue(Direction dir);


}
