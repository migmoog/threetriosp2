package model.actor.strategies;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.Cell;
import model.ReadThreeTrios;
import model.actor.Actor;
import model.actor.Move;
import model.actor.ThreeTriosMove;
import model.card.GameCard;
import model.card.Card;

import model.card.Direction;

/**
 * Makes a move to a corner with cards that have the lowest chance of being flipped.
 */
public class PickCorners implements Strategy {
  @Override
  public Optional<Move> makeMove(ReadThreeTrios model, Actor caller) {
    Point topLeft = null;
    Point topRight = null;
    Point bottomLeft = null;
    Point bottomRight = null;

    Map<Point, Cell> grid = model.getCells();
    for (Point p : grid.keySet()) {
      if (grid.get(p).hasCard()) {
        continue;
      }

      if (topLeft == null || p.x <= topLeft.x && p.y <= topLeft.y) {
        topLeft = p;
      }
      if (topRight == null || p.x >= topRight.x && p.y <= topRight.y) {
        topRight = p;
      }
      if (bottomLeft == null || p.x <= bottomLeft.x && p.y >= bottomLeft.y) {
        bottomLeft = p;
      }
      if (bottomRight == null || p.x >= bottomRight.x && p.y >= bottomRight.y) {
        bottomRight = p;
      }
    }

    // scoring values by adding the attackvalues from their sides that face outward.
    Map<Point, Integer> corners = new HashMap<>();
    corners.put(topLeft, 0);
    corners.put(topRight, 0);
    corners.put(bottomLeft, 0);
    corners.put(bottomRight, 0);

    Card tlCard = null;
    Card trCard = null;
    Card brCard = null;
    Card blCard = null;
    List<Card> hand = caller.getHand();
    for (Card c : hand) {
      int tlScore = c.getValue(Direction.EAST).getValue() + c.getValue(Direction.SOUTH).getValue();
      if (tlScore > corners.get(topLeft)) {
        corners.put(topLeft, tlScore);
        tlCard = c;
      }

      int trScore = c.getValue(Direction.WEST).getValue() + c.getValue(Direction.SOUTH).getValue();
      if (trScore > corners.get(topRight)) {
        corners.put(topRight, trScore);
        trCard = c;
      }

      int blScore = c.getValue(Direction.EAST).getValue() + c.getValue(Direction.NORTH).getValue();
      if (blScore > corners.get(bottomLeft)) {
        corners.put(bottomLeft, blScore);
        blCard = c;
      }

      int brScore = c.getValue(Direction.WEST).getValue() + c.getValue(Direction.NORTH).getValue();
      if (brScore > corners.get(bottomRight)) {
        corners.put(bottomRight, brScore);
        brCard = c;
      }
    }

    Card outCard = null;
    Point outPoint = null;
    int highestScore = 0;
    for (Point p : corners.keySet()) {
      int curScore = corners.get(p);
      if (curScore > highestScore) {
        highestScore = curScore;

        if (p.equals(topLeft)) {
          outCard = tlCard;
          outPoint = topLeft;
        } else if (p.equals(topRight)) {
          outCard = trCard;
          outPoint = topRight;
        } else if (p.equals(bottomLeft)) {
          outCard = blCard;
          outPoint = bottomLeft;
        } else if (p.equals(bottomRight)) {
          outCard = brCard;
          outPoint = bottomRight;
        }
      }
    }
    return Optional.of(new ThreeTriosMove(outCard, outPoint));
  }
}
