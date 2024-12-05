import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import java.util.Map;

import cs3500.threetrios.provider.model.Direction;
import cs3500.threetrios.provider.model.PlayerColor;
import hw8.adapters.ProviderCardAdapter;
import model.Cell;
import model.card.Card;
import model.card.GameCard;

public class CardAdaptationTests extends ThreeTriosSetup {
  private void playOneCard() {
    cfg3Cell.start(threeCellGame, false);
    p3.selectCard(0); // select RedWeak
    Point leftMid = new Point(0, 1);
    p3.selectCell(leftMid.x, leftMid.y); // left cell
    threeCellGame.placeCard();
  }

  @Test
  public void testBasicWrapper() {
    playOneCard();
    Map<Point, Cell> cells = threeCellGame.getCells();
    model.card.Card redWeak = cells.get(new Point(0, 1)).getCard();
    Assert.assertNotNull(redWeak);

    // use the actual adapter
    ProviderCardAdapter adapt = new ProviderCardAdapter(redWeak, threeCellGame);
    Assert.assertEquals("Attack values are the same",
            1,
            adapt.getDamage(Direction.EAST));
    Assert.assertEquals("Should belong to red",
            PlayerColor.RED,
            adapt.getPlayer());
    Assert.assertEquals("Should have the same name",
            "RedWeak",
            adapt.getName());
  }

  @Test
  public void testUnownedCard() {
    playOneCard(); // just to be sure we've started

    model.card.Card c = p4.getHand().get(0);
    ProviderCardAdapter a = new ProviderCardAdapter(c, threeCellGame);
    Assert.assertEquals(PlayerColor.BLUE, a.getPlayer());
  }
}
