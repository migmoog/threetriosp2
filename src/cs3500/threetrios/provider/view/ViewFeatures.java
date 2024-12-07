package cs3500.threetrios.provider.view;

import cs3500.threetrios.provider.model.PlayerColor;

/**
 * Features interface for the view -- defines callbacks that the view will call,
 * which concrete classes extending this interface must implement.
 */
public interface ViewFeatures {

  /**
   * Callback for when a card from one of the player's hands is selected.
   *
   * @param index  - index of the card in the hand
   * @param player - player that owns the hand
   */
  void handCardSelected(int index, PlayerColor player);

  /**
   * Callback for when a board tile is selected.
   *
   * @param row - the row of the tile (0-indexed, top down)
   * @param col - the column of the tile (0-indexed, top down)
   */
  void boardTileSelected(int row, int col);
}
