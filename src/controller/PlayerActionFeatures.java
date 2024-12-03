package controller;

import java.awt.Point;

import model.actor.Actor;

/**
 * The actions that a player can directly do to affect the game.
 */
public interface PlayerActionFeatures {
  /**
   * Select a card from a player's hand to be played later.
   *
   * @param player the player whose card it is.
   * @param index  the index of the card in the player's hand to select.
   * @throws IllegalArgumentException if the actor is null or not in the game.
   * @throws IllegalArgumentException if the index does not correlate to a valid card.
   */
  public void selectCard(Actor player, int index);

  /**
   * Select a cell on the board to play a card to.
   *
   * @param location the x-y coordinate of the cell on the board.
   * @throws IllegalArgumentException if the actor is null or not in the game.
   * @throws IllegalArgumentException if the point is not a valid cell with an empty card.
   */
  public void selectCell(Point location);
}
