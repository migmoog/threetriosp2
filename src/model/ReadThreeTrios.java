package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.actor.Actor;
import model.card.Card;
import model.rules.BattleRule;

/**
 * Strictly the observations for a `ThreeTriosGame` model. None of thee methods can
 * affect the game.
 */
public interface ReadThreeTrios {
  /**
   * Get a representation of the current gameBoard as a Map of Cells.
   * <p>Attempting to retrieve using a Point with no Cell at its position will
   * return null.</p>
   *
   * @return a duplicate of the current state of the grid.
   * @throws IllegalStateException if game has not started
   */
  Map<Point, Cell> getCells();

  /**
   * Check if no more cards can be played.
   *
   * @return true if every cell is filled
   * @throws IllegalStateException if game has not started
   */
  boolean isGameOver();

  /**
   * Check which player has won on the grid.
   *
   * @return the index of the player that has won the game
   * @throws IllegalStateException if the game isn't over
   */
  int winningPlayerIndex();

  /**
   * Returns an array of the respective players hand.
   *
   * @param playerIndex if true, get the red player's hand. Retrieve blue otherwise
   * @return the red or blue player's hand.
   * @throws IllegalStateException     if the game hasn't begun
   * @throws IndexOutOfBoundsException if given an invalid player index
   */
  ArrayList<Card> getPlayerHand(int playerIndex);

  /**
   * Get the amount of cards currently owned by the specified player.
   *
   * @param playerIndex index of the player to count the owned cards
   * @return amount of cards this player owns on the grid
   * @throws IllegalStateException     if the game hasn't begun
   * @throws IndexOutOfBoundsException if given an invalid player index
   */
  int countPlayersCards(int playerIndex);

  /**
   * Returns the width of the board.
   *
   * @return width of the total grid.
   */
  int getWidth();

  /**
   * Returns the height of the board.
   *
   * @return height of the total grid.
   */
  int getHeight();

  /**
   * Return the actor who owns a given card ON THE BOARD.
   * <p>It does not check cards in the players' hands.</p>
   *
   * @param c the card to search for.
   * @return the actor who owns the card currently.
   */
  Actor whoOwns(Card c);

  /**
   * Returns the player who's turn it is.
   *
   * @return the player whose turn it currently is.
   */
  Actor getPlayerTurn();

  /**
   * Returns the game's deck of cards.
   *
   * @return the list of cards that are used in the game.
   */
  List<Card> getDeck();

  /**
   * Returns the actors in the current game.
   *
   * @return The list of actors playing the current game.
   */
  List<Actor> getActors();

  /**
   * Retrieve the current rule that the model is using to judge spreads.
   *
   * @return the rule being used to judge wins.
   */
  BattleRule getRule();
}
