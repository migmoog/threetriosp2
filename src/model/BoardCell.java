package model;

import model.card.Card;

/**
 * A cell that can be placed on the game grid, and can hold a card.
 */
public class BoardCell implements Cell {
  private Card card;

  /**
   * A default cell with no card assigned to it.
   */
  public BoardCell() {
    this(null);
  }

  /**
   * A cell with a card assigned to.
   *
   * @param card The card to assign to the cell.
   */
  public BoardCell(Card card) {
    this.card = card;
  }

  /**
   * Returns whether this cell holds a card.
   *
   * @return true if there is a card in this cell, false if not
   */
  public boolean hasCard() {
    return this.card != null;
  }

  /**
   * Return the card held in this cell.
   *
   * @return the card stored on this cell.
   */
  public Card getCard() {
    return this.card;
  }

  /**
   * Remove the card from this cell.
   */
  public void removeCard() {
    this.card = null;
  }

  /**
   * Place a card on this cell.
   *
   * @param card The card to place on this cell.
   */
  public void placeCard(Card card) {
    this.card = card;
  }
}
