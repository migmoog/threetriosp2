package hw8.adapters;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Tile;
import model.Cell;
import model.ReadThreeTrios;

/**
 * Adapter for cells to become tiles compatible with the provider's code.
 */
public class TileAdapter implements Tile {
  private Cell cell;
  private ReadThreeTrios observations;

  public TileAdapter(Cell cell, ReadThreeTrios model) {
    this.cell = cell;
    this.observations = model;
  }

  @Override
  public boolean isHole() {
    if (cell == null) {
      return true;
    }
    return false;
  }

  @Override
  public Card getCard() {
    if (cell == null || !cell.hasCard()) {
      return null;
    }
    return new ProviderCardAdapter(cell.getCard(), observations);
  }

  @Override
  public void setCard(Card card) {
    cell.placeCard(new OurCardAdapter(card));
  }

  @Override
  public Tile copy() {
    return new TileAdapter(cell, observations);
  }
}
