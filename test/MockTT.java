import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.ModelStatusFeatures;
import model.BoardCell;
import model.Cell;

import model.ThreeTriosGame;
import model.actor.Actor;
import model.actor.Color;
import model.actor.Player;
import model.card.AttackValue;
import model.card.Card;
import model.card.GameCard;

import model.rules.BattleRule;

/**
 * A mock model that logs calls to the interface's functions.
 */
public class MockTT implements ThreeTriosGame {
  Appendable ap;

  public MockTT(Appendable ap) {
    this.ap = ap;
  }

  private void log(String s) {
    try {
      ap.append(s);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void placeCard() {
    log("Placed a card");
  }

  @Override
  public void battle() {
    log("Commencing battle phase");
  }

  @Override
  public void start(boolean shuffle, List<Card> deck, boolean[][] gridConfig, List<Actor> actors) {
    log(String.format("Began the game with a deck of size %d", deck.size()));
  }

  @Override
  public void addFeaturesListener(ModelStatusFeatures features) {
    // ignored
  }

  @Override
  public Map<Point, Cell> getCells() {
    Map<Point, Cell> returnHash = new HashMap<Point, Cell>();
    returnHash.put(new Point(0, 0), new BoardCell());
    returnHash.put(new Point(1, 0), new BoardCell());
    returnHash.put(new Point(2, 0), new BoardCell());
    returnHash.put(new Point(0, 1), new BoardCell());
    returnHash.put(new Point(1, 1), new BoardCell());
    returnHash.put(new Point(2, 1), new BoardCell());
    returnHash.put(new Point(0, 2), new BoardCell());
    returnHash.put(new Point(1, 2), new BoardCell());
    returnHash.put(new Point(2, 2), new BoardCell());
    return returnHash;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public int winningPlayerIndex() {
    return 0;
  }

  @Override
  public ArrayList<Card> getPlayerHand(int playerIndex) {
    return null;
  }

  @Override
  public int countPlayersCards(int playerIndex) {
    return 0;
  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public int getHeight() {
    return 0;
  }

  @Override
  public Actor whoOwns(Card c) {
    return null;
  }

  @Override
  public Actor getPlayerTurn() {
    return null;
  }

  @Override
  public List<Card> getDeck() {
    List<Card> returnDeck = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      returnDeck.add(new GameCard("",
              AttackValue.ONE, AttackValue.ONE, AttackValue.ONE, AttackValue.ONE));
    }
    return returnDeck;
  }

  @Override
  public List<Actor> getActors() {
    Player returnPlayer = new Player();
    returnPlayer.setup(5, this.getDeck(), Color.RED);
    return List.of(returnPlayer);
  }

  @Override
  public BattleRule getRule() {
    return null;
  }
}
