package cs3500.threetrios.provider.model;

import java.util.List;

/**
 * Interactive behaviours for a cs3500.threetrios.provider.model of TripleTriad.
 */
public interface TriadModel extends ObservationalTriadModel {

  /**
   * Places a card on the board in the provided row and column, 0-indexed, origin at
   * top left. Removes card from hand.
   *
   * @param row     - row to place the card in.
   * @param column  - column to place the card in.
   * @param handIdx - index of card in hand
   * @throws IllegalStateException    if game is over or hasn't started.
   * @throws IllegalArgumentException if handIdx is out of the range of the current player's hand
   * @throws IllegalArgumentException if card is null or row or column are out of bounds.
   * @throws IllegalStateException    if the position is a hole or already has a card placed
   */
  void placeCard(int row, int column, int handIdx);

  /**
   * Executes the battle phase, with the first tick centered on the provided card.
   * Steps of a battle tick:
   * 1. The origin card does battle against all adjacent cards, capturing adjacent cards
   * if their attack value on that side is lower.
   * 2. All swapped cards do battle with adjacent cards in the same fashion.
   * Step 2 continues until no cards are flipped.
   * Once all battle ticks are over, swaps the current player
   *
   * @throws IllegalStateException    if the game is over or hasn't started.
   * @throws IllegalArgumentException if the indices are out of bounds.
   * @throws IllegalArgumentException if the card at the indices given is not the current player'
   *                                  or null.
   */
  void doBattle(int row, int col);

  /**
   * Starts the game with the provided board layout and deck of Cards. This method must be called
   * prior to any other method of the cs3500.threetrios.provider.model.
   *
   * @param board   - the board to play on
   * @param deck    - the deck of cards to use in the game
   * @param shuffle - True if the deck should be shuffled prior to dealing, and false otherwise
   * @throws IllegalStateException    if the game has already been started
   * @throws IllegalArgumentException if the board, deck, or any card in the deck is null, or
   *                                  if any row or tile in the board is null
   * @throws IllegalArgumentException if any two cards in the deck share the same name
   * @throws IllegalArgumentException if the number of non-hole tiles is even or if there are
   *                                  fewer cards than one more than the number of non-hole tiles
   */
  void startGame(List<List<Tile>> board, List<Card> deck, boolean shuffle);
}
