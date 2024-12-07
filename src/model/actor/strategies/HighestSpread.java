package model.actor.strategies;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import model.Cell;
import model.ReadThreeTrios;
import model.actor.Actor;
import model.actor.Move;
import model.actor.ThreeTriosMove;
import model.card.Card;
import model.card.Direction;
import model.rules.BattleRule;

/**
 * A strategy that uses the lowest scored card with the highest possible spread.
 */
public class HighestSpread implements Strategy {
  private BattleRule rule;
  private Map<Point, Cell> grid;
  private Actor caller;

  @Override
  public Optional<Move> makeMove(ReadThreeTrios model, Actor caller) {
    rule = model.getRule();
    this.caller = caller;
    grid = model.getCells();
    Map<Point, Cell> grid = model.getCells();
    List<Point> openSpots = grid.keySet()
            .stream()
            .filter(p -> !grid.get(p).hasCard())
            .collect(Collectors.toList());

    ThreeTriosMove bestMove = null;
    int maxSpread = 0;
    for (Point p : openSpots) {
      Optional<Card> c = pickBestCard(p);
      if (c.isEmpty()) {
        continue;
      }
      Card card = c.get();
      int potential = spreadPotential(p, card);
      if (potential > maxSpread) {
        bestMove = new ThreeTriosMove(card, p);
        maxSpread = potential;
      }
    }

    if (bestMove == null) {
      return Optional.empty();
    }
    return Optional.of(bestMove);
  }

  /**
   * Picks the best card to play to the specified cell (if it can).
   *
   * @param origin the point the strategy wants to play to
   * @return the lowest scored card that can be played to
   */
  Optional<Card> pickBestCard(Point origin) {
    Map<Direction, Point> neighbors = getNeighbors(origin);
    // collect cells that we can actually fight
    Map<Direction, Card> enemies = new HashMap<>();
    for (Direction d : neighbors.keySet()) {
      Cell cell = grid.get(neighbors.get(d));

      if (isNotEnemyCell(cell)) {
        continue;
      }

      enemies.put(d, cell.getCard());
    }

    List<Card> hand = caller.getHand();
    List<Card> playables = new ArrayList<>();
    List<Integer> sidesBeaten = new ArrayList<>();
    for (Card c : hand) {
      boolean takeable = false;
      int beatenSides = 0;
      for (Direction d : enemies.keySet()) {
        // check to see if the neighbor can defeat this card
        if (rule.fight(enemies.get(d), c, d.getNeighbor())) {
          takeable = true;
          break;
        }
        // check how many sides we can overtake
        if (rule.fight(c, enemies.get(d), d)) {
          beatenSides++;
        }
      }

      if (!takeable) {
        playables.add(c);
        sidesBeaten.add(beatenSides);
      }
    }

    if (playables.isEmpty()) {
      return Optional.empty();
    }

    // find the card with the lowest
    int minCardIdx = 0;
    for (int i = 1; i < playables.size(); i++) {
      int minSideCount = sidesBeaten.get(minCardIdx);
      int currentSideCount = sidesBeaten.get(i);

      Card min = playables.get(minCardIdx);
      Card current = playables.get(i);

      // if the current
      if (currentSideCount > minSideCount || current.getScore() < min.getScore()) {
        minCardIdx = i;
      }
    }

    return Optional.of(playables.get(minCardIdx));
  }

  /**
   * Count how many cards the actor will own based on the provided move.
   *
   * @param origin the starting point of the move.
   * @param card   the card being played to the cell.
   * @return the amount of cards the actor will own by the end of the battle phase.
   */
  private int spreadPotential(Point origin, Card card) {
    return spreadPotentialHelp(origin, card, new ArrayList<>());
  }

  private int spreadPotentialHelp(Point origin, Card card, List<Point> visited) {
    if (visited.contains(origin)) {
      return 0;
    }
    visited.add(origin);

    Map<Direction, Point> neighbors = getNeighbors(origin);

    int spreadAmount = 0;
    for (Direction d : neighbors.keySet()) {
      Point p = neighbors.get(d);
      Cell c = grid.get(p);
      // reject if there is: No cell, cell is empty, or this actor already owns this card.
      if (isNotEnemyCell(c)) {
        continue;
      }

      Card defender = c.getCard();
      if (rule.fight(card, defender, d)) {
        spreadAmount += 1 + spreadPotentialHelp(p, card, visited);
      }
    }

    return spreadAmount;
  }

  /**
   * Constructs a map of the neighboring points to an origin.
   *
   * @param origin the starting point to retrieve neighbors from.
   * @return map of directions to the origin's neighboring points.
   */
  private Map<Direction, Point> getNeighbors(Point origin) {
    int x = origin.x;
    int y = origin.y;
    Map<Direction, Point> returnHash = new HashMap<>();
    returnHash.put(Direction.NORTH, new Point(x, y - 1));
    returnHash.put(Direction.SOUTH, new Point(x, y + 1));
    returnHash.put(Direction.WEST, new Point(x - 1, y));
    returnHash.put(Direction.EAST, new Point(x + 1, y));
    return returnHash;
  }

  /**
   * Checks if this cell contains an enemy card.
   *
   * @param c the cell being checked.
   * @return true if the cell is filled with a card that doesn't belong to caller.
   */
  private boolean isNotEnemyCell(Cell c) {
    return c == null || !c.hasCard() || caller.ownsCard(c.getCard());
  }
}
