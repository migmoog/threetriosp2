import org.junit.Before;

import java.io.StringReader;
import java.util.List;
import java.util.Random;

import model.ThreeTrios;
import model.ThreeTriosGame;
import model.actor.Actor;
import model.actor.Player;
import model.card.AttackValue;
import model.card.GameCard;

/**
 * Test basic three trios functionality.
 */
public class ThreeTriosSetup {
  protected ThreeTriosGame ttg;
  protected List<Actor> actors;
  protected Player p1;
  protected Player p2;
  protected TTConfigReader cfg;

  protected ThreeTriosGame threeCellGame;
  protected List<Actor> actors3Cell;
  protected Player p3;
  protected Player p4;
  protected TTConfigReader cfg3Cell;

  protected static GameCard makeCard(String name, int north, int south, int east, int west) {
    return new GameCard(name,
            AttackValue.fromInt(north),
            AttackValue.fromInt(east),
            AttackValue.fromInt(south),
            AttackValue.fromInt(west));
  }

  private static List<Actor> make2Player() {
    return List.of(new Player(), new Player());
  }

  @Before
  public void init() {
    ttg = new ThreeTrios(new Random(1));
    actors = make2Player();
    p1 = (Player) actors.get(0);
    p2 = (Player) actors.get(1);
    cfg = new TTConfigReader();

    threeCellGame = new ThreeTrios(new Random(1));
    actors3Cell = make2Player();
    p3 = (Player) actors3Cell.get(0);
    p4 = (Player) actors3Cell.get(1);
    cfg3Cell = new TTConfigReader();

    String allCells = "3 3\n" +
            "CCC\n" +
            "CCC\n" +
            "CCC\n";
    StringBuilder cards = new StringBuilder()
            .append("Jimmy 1 2 3 4\n")
            .append("Bobby 3 3 3 2\n")
            .append("FatRicky 1 1 5 10\n")
            .append("ShortTimmy 2 3 4 8\n")
            .append("Willy 6 6 6 6\n")
            .append("Johnny 5 5 6 3\n")
            .append("Abe 10 10 10 10\n")
            .append("WildWoody 2 3 3 3\n")
            .append("Insane 3 4 3 4\n")
            .append("Joaquin 7 9 9 10\n")
            .append("Oog 1 1 1 1");
    cfg.readDeck(new StringReader(cards.toString()));
    cfg.readGrid(new StringReader(allCells));
    cfg.readActors(actors);

    StringBuilder threeCells = new StringBuilder("3 3\n");
    threeCells.append("XXX\n")
            .append("CCC\n")
            .append("XXX\n");
    StringBuilder deck3Cells = new StringBuilder()
            .append("RedWeak 1 1 1 1\n")
            .append("RedStrong 1 1 10 10\n")
            .append("BlueWeak 1 1 1 1\n")
            .append("BlueStrong 1 1 10 10\n");

    cfg3Cell.readGrid(new StringReader(threeCells.toString()));
    cfg3Cell.readDeck(new StringReader(deck3Cells.toString()));
    cfg3Cell.readActors(actors3Cell);
  }
}
