import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import java.io.InputStreamReader;

import controller.ThreeTriosController;
import view.SimpleThreeTriosTextView;

/**
 * A simple controller test.
 */
public class TTControllerTest extends ThreeTriosSetup {

  protected ThreeTriosController controller1;
  protected ThreeTriosController controller2;
  protected SimpleThreeTriosTextView view1;
  protected SimpleThreeTriosTextView view2;

  @Before
  @Override
  public void init() {
    super.init();
    view1 = new SimpleThreeTriosTextView(ttg, new InputStreamReader(System.in), System.out);
    view2 = new SimpleThreeTriosTextView(ttg, new InputStreamReader(System.in), System.out);
  }

  private void setControllers() {
    controller1 = new ThreeTriosController(p1, view1, ttg);
    controller2 = new ThreeTriosController(p2, view2, ttg);
  }

  @Test
  public void testSelect() {
    cfg.start(ttg, false);
    setControllers();
    controller1.selectCard(p1, 0);
    Assert.assertEquals(p1.getSelectedCardIdx(), 0);
    controller1.selectCell(new Point(1, 1));
    Assert.assertEquals(p1.getSelectedCell(), new Point(1, 1));
  }
}
