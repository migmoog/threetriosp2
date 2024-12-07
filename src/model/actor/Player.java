package model.actor;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.card.Card;

/**
 * A player of Three Trios that has its own cards on the board and in its hand.
 */
public class Player implements Actor {
  private ArrayList<Card> hand;
  private List<Card> allCards = new ArrayList<>();
  private Color color;

  private int moveX;
  private int moveY;
  private int moveHandIdx;

  @Override
  public void setup(int handSize, List<Card> deck, Color color) {
    hand = new ArrayList<>();
    this.color = color;
    for (int i = 0; i < handSize; i++) {
      Card c = deck.remove(0);
      hand.add(c);
    }
    moveHandIdx = -1;
  }

  @Override
  public Move makeMove() {
    Card out = hand.remove(moveHandIdx);
    allCards.add(out);
    moveHandIdx = -1;
    return new ThreeTriosMove(out, new Point(moveX, moveY));
  }

  /**
   * Prepare the player to place a card at a position.
   *
   * @param x horizontal index on the grid
   * @param y vertical index on the grid
   */
  public void selectCell(int x, int y) {
    moveX = x;
    moveY = y;
  }

  @Override
  public void start() {
    // ignorable -- human player can play whenever it wants
  }

  @Override
  public void selectCard(int handIdx) {
    moveHandIdx = handIdx;
  }

  @Override
  public int getSelectedCardIdx() {
    return moveHandIdx;
  }

  @Override
  public Point getSelectedCell() {
    return new Point(moveX, moveY);
  }

  @Override
  public ArrayList<Card> getHand() {
    return new ArrayList<Card>(this.hand);
  }

  @Override
  public boolean ownsCard(Card card) {
    return this.allCards.contains(card);
  }

  @Override
  public void gainCard(Card card) {
    if (ownsCard(card)) {
      return;
    }
    allCards.add(card);
  }

  @Override
  public void loseCard(Card card) {
    if (!ownsCard(card)) {
      throw new IllegalArgumentException("Does not own card to remove it");
    }
    allCards.remove(card);
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public int countOwnedCards() {
    return allCards.size();
  }
}
