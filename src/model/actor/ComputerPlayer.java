package model.actor;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import controller.PlayerActionFeatures;
import model.ReadThreeTrios;
import model.actor.strategies.Strategy;
import model.card.Card;


/**
 * A decorator object for a computer player.
 */
public class ComputerPlayer implements Actor {
  private Actor delegate;
  private ReadThreeTrios observations;
  private List<Strategy> strategies;
  private ThreeTriosMove move;
  private PlayerActionFeatures controller;
  private int moveX;
  private int moveY;
  private int moveHandIdx;

  public ComputerPlayer(ReadThreeTrios observations, List<Strategy> strategies) {
    this.strategies = new ArrayList<>(strategies);
    this.observations = observations;
  }

  public void setController(PlayerActionFeatures controller) {
    this.controller = controller;
  }

  @Override
  public void setup(int handSize, List<Card> deck, Color color) {
    delegate = new Player();
    delegate.setup(handSize, deck, color);
    moveHandIdx = -1;
  }

  @Override
  public Move makeMove() {
    delegate.makeMove();
    this.moveHandIdx = -1;
    return this.move;
  }

  @Override
  public ArrayList<Card> getHand() {
    return delegate.getHand();
  }

  @Override
  public boolean ownsCard(Card card) {
    return delegate.ownsCard(card);
  }

  @Override
  public void gainCard(Card card) {
    delegate.gainCard(card);
  }

  @Override
  public void loseCard(Card card) {
    delegate.loseCard(card);
  }

  @Override
  public Color getColor() {
    return delegate.getColor();
  }

  @Override
  public int countOwnedCards() {
    return delegate.countOwnedCards();
  }

  @Override
  public int getSelectedCardIdx() {
    return moveHandIdx;
  }

  @Override
  public Point getSelectedCell() {
    return new Point(this.moveX, this.moveY);
  }

  @Override
  public void selectCell(int x, int y) {
    // controller told computer to select its chosen position
    this.moveX = x;
    this.moveY = y;
    delegate.selectCell(x, y);
  }

  @Override
  public void start() {
    checkControllerError();
    // calculate optimal move and store it in this.move
    calculateMove();

    // find hand index of optimal card to play from calculated move
    int selectedIdx = 0;
    for (int c = 0; c < delegate.getHand().size(); c++) {
      if (delegate.getHand().get(c).equals(move.getCard())) {
        selectedIdx = c;
      }
    }
    // tell controller that the computer wants to select that card
    controller.selectCard(this, selectedIdx);
  }

  @Override
  public void selectCard(int handIdx) {
    checkControllerError();
    // controller told computer to select the given card
    this.moveHandIdx = handIdx;
    delegate.selectCard(handIdx);
    // tell controller that the computer wants to select the computed position
    controller.selectCell(new Point(move.getPosition().x, move.getPosition().y));
  }

  private void calculateMove() {
    int i = 0;
    Optional<Move> out = strategies.get(i).makeMove(observations, this);
    for (; out.isEmpty(); i++) {
      if (i >= strategies.size()) {
        throw new IllegalStateException("No move can be made by the computer player");
      }

      out = strategies.get(i).makeMove(observations, this);
    }
    // find index of the card and select it in the delegate
    this.move = (ThreeTriosMove) out.get();
  }

  private void checkControllerError() {
    if (this.controller == null) {
      throw new IllegalStateException("No controller selected for computer player. " +
              "Add one with selectController().");
    }
  }
}
