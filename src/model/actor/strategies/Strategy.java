package model.actor.strategies;

import java.util.Optional;

import model.ReadThreeTrios;
import model.actor.Actor;
import model.actor.Move;

/**
 * A predetermined strategy on what move to make to a provided grid.
 * (i.e. "PickCorners" or "MostFlipped")
 */
public interface Strategy {
  /**
   * Calculates the optimal move a given player can make in a given game
   * using this strategy.
   *
   * @param model  data observations for the strategy to perform its logic.
   * @param caller the actor calling this strategy.
   * @return a move if it can make one successfully.
   */
  Optional<Move> makeMove(ReadThreeTrios model, Actor caller);
}
