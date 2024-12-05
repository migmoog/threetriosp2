import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.List;

import model.ThreeTrios;
import model.ThreeTriosGame;
import model.actor.Actor;
import model.actor.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

/**
 * Tests for the config reader class.
 */
public class ConfigReaderTest {
  private TTConfigReader configReader;
  private ThreeTriosGame ttg;

  private List<Actor> baseActors;

  private static List<Actor> make2Players() {
    return List.of(new Player(), new Player());
  }

  @Before
  public void init() {
    configReader = new TTConfigReader();
    ttg = new ThreeTrios();

    baseActors = make2Players();
    Player p1 = (Player) baseActors.get(0);
    Player p2 = (Player) baseActors.get(1);
  }

  @Test
  public void testReadGrid() {
    StringReader gridConfig = new StringReader("3 3\nXXX\nXCX\nXXX");
    StringReader deckConfig = new StringReader("Card1 1 2 3 4\nCard2 5 6 7 8");
    configReader.readGrid(gridConfig);
    configReader.readDeck(deckConfig);
    configReader.readActors(baseActors);
    configReader.start(ttg, false);
    assertNotNull(ttg.getCells());
  }

  @Test
  public void testInvalidGridCharacter() {
    StringReader gridConfig = new StringReader("2 3\nCXC\nAXX");
    assertThrows(IllegalArgumentException.class, () -> configReader.readGrid(gridConfig));
  }

  @Test
  public void testReadDeck() {
    StringReader gridConfig = new StringReader("3 3\nXXX\nXCX\nXXX");
    StringReader deckConfig = new StringReader("Card1 1 2 3 4\nCard2 5 6 7 8");
    configReader.readDeck(deckConfig);
    configReader.readGrid(gridConfig);
    configReader.readActors(baseActors);
    configReader.start(ttg, false);
    assertEquals("Card1 1 2 3 4", ttg.getPlayerTurn().getHand().get(0).toString());
  }

  @Test
  public void testStartWithoutGridAndDeck() {
    assertThrows(IllegalStateException.class, () -> configReader.start(ttg, false));
  }
}