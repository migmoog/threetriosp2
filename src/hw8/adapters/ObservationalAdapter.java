package hw8.adapters;

import java.util.List;
import java.util.Optional;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.ObservationalTriadModel;
import cs3500.threetrios.provider.model.PlayerColor;
import cs3500.threetrios.provider.model.Tile;
import model.ReadThreeTrios;
import model.actor.Actor;

/**
 * An implementation of our provider's read-only interface for their model. Using our own.
 */
public class ObservationalAdapter implements ObservationalTriadModel {
  private ReadThreeTrios readThreeTrios;

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

  }

  @Override
  public PlayerColor getCurrentPlayer() {
    return readThreeTrios.getPlayerTurn().getColor().toProvider();
  }

  @Override
  public List<Card> getHand(PlayerColor player) {
    
  }

  @Override
  public int getBoardWidth() {
    return 0;
  }

  @Override
  public int getBoardHeight() {
    return 0;
  }

  @Override
  public Tile getTileAt(int row, int col) {
    return null;
  }

  @Override
  public PlayerColor getPlayerAt(int row, int col) {
    return null;
  }

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

  @Override
  public void addFeatures(ModelFeatures features) {

  }
}
