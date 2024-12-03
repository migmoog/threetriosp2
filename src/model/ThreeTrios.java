package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import controller.ModelStatusFeatures;
import controller.ModelStatusListeners;
import model.actor.Actor;
import model.actor.Color;
import model.card.Card;
import model.card.GameCard;
import model.card.Direction;
import model.rules.BattleRule;
import model.rules.GreaterRule;

/**
 * A game of three trios.
 */
public class ThreeTrios implements ThreeTriosGame {
  private final Random rand;
  private boolean unstarted = true;
  // main data used throughout the entire class
  private Map<Point, Cell> grid;
  private List<Card> deck;
  private List<Actor> actors;
  private int currentPlayer;
  private Point dimensions;

  // playing game information
  private GamePhase phase;
  private BattleRule rule = new GreaterRule();

  // battle phase spread info
  private Point spreadOrigin;

  // features listener
  private ModelStatusListeners features;


  /**
   * Construct a game of three trios with a specified seed.
   *
   * @param r seeded random object
   */
  public ThreeTrios(Random r) {
    this.rand = r;
    this.actors = new ArrayList<>();
    this.features = new ModelStatusListeners();
  }

  /**
   * <p>Construct a game of three trios. Use `start` to actually play the game.
   * Will use Java's default seeding methods. </p>
   */
  public ThreeTrios() {
    this(new Random());
  }

  @Override
  public Map<Point, Cell> getCells() {
    checkBegan();
    Map<Point, Cell> out = new HashMap<>();
    for (Point p : grid.keySet()) {
      out.put(p, grid.get(p));
    }
    return out;
  }

