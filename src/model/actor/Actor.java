package model.actor;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.card.Card;

/**
 * <p>An actor is a "player" in the context of the game.
 * It should be able to place cards and contain them.</p>
 */
public interface Actor {
  /**
   * Prepares the actor for gameplay, by initializing its hand, color, and the
   * model of the game it is playing in.
   *
   * @param handSize the amount of cards this actor can hold.
   * @param deck     the initial deck of cards to draw from. It will draw handSize cards,
   *                 removing each from the deck.
   * @param color    the assigned color of the actor
   */
  void setup(int handSize, List<Card> deck, Color color);

  /**
   * Returns the player's move.
   * <p>The parameters chosen using selectCard() and selectCell().
   * The player itself does not need to call apply() on the move.</p>
   *
   * @return the card at the specified index
   * @throws IllegalArgumentException if index is invalid
   * @throws IllegalArgumentException if no card is to be found at the index.
   */
  Move makeMove();

  /**
   * Select a card from your hand to play.
   * <p>This does NOT play the card. Call makeMove() to make a move
   * with the selected card.</p>
   * <p>Select index -1 to deselect.</p>
   * <p>Only one card can be selected at a time.</p>
   *
   * @param handIdx the card to be picked from the hand
   */
  public void selectCard(int handIdx);

  /**
   * Select a cell on the grid to play to.
   * <p>This does NOT play the card. Call makeMove() to make a move
   * with the selected card.</p>
   * <p>Select index -1 to deselect.</p>
   * <p>Only one card can be selected at a time.</p>
   *
   * @param x horizontal index on the grid
   * @param y vertical index on the grid
   */
  public void selectCell(int x, int y);

  /**
   * Get a duplicate array of this actor's current hand.
   *
   * @return current state of this actor's hand
   */
  ArrayList<Card> getHand();

  /**
   * Check if this actor owns this card.
   * <p>Note: this only checks PLAYED CARDS on the grid,
   * and does not check if the card is in the player's hand
   * [use getHand.contains(card) for that].</p>
   *
   *
   * @param card card to check
   * @return true if this actor owns the card
   */
  boolean ownsCard(Card card);

  /**
   * Notify the player that they now own a given card ON THE GRID.
   * <p>It does not add the card to their hand, nor does it actually
   * play the card onto the grid.</p>
   *
   * @param card the card they now own
   */
  void gainCard(Card card);

  /**
   * Notify the player that they no longer own a given card ON THE GRID.
   * <p>It does not remove the card from their hand or from the grid.</p>
   *
   * @param card the card to remove
   * @throws IllegalArgumentException if card is not initially owned by the player.
   */
  void loseCard(Card card);

  /**
   * Returns this player's color.
   *
   * @return color assigned to this player
   */
  Color getColor();

  /**
   * See how many cards this actor owns currently.
   *
   * @return count of owned cards on the grid
   */
  int countOwnedCards();

  /**
   * Return the index of the currently selected card by this player.
   * If no card is selected, return -1.
   *
   * @return the index of the card currently selected by the player.
   */
  public int getSelectedCardIdx();


  /**
   * Return the coordinate of the selected cell, or null if no
   * cell is selected.
   *
   * @return the x,y coordinates of the selected cell.
   */
  public Point getSelectedCell();

  /**
   * Notify the player that it is their turn -- primarily useful
   * for computer-controlled players.
   */
  public void start();
}
