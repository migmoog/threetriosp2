package hw8.adapters;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.ObservationalTriadModel;
import cs3500.threetrios.provider.model.PlayerColor;
import cs3500.threetrios.provider.model.Tile;
import model.Cell;
import model.ReadThreeTrios;
import model.actor.Actor;

/**
 * An implementation of our provider's read-only interface for their model. Using our own.
 */
public class ObservationalAdapter implements ObservationalTriadModel {
  private ReadThreeTrios readThreeTrios;

  /**
   * Create an adapter for a read-only three-trios model.
   *
   * @param readThreeTrios Our own Read-only three trios implementation
   *                       to convert to the provider's
   */
  public ObservationalAdapter(ReadThreeTrios readThreeTrios) {
    this.readThreeTrios = readThreeTrios;
  }

  @Override
  public boolean isGameOver() {
    return readThreeTrios.isGameOver();
  }

  @Override
  public Optional<PlayerColor> getWinningPlayer() {
    List<Actor> actors = readThreeTrios.getActors();
    return Optional.of(actors.get(readThreeTrios.winningPlayerIndex()).getColor().toProvider());
  }

  @Override
  public List<List<Tile>> getBoard() {
    int width = readThreeTrios.getWidth();
    int height = readThreeTrios.getHeight();
    List<List<Tile>> board = new ArrayList<>();
    for (int y = 0; y < height; y++) {
      board.add(new ArrayList<>());
      for (int x = 0; x < width; x++) {
        board.get(y).add(new TileAdapter(
                readThreeTrios.getCells().get(new Point(x, y)),
                readThreeTrios));
      }
    }
    return board;
  }

  @Override
  public PlayerColor getCurrentPlayer() {
    return readThreeTrios.getPlayerTurn().getColor().toProvider();
  }

  @Override
  public List<Card> getHand(PlayerColor player) {
    List<Card> providerDeck = new ArrayList<>();
    for (Actor p : readThreeTrios.getActors()) {
      if (p.getColor().toProvider().equals(player)) {
        for (model.card.Card c : p.getHand()) {
          providerDeck.add(new ProviderCardAdapter(c, readThreeTrios));
        }
      }
    }
    return providerDeck;
  }

  @Override
  public int getBoardWidth() {
    return readThreeTrios.getWidth();
  }

  @Override
  public int getBoardHeight() {
    return readThreeTrios.getHeight();
  }

  @Override
  public Tile getTileAt(int row, int col) {
    return new TileAdapter(readThreeTrios.getCells().get(new Point(col, row)), readThreeTrios);
  }

  @Override
  public PlayerColor getPlayerAt(int row, int col) {
    Point p = new Point(row, col);
    Map<Point, Cell> cells = readThreeTrios.getCells();
    if (!cells.get(p).hasCard()) {
      return null;
    }
    Actor owner = readThreeTrios.whoOwns(cells.get(p).getCard());
    return owner.getColor().toProvider();
  }

  // All the methods below seem to be related to gameplay and aren't needed by the view
  @Override
  public int getPossibleCardsFlipped(Card card, int row, int col) {
    return 0;
  }

  @Override
  public ObservationalTriadModel simulateMove(Card card, int row, int col) {
    return null;
  }

  @Override
  public int getPlayerScore(PlayerColor player) {
    return 0;
  }

  @Override
  public boolean checkValidMove(int row, int col, int handIdx, PlayerColor player) {
    return false;
  }

  @Override
  public boolean checkValidSpot(int row, int col) {
    return false;
  }

  @Override
  public int getHandIndexOfCard(Card card) {
    return 0;
  }
}