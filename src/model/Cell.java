package model;

import model.card.Card;

/**
 * A cell, used to make up a grid. It can hold a card in it or be empty.
 */
public interface Cell {
  /**
   * Returns whether this cell holds a card.
   *
   * @return true if there is a card in this cell, false if not
   */
  boolean hasCard();

  /**
   * Return the card held in this cell.
   *
   * @return the card stored on this cell.
   */
  Card getCard();

  /**
   * Remove the card from this cell.
   */
  void removeCard();

  /**
   * Place a card in this cell.
   *
   * @param card The card to place on this cell.
   */
  void placeCard(Card card);
}
