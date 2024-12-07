package main;

import java.util.List;

import model.ThreeTriosGame;
import model.actor.Actor;

/**
 * An interface to read config files to start a model.
 */
public interface ConfigReader {
  /**
   * Reads a grid config and stores the required grid data needed
   * for setting up a ThreeTrios game.
   *
   * @param rd readable with the grid config
   */
  public void readGrid(Readable rd);

  /**
   * Reads in a card database config, then constructs and stores the deck.
   *
   * @param rd readable with the card data
   */
  public void readDeck(Readable rd);

  /**
   * Prepare the actors that the game will be played with.
   *
   * @param actors list of actors to play the game with
   * @throws IllegalArgumentException if the list is null or empty
   */
  public void readActors(List<Actor> actors);

  /**
   * Start the game with the read data.
   *
   * @param tt      three trios game to start
   * @param shuffle shuffle the deck on playing
   * @throws IllegalStateException if hasn't called readGrid, readDeck, or readActors
   */
  public void start(ThreeTriosGame tt, boolean shuffle);
}
