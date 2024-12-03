import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.ThreeTriosGame;
import model.actor.Actor;
import model.card.AttackValue;
import model.card.Card;
import model.card.GameCard;

/**
 * Reads in a config file from a readable and creates a model.
 */
public class TTConfigReader implements ConfigReader {
  private boolean[][] grid;
  private List<Card> deck;
  private List<Actor> actors;

  @Override
  public void readGrid(Readable rd) {
    Scanner sc = new Scanner(rd);

    int rows = sc.nextInt();
    int cols = sc.nextInt();
    grid = new boolean[rows][cols];
    int y = 0;
    while (sc.hasNext() && y < rows) {
      String line = sc.next();

      for (int x = 0; x < line.length(); x++) {
        String spot = line.substring(x, x + 1);
        if (spot.equals("C")) {
          grid[y][x] = true;
        } else if (spot.equals("X")) {
          grid[y][x] = false;
        } else {
          throw new IllegalArgumentException("Not a valid grid config character: " + spot);
        }
      }
      y++;
    }
  }

  @Override
  public void readDeck(Readable rd) {
    Scanner sc = new Scanner(rd);
    deck = new ArrayList<Card>();
    while (sc.hasNext()) {
      String name = sc.next();
      AttackValue north = AttackValue.fromInt(sc.nextInt());
      AttackValue south = AttackValue.fromInt(sc.nextInt());
      AttackValue east = AttackValue.fromInt(sc.nextInt());
      AttackValue west = AttackValue.fromInt(sc.nextInt());

      deck.add(new GameCard(name, north, east, south, west));
    }
  }

  @Override
  public void readActors(List<Actor> actors) {
    if (actors == null) {
      throw new IllegalArgumentException("Actors cannot be null");
    }
    this.actors = actors;
  }

  @Override
  public void start(ThreeTriosGame tt, boolean shuffle) {
    if (grid == null || deck == null || actors == null) {
      throw new IllegalStateException("Must initialize all required fields");
    }


    tt.start(shuffle, this.deck, this.grid, this.actors);
  }
}
