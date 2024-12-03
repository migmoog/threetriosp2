package model.actor;

import java.awt.Point;
import java.util.Map;

import model.Cell;
import model.card.Card;

/**
 * A move that can be played to the grid.
 */
public class ThreeTriosMove implements Move {
  private final Card card;
  private final Point position;

  /**
   * Construct a new move to played to the model.
   *
   * @param card     the card being played to the grid.
   * @param position the position of the cell the card is being played to.
   */
  public ThreeTriosMove(Card card, Point position) {
    this.card = card;
    this.position = position;
  }

  @Override
  public Point apply(Map<Point, Cell> grid) {
    grid.get(position).placeCard(card);
    return position;
  }

  public Card getCard() {
    return card;
  }

  public Point getPosition() {
    return position;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof ThreeTriosMove)) {
      return false;
    }

    ThreeTriosMove other = (ThreeTriosMove) obj;
    return card.equals(other.card) && position.equals(other.position);
  }

  @Override
  public int hashCode() {
    return card.hashCode() + position.hashCode();
  }
}
