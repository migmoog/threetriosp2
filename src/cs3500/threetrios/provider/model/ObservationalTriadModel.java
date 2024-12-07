package cs3500.threetrios.provider.model;

import java.util.List;
import java.util.Optional;

/**
 * Observational behaviours for a cs3500.threetrios.provider.model of TripleTriad.
 * Mutating any of the objects returned by these methods will have no effect on the game.
 */
public interface ObservationalTriadModel {

  /**
   * Returns a boolean representing whether the game is over. The game is
   * over when all non-hole cells are filled by a player's card.
   *
   * @return - true if game over, false otherwise.
   * @throws IllegalStateException if the game has not started.
   */
  boolean isGameOver();

  /**
   * Returns the player with the most cards in both their hand and board combined.
   *
   * @return - Optional containing the player that won, or empty in case of a tie.
   * @throws IllegalStateException if the game has not started.
   */
  Optional<PlayerColor> getWinningPlayer();

  /**
   * Returns a copy of the current board. Changing this board should have
   * no impact on the game.
   *
   * @return - copy of board.
   * @throws IllegalStateException if the game hasn't started.
   */
  List<List<Tile>> getBoard();

  /**
   * Returns the player whose turn it is.
   *
   * @return - current player.
   * @throws IllegalStateException if the game hasn't started or the game is over.
   */
  PlayerColor getCurrentPlayer();

  /**
   * Returns a copy of the player's hand. Changing this should have no impact on the game.
   *
   * @param player - which player's hand to return.
   * @return - hand of the provided player.
   * @throws IllegalStateException    if the game hasn't started.
   * @throws IllegalArgumentException if the supplied player is null.
   */
  List<Card> getHand(PlayerColor player);

  /**
   * Returns an integer representing the width of the board.
   *
   * @return - board width
   * @throws IllegalStateException if the game hasn't started.
   */
  int getBoardWidth();

  /**
   * Returns an integer representing the height of the board.
   *
   * @return - board height
   * @throws IllegalStateException if the game hasn't started.
   */
  int getBoardHeight();

  /**
   * Returns a copy of the tile found at a given row and column.
   * Modifying this copy will not modify the original tile.
   *
   * @param row - the row of the tile to copy
   * @param col - the column of the tile to copy
   * @return - a copy of the tile at row, col.
   * @throws IllegalStateException    if the game hasn't started.
   * @throws IllegalArgumentException if the row or column are out of bounds.
   */
  Tile getTileAt(int row, int col);

  /**
   * Returns the player which owns the card at the given row and column.
   * Returns null if no player owns the card at the given row or column.
   *
   * @param row - the row of the tile to check
   * @param col - the column of the tile to check
   * @return - the player that owns the card at the given row or column.
   * @throws IllegalStateException    if the game hasn't started.
   * @throws IllegalArgumentException if the row or column are out of bounds.
   */
  PlayerColor getPlayerAt(int row, int col);

  /**
   * Returns the number of cards flipped if the supplied card were to be played
   * to the supplied coordinates.
   *
   * @param card - card to simulate play
   * @param row  - destination row
   * @param col  - destination column
   * @return - the number of cards flipped if this play were to occur.
   * @throws IllegalStateException    if the game has started or is over.
   * @throws IllegalArgumentException if card is null or row, col out of bounds.
   */
  int getPossibleCardsFlipped(Card card, int row, int col);

  /**
   * Returns a copy of the current model with a particular move having been made.
   * Modifying this copy will not modify the original object.
   *
   * @param card - card to play
   * @param row  - destination row
   * @param col  - destination column
   * @return - a copy of the current cs3500.threetrios.provider.model with the move made.
   * @throws IllegalStateException    if the game is over or hasn't started.
   * @throws IllegalArgumentException if card is null or row, col out of bounds.
   */
  ObservationalTriadModel simulateMove(Card card, int row, int col);

  /**
   * Returns the number of cards belonging to a particular player on the board.
   *
   * @param player - the player to check the score of
   * @return - the score of that player.
   * @throws IllegalStateException    if game hasn't started.
   * @throws IllegalArgumentException if player is null.
   */
  int getPlayerScore(PlayerColor player);

  /**
   * Checks whether the given move of a specific player is a legal move to play.
   *
   * @param row     the row to play to
   * @param col     the column to play to
   * @param handIdx the handIdx of the card to play
   * @param player  the player to play for
   * @return true if the move is valid, and false otherwise
   */
  boolean checkValidMove(int row, int col, int handIdx, PlayerColor player);

  /**
   * Checks if the given position on the board can have any card played to it.
   *
   * @param row the row to check
   * @param col the column to check
   * @return true if the tile is empty, and false if it is a hole or has a card
   */
  boolean checkValidSpot(int row, int col);

  /**
   * Given a card, returns the index of the card in either player's hand. Since cards are unique,
   * there will only be one possible index to return.
   *
   * @param card - the card to find the index of.
   * @return - the index of the card.
   * @throws IllegalArgumentException if the card does not exist in either player's hand.
   */
  int getHandIndexOfCard(Card card);
}
