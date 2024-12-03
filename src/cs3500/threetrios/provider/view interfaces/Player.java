import java.util.Optional;

import cs3500.threetrios.model.ObservationalTriadModel;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.strategy.Move;

/**
 * Represents a player for the Triple Triad game. Players make moves and have a color, either
 * RED or BLUE.
 */
public interface Player {

  /**
   * Gets the next move from the player, based off implementation.
   *
   * @param model - the current state of the game
   * @return - The move the player's made (or empty, if the player is human and the program
   *        needs to wait for view callbacks)
   */
  Optional<Move> getNextMove(ObservationalTriadModel model);

  /**
   * Returns the color of the player.
   *
   * @return - the PlayerColor of this player.
   */
  PlayerColor getColor();
}
