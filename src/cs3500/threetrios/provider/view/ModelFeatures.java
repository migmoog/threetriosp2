package cs3500.threetrios.provider.view;

/**
 * Features interface for the cs3500.threetrios.provider.model -- defines callbacks that the cs3500.threetrios.provider.model will call,
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
