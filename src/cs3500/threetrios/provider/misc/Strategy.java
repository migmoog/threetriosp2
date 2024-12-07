package cs3500.threetrios.provider.misc;

import java.util.List;

import cs3500.threetrios.provider.model.PlayerColor;
import cs3500.threetrios.provider.model.ObservationalTriadModel;

/**
 * Strategy interface. Strategies are function objects that determine the best possible moves
 * for a player to make, depending on how the strategy defines the "best move."
 */
public interface Strategy {
  /**
   * Given a particular model, return a list of moves depending on the player and the
   * implementation of the strategy.
   * The returned moves will have the best score determined by the strategy,
   * and be sorted by their position on the board and in the hand. If no move can be made
   * in accordance with the strategy, the top-leftmost empty spot and the card at handIndex 0 will
   * be selected.
   * Moves are sorted first by their top-leftmost place on the board, then by card index in hand.
   *
   * @param model - the current game
   * @return the list of sorted Moves
   * @throws IllegalArgumentException if cs3500.threetrios.provider.model is null
   */
  List<Move> getPossibleMoves(ObservationalTriadModel model, PlayerColor player);

}


