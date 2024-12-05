import org.junit.Test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.BoardCell;
import model.actor.Actor;
import model.actor.Player;
import model.card.AttackValue;
import model.card.Card;
import model.card.GameCard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Test basic three trios functionality.
 */
public class ThreeTriosTest extends ThreeTriosSetup {
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

  @Test
  public void testSetup() {
    cfg.start(ttg, false);
    Map<Point, BoardCell> expected = new HashMap<>();
    for (int y = 0; y < 3; y++) {
      for (int x = 0; x < 3; x++) {
        expected.put(new Point(x, y), new BoardCell());
      }
    }

    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        assertEquals(expected.get(new Point(x, y)).getCard(),
                ttg.getCells().get(new Point(x, y)).getCard());
      }
    }

    assertEquals("Red player's hand should be filled",
            new ArrayList<Card>(List.of(
                    makeCard("Jimmy", 1, 2, 3, 4),
                    makeCard("Bobby", 3, 3, 3, 2),
                    makeCard("FatRicky", 1, 1, 5, 10),
                    makeCard("ShortTimmy", 2, 3, 4, 8),
                    makeCard("Willy", 6, 6, 6, 6))),
            new ArrayList<Card>(ttg.getPlayerHand(0)));
    assertEquals("Blue player's hand should be filled",
            new ArrayList<Card>(List.of(
                    makeCard("Johnny", 5, 5, 6, 3),
                    makeCard("Abe", 10, 10, 10, 10),
                    makeCard("WildWoody", 2, 3, 3, 3),
                    makeCard("Insane", 3, 4, 3, 4),
                    makeCard("Joaquin", 7, 9, 9, 10))),
            new ArrayList<Card>(ttg.getPlayerHand(1)));
  }

  @Test
  public void testGamePlay() {
    cfg.start(ttg, false);
    Point p = new Point(0, 1);
    p1.selectCell(p.x, p.y);
    p1.selectCard(0);
    ttg.placeCard();
    assertNotNull("Should now have a card placed at this cell",
            ttg.getCells().get(p));
    assertThrows("Should not be able to play again",
            IllegalStateException.class, () -> ttg.placeCard());

    // won't do anything but the game will progress
    ttg.battle();
    // place a blue card
    p2.selectCell(2, 1);
    p2.selectCard(0);
    ttg.placeCard(); // play "Johnny" to the right
    ttg.battle();
    assertEquals("Red should own only one card.",
            1,
            ttg.countPlayersCards(0));
    assertEquals("Blue should also own only one.",
            1,
            ttg.countPlayersCards(1));
    // place red card and commence the spread
    p1.selectCell(1, 1);
    p1.selectCard(2);
    ttg.placeCard(); // east side of FatRicky exceeds west of Johnny (5 > 3)
    ttg.battle();

    // check after battle
    assertEquals("Red should own all cards",
            3,
            ttg.countPlayersCards(0));
    assertEquals("Blue should lose the only card it had",
            0,
            ttg.countPlayersCards(1));

    assertThrows("Can't call battle again without placing next",
            IllegalStateException.class, () -> ttg.battle());
  }

  @Test
  public void testWonGame() {
    cfg3Cell.start(threeCellGame, true);

    assertThrows("Should not be able to check winner yet",
            IllegalStateException.class, () -> threeCellGame.winningPlayerIndex());

    // play game to completion
    p3.selectCell(2, 1);
    p3.selectCard(0);
    threeCellGame.placeCard();
    threeCellGame.battle();
    p4.selectCell(0, 1);
    p4.selectCard(0);
    threeCellGame.placeCard();
    threeCellGame.battle();
    p3.selectCell(1, 1);
    p3.selectCard(0);
    threeCellGame.placeCard();
    threeCellGame.battle();


    assertTrue(threeCellGame.isGameOver());
    assertEquals("Red should have won the game",
            0, threeCellGame.winningPlayerIndex());
  }
}
