package view;

import java.awt.Point;
import java.util.Map;

import model.Cell;
import model.ReadThreeTrios;
import model.actor.Actor;

/**
 * Makes a simple text view of a game of three trios.
 */
public class SimpleThreeTriosTextView extends ThreeTriosTextView {
  /**
   * Construct a text view.
   *
   * @param model the model to view.
   * @param in    the redable to pass in.
   * @param out   the appendable to write the output to.
   */
  public SimpleThreeTriosTextView(ReadThreeTrios model, Readable in, Appendable out) {
    super(model, in, out);
  }

  @Override
  protected void renderBoard() {
    Map<Point, Cell> cells = model.getCells();
    for (int y = 0; y < model.getHeight(); y++) {
      for (int x = 0; x < model.getWidth(); x++) {
        if (validCell(x, y)) {
          if (cells.get(new Point(x, y)).getCard() != null) {
            Actor owner = model.whoOwns(cells.get(new Point(x, y)).getCard());
            out(owner.getColor().toString().substring(0, 1));
          } else {
            out("_");
          }
        } else {
          out(" ");
        }
      }
      newLine();
    }
  }
}