  @Override
  public boolean isGameOver() {
    checkBegan();
    for (Point p : grid.keySet()) {
      if (!grid.get(p).hasCard()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int winningPlayerIndex() {
    if (!isGameOver()) {
      throw new IllegalStateException("Cannot determine winner if game hasn't ended");
    }

    int winningIndex = 0;
    for (int i = 1; i < actors.size(); i++) {
      if (actors.get(i).countOwnedCards() > actors.get(winningIndex).countOwnedCards()) {
        winningIndex = i;
      }
    }

    return winningIndex;
  }

  @Override
  public ArrayList<Card> getPlayerHand(int playerIndex) {
    checkBegan();
    checkPlayerIndex(playerIndex);
    return actors.get(playerIndex).getHand();
  }

  @Override
  public int countPlayersCards(int playerIndex) {
    checkBegan();
    checkPlayerIndex(playerIndex);
    return actors.get(playerIndex).countOwnedCards();
  }

  @Override
  public void placeCard() {
    checkBegan();
    if (phase != GamePhase.PLACING) {
      throw new IllegalStateException("Battle phase must commence before placing another card.");
    }

    spreadOrigin = getPlayerTurn().makeMove().apply(this.grid);
    phase = GamePhase.BATTLE;

    features.updateBoard();
  }

  @Override
  public void battle() {
    checkBegan();
    if (phase != GamePhase.BATTLE) {
      throw new IllegalStateException("Cannot do battle if player hasn't placed a card yet");
    }

    spread(spreadOrigin);
    phase = GamePhase.PLACING;
    features.updateBoard();
    features.endTurn(actors.get(currentPlayer));
    currentPlayer = (currentPlayer + 1) % actors.size();
    features.beginTurn(actors.get(currentPlayer));
  }

  /**
   * From the specified point, spread the cards along the battle phase.
   *
   * @param p location of the card on the offense
   */
  private void spread(Point p) {
    Actor current = actors.get(currentPlayer);
    Map<Direction, Point> neighbors = Map.of(
            Direction.WEST, new Point(p.x - 1, p.y), // get left neighbor
            Direction.EAST, new Point(p.x + 1, p.y), // get right neighbor
            Direction.NORTH, new Point(p.x, p.y - 1), // get above neighbor
            Direction.SOUTH, new Point(p.x, p.y + 1)); // get below neighbor
    Card attacker = grid.get(p).getCard();

    for (Direction dir : neighbors.keySet()) {
      Point pnt = neighbors.get(dir);
      Card c;
      if (grid.get(pnt) != null) {
        c = grid.get(pnt).getCard();
      } else {
        c = null;
      }
      // filter out unuseables
      if (c == null || current.ownsCard(c)) {
        continue;
      }

      // fight
      if (rule.fight(attacker, c, dir)) {
        int otherActorIdx = (currentPlayer + 1) % actors.size();
        // transferring ownership
        actors.get(otherActorIdx).loseCard(c);
        actors.get(currentPlayer).gainCard(c);
        spread(pnt); // continue the spread
      }
    }
  }

  @Override
  public void start(boolean shuffle, List<Card> givenDeck
          , boolean[][] gridConfig, List<Actor> actors) {
    // check that every row in gridConfig has the same length
    for (int y = 1; y < gridConfig.length; y++) {
      for (int x = 0; x < gridConfig[y].length; x++) {
        if (gridConfig[y].length != gridConfig[y - 1].length) {
          throw new IllegalArgumentException("gridConfig must be rectangular");
        }
      }
    }

    dimensions = new Point(gridConfig[0].length, gridConfig.length);

    int cardCellCount = 0;
    grid = new HashMap<>();
    for (int y = 0; y < gridConfig.length; y++) {
      for (int x = 0; x < gridConfig[y].length; x++) {
        if (!gridConfig[y][x]) {
          continue;
        }
        grid.put(new Point(x, y), new BoardCell()); // fill our map with cells
        cardCellCount++; // count how many cells we actually have
      }
    }

    // checks
    if (cardCellCount % 2 == 0) {
      throw new IllegalArgumentException("There must be an odd number of card cells.");
    } else if (givenDeck.size() < cardCellCount + 1) {
      throw new IllegalArgumentException("Not enough cards to start the game.");
    }

    // deck shuffling
    this.deck = new ArrayList<>(givenDeck);
    if (shuffle) {
      int shuffleCount = rand.nextInt(this.deck.size() / 2) + 1;
      for (int i = 0; i < shuffleCount; i++) {
        int idx1 = rand.nextInt(this.deck.size());
        int idx2 = rand.nextInt(this.deck.size());
        if (idx1 == idx2) { // no same swaps!
          i--;
          continue;
        }

        Card temp = this.deck.get(idx1);
        this.deck.set(idx1, this.deck.get(idx2));
        this.deck.set(idx2, temp);
      }
    }

    // hand initialization
    int handSize = (cardCellCount + 1) / 2;
    this.actors = new ArrayList<>(actors);
    int colorIdx = 0;

    // player intialization
    List<Color> colors = List.of(
            Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.YELLOW, Color.PURPLE);
    if (actors.size() > colors.size()) {
      throw new IllegalArgumentException("Not enough colors listed. There are "+
              colors.size()+" colors for "+actors.size()+" actors.");
    }
    Color[] colorArray = new Color[actors.size()];
    for (int i = 0; i < actors.size(); i++) {
      colorArray[i] = colors.get(i);
    }
    for (Actor a : actors) {
      a.setup(handSize, deck, colorArray[colorIdx++]);
    }
    unstarted = false;
    currentPlayer = 0;
    phase = GamePhase.PLACING;
  }

  @Override
  public void addFeaturesListener(ModelStatusFeatures features) {
    this.features.add(features);

    // notify that the first player's turn has begun
    this.features.beginTurn(getPlayerTurn());
  }

  /**
   * Checks if start has been called and throws an exception if it hasn't.
   */
  private void checkBegan() {
    if (unstarted) {
      throw new IllegalStateException("Game hasn't started.");
    }
  }

  private void checkPlayerIndex(int playerIndex) {
    if (playerIndex < 0 || playerIndex >= actors.size()) {
      throw new IndexOutOfBoundsException(
              "Provided player index should be from 0 to " + actors.size());
    }
  }


  @Override
  public int getWidth() {
    return dimensions.x;
  }

  @Override
  public int getHeight() {
    return dimensions.y;
  }

  @Override
  public Actor whoOwns(Card c) {
    for (Actor a : actors) {
      if (a.ownsCard(c)) {
        return a;
      }
    }
    return null;
  }

  @Override
  public Actor getPlayerTurn() {
    return actors.get(currentPlayer);
  }

  @Override
  public List<Card> getDeck() {
    return deck;
  }

  @Override
  public List<Actor> getActors() {
    return new ArrayList<>(actors);
  }

  @Override
  public BattleRule getRule() {
    return rule;
  }
}
