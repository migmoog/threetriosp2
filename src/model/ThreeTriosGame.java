package model;

import java.util.List;

import controller.ModelStatusFeatures;
import model.actor.Actor;
import model.card.Card;

/**
 * Interactable functions for a ThreeTriosGame..
 */
public interface ThreeTriosGame extends ReadThreeTrios {
  /**
   * While in the playing state, play the current players' selected card to their
   * selected position.
   *
   * @throws IllegalArgumentException if given invalid coordinates
   * @throws IllegalArgumentException if played to a hole
   * @throws IllegalArgumentException if played to a filled cell
   * @throws IllegalArgumentException if in battle phase
   */
  void placeCard();

  /**
   * Battle the most recent card against its adjacent cards.
   *
   * @throws IllegalStateException if in placing state.
   */
  void battle();

  /**
   * Begin the game with a specified number of cells and a starting deck.
   *
   * @param shuffle    Shuffle the provided deck of cards before starting the game
   * @param deck       list of cards to initially fill the players' hands.
   * @param gridConfig <p>2D array that represents what the game's grid looks like.
   *                   Trues represent a cell, falses represent a hole.</p>
   * @param actors     The list of actors that place cards in the game.
   * @throws IllegalArgumentException if cardCellCount is not odd
   * @throws IllegalArgumentException if deck's size isn't >= cardCellCount + 1
   */
  void start(boolean shuffle, List<Card> deck,
             boolean[][] gridConfig, List<Actor> actors);

  /**
   * Connect a features listener to the model to emit feature notifications to.
   *
   * @param features the object that listens for feature notifications.
   */
  void addFeaturesListener(ModelStatusFeatures features);
}
