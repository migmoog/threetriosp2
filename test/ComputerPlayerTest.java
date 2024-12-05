import org.junit.Assert;
import org.junit.Test;
import main.ConfigReader;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import model.ThreeTriosGame;
import model.actor.Move;
import model.actor.ThreeTriosMove;
import model.actor.strategies.HighestSpread;
import model.actor.strategies.PickCorners;
import model.actor.strategies.Strategy;

/**
 * Tests for an AI computer player.
 */
public class ComputerPlayerTest extends ThreeTriosSetup {

  @Test
  public void testNoMoveForSpread() {
    cfg.start(ttg, false);
    Strategy highestSpread = new HighestSpread();
    Assert.assertEquals(highestSpread.makeMove(ttg, p1), Optional.empty());
  }

  @Test
  public void testHighestSpread() {
    cfg.start(ttg, false);
    setSituationOne();
    Strategy highestSpread = new HighestSpread();
    Move actual = highestSpread.makeMove(ttg, p1).get();
    Move expected = new ThreeTriosMove(p1.getHand().get(2), new Point(1, 2));
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testHighestSpreadBigMap() {
    try {
      cfg.readGrid(new FileReader("grid_config.properties"));
      cfg.readDeck(new FileReader("deck_config.properties"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    cfg.start(ttg, false);
    setSituationTwo();
    Strategy highestSpread = new HighestSpread();
    Assert.assertEquals(highestSpread.makeMove(ttg, p2).get(),
            new ThreeTriosMove(p2.getHand().get(3), new Point(2, 1)));
  }

  @Test
  public void testEmptyMapCorner() throws IOException {
    cfg.start(ttg, false);

    ThreeTriosGame mockModel = new MockTT(new FileWriter("strategy-transcript.txt"));
    cfg.start(mockModel, false);

    Strategy pickCorners = new PickCorners();
    Assert.assertEquals(pickCorners.makeMove(ttg, p1).get(),
            new ThreeTriosMove(p1.getHand().get(4), new Point(0, 0)));

    pickCorners.makeMove(mockModel, p1);
  }

  @Test
  public void testCornerPick() {
    cfg.start(ttg, false);
    setSituationOne();
    Strategy cornerPick = new PickCorners();
    Move actual = cornerPick.makeMove(ttg, p1).get();
    Move expected = new ThreeTriosMove(p1.getHand().get(2), new Point(0, 1));
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testCornerPickBigMap() {
    try {
      cfg.readGrid(new FileReader("grid_config.properties"));
      cfg.readDeck(new FileReader("deck_config.properties"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    cfg.start(ttg, false);
    setSituationTwo();
    Strategy cornerPick = new PickCorners();
    Assert.assertEquals(cornerPick.makeMove(ttg, p2).get(),
            new ThreeTriosMove(p2.getHand().get(1), new Point(0, 3)));
  }

  private void setSituationOne() {
    p1.selectCell(0, 0);
    p1.selectCard(0);
    ttg.placeCard();
    ttg.battle();
    p2.selectCell(1, 0);
    p2.selectCard(2);
    ttg.placeCard();
    ttg.battle();
    p1.selectCell(2, 0);
    p1.selectCard(0);
    ttg.placeCard();
    ttg.battle();
    p2.selectCell(1, 1);
    p2.selectCard(0);
    ttg.placeCard();
    ttg.battle();
  }

  private void setSituationTwo() {
    p1.selectCell(0, 0);
    p1.selectCard(3);
    ttg.placeCard();
    ttg.battle();
    p2.selectCell(1, 0);
    p2.selectCard(2);
    ttg.placeCard();
    ttg.battle();
    p1.selectCell(2, 0);
    p1.selectCard(0);
    ttg.placeCard();
    ttg.battle();
    p2.selectCell(3, 2);
    p2.selectCard(2);
    ttg.placeCard();
    ttg.battle();
    p1.selectCell(4, 2);
    p1.selectCard(2);
    ttg.placeCard();
    ttg.battle();
  }
}
