package hw8.adapters;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Tile;
import model.Cell;

/**
 * Adapter for cells to become tiles compatible with the provider's code.
 */
public class TileAdapter implements Tile {
  private Cell cell;
  public TileAdapter(Cell cell) {
    this.cell = cell;
  }

  @Override
  public boolean isHole() {
    if (cell == null) {
      return false;
    }
    return cell.hasCard();
  }

  @Override
  public Card getCard() {
    if (cell == null || !cell.hasCard()) {
      return null;
    }
    return
  }

  @Override
  public void setCard(Card card) {

  }

  @Override
  public Tile copy() {
    return null;
  }
}
