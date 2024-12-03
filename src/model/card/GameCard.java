package model.card;

import java.util.HashMap;
import java.util.Map;

/**
 * A card with attack values and ownership under red or blue status.
 */
public class GameCard implements Card {
  private final Map<Direction, AttackValue> values;
  private final String name;

  /**
   * Construct a card with specified values.
   *
   * @param north The north-facing value of the card.
   * @param east  The east-facing value of the card.
   * @param south The south-facing value of the card.
   * @param west  The west-facing value of the card.
   */
  public GameCard(String name,
                  AttackValue north,
                  AttackValue east,
                  AttackValue south,
                  AttackValue west) {
    this.name = name;
    values = new HashMap<>();
    values.put(Direction.NORTH, north);
    values.put(Direction.EAST, east);
    values.put(Direction.SOUTH, south);
    values.put(Direction.WEST, west);
  }

  @Override
  public String toString() {
    StringBuilder valrow = new StringBuilder();
    valrow.append(values.get(Direction.NORTH)).append(" ");
    valrow.append(values.get(Direction.SOUTH)).append(" ");
    valrow.append(values.get(Direction.EAST)).append(" ");
    valrow.append(values.get(Direction.WEST)).append(" ");
    return name + " " + valrow.toString().trim();
  }

  public int getScore() {
    int score = 0;
    for (AttackValue val : values.values()) {
      score += val.getValue();
    }
    return score;
  }

  public AttackValue getValue(Direction dir) {
    return values.get(dir);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof GameCard)) {
      return false;
    }

    GameCard other = (GameCard) o;
    if (!name.equals(other.name)) {
      return false;
    }
    for (Direction dir : Direction.values()) {
      if (!values.get(dir).equals(other.values.get(dir))) {
        return false;
      }
    }

    return true;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
