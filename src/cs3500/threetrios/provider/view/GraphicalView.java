package cs3500.threetrios.provider.view;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.PlayerColor;

/**
 * Behaviours for a view of a TripleTriad implementation, so that the user can see the game.
 */
public interface GraphicalView {

  /**
   * Makes sure the view is visible.
   */
  void setVisible();

  /**
   * Updates the output of the view such that it reflects
   * the cs3500.threetrios.provider.model's current state.
   *
   * @param myPlayer   - the player that this view belongs to
   * @param isGameOver - whether the game is over
   */
  void update(PlayerColor myPlayer, boolean isGameOver);

  /**
   * Adds a subscriber to give callbacks to.
   *
   * @param viewFeatures - the subscriber to send callbacks to.
   */
  void addFeatures(ViewFeatures viewFeatures);

  /**
   * Highlights a given card in the hand. If the card is already highlighted,
   * remove the highlight from that card.
   *
   * @param card - the card to toggle highlight on.
   */
  void highlightCard(Card card);

  /**
   * Returns the currently-highlighted card.
   *
   * @return - the currently highlighted card, or null if no card is highlighted.
   */
  Card getHighlightedCard();

  /**
   * Displays the given error message to the user.
   *
   * @param msg - the message to display.
   */
  void showError(String msg);
}
