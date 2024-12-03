package controller;

import model.actor.Actor;

/**
 * Status notifications that can be sent from the model that affect the view.
 */
public interface ModelStatusFeatures {
  /**
   * Signify the beginning of a specific player's turn.
   *
   * @param actor the player whose turn has begun.
   * @throws IllegalArgumentException if the actor is null or not in the game.
   */
  public void beginTurn(Actor actor);

  /**
   * Signify the end of a specific player's turn.
   *
   * @param actor the player whose turn has ended.
   * @throws IllegalArgumentException if the actor is null or not in the game.
   */
  public void endTurn(Actor actor);

  /**
   * Notify whenever the board is updated--like when a card is placed onto the grid
   * or lost from your hand.
   */
  public void updateBoard();
}
