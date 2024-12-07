package cs3500.threetrios.provider.view;

import cs3500.threetrios.provider.model.PlayerColor;

/**
 * Features interface for the model -- defines callbacks that the model will call,
 * which concrete classes extending this interface must implement.
 */
public interface ModelFeatures {

  /**
   * Callback for a change of turn happening on the cs3500.threetrios.provider.model's end.
   *
   * @param newPlayer - the cs3500.threetrios.provider.model's current player.
   */
  void changeOfTurn(PlayerColor newPlayer);

  /**
   * Callback for the game finishing.
   */
  void gameOver();
}
