import org.junit.Test;

import java.util.InputMismatchException;

import main.Main;

import static org.junit.Assert.assertThrows;

/**
 * Test command line arguments.
 */
public class CommandLinesTest {

  @Test
  public void testTooFewArguments() {
    assertThrows(IllegalStateException.class, () -> Main.main(new String[]{}));
    assertThrows(IllegalStateException.class, () -> Main.main(new String[]{""}));
  }

  @Test
  public void testInvalidArguments() {
    assertThrows(IllegalStateException.class, () -> Main.main(
            new String[]{"grid_config.properties",
                "deck_config.properties",
                "asdsad", "pastelgui"}));
    assertThrows(IllegalStateException.class,
        () -> Main.main(new String[]{"grid_config.properties",
            "deck_config.properties",
            "human", "asdasd", "pickcorners", "idk"}));
  }

  @Test
  public void testMismatchedProperties() {
    assertThrows(InputMismatchException.class,
        () -> Main.main(new String[]{"deck_config.properties",
            "grid_config.properties", "human", "contrastgui",
            "human", "pastelgui"}));
  }
}
