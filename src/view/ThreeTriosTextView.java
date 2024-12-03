package view;

import java.awt.Point;
import java.io.IOException;
import java.util.Map;

import controller.PlayerActionFeatures;
import model.Cell;
import model.ReadThreeTrios;
import model.actor.Actor;
import model.card.Card;
import model.card.Direction;

/**
 * A text-based GUI for a three trios game.
 */
public class ThreeTriosTextView implements ThreeTriosView {
  ReadThreeTrios model;
  Readable input;
  Appendable output;

  /**
   * Create a three trios text view for a given model.
   *
   * @param in  input of the view.
   * @param out output of the view.
   */
  public ThreeTriosTextView(ReadThreeTrios model, Readable in, Appendable out) {
    input = in;
    output = out;
    this.model = model;
  }

  @Override
  public void render() {
    out("Player: " + model.getPlayerTurn().getColor() + "\n");
    renderBoard();
    renderCards();
    newLine();
  }

  @Override
  public void addFeaturesListener(PlayerActionFeatures features) {
    // not needed.
  }

  private void renderCards() {
    out("Hand:\n");
    for (Card card : model.getPlayerTurn().getHand()) {
      if (card == null) {
        out("null");
      } else {
        out(card.toString());
      }
      newLine();
    }
  }

  protected void renderBoard() {
    Map<Point, Cell> cells = model.getCells();
    for (int y = 0; y < model.getHeight(); y++) {
      for (int x = 0; x < model.getWidth(); x++) {
        if (validCell(x, y)) {
          out("+-------+");
        } else {
          out("         ");
        }
      }
      newLine();
      for (int x = 0; x < model.getWidth(); x++) {
        if (validCell(x, y)) {
          out("|   ");
          if (cells.get(new Point(x, y)).hasCard()) {
            out(cells.get(new Point(x, y)).getCard().getValue(Direction.NORTH).toString());
          } else {
            out(" ");
          }
          out("   |");
        } else {
          out("         ");
        }
      }
      newLine();
      for (int x = 0; x < model.getWidth(); x++) {
        if (validCell(x, y)) {
          out("| ");
          if (cells.get(new Point(x, y)).hasCard()) {
            out(cells.get(new Point(x, y)).getCard().getValue(Direction.WEST).toString());
            out("(");
            Actor owner = model.whoOwns(cells.get(new Point(x, y)).getCard());
            out(owner.getColor().toString().substring(0, 1));
            out(")");
            out(cells.get(new Point(x, y)).getCard().getValue(Direction.EAST).toString());
          } else {
            out("     ");
          }
          out(" |");
        } else {
          out("         ");
        }
      }
      newLine();
      for (int x = 0; x < model.getWidth(); x++) {
        if (validCell(x, y)) {
          out("|   ");
          if (cells.get(new Point(x, y)).hasCard()) {
            out(cells.get(new Point(x, y)).getCard().getValue(Direction.SOUTH).toString());
          } else {
            out(" ");
          }
          out("   |");
        } else {
          out("         ");
        }
      }
      newLine();
      for (int x = 0; x < model.getWidth(); x++) {
        if (validCell(x, y)) {
          out("+-------+");
        } else {
          out("         ");
        }
      }
      newLine();
    }
    newLine();
  }

  protected void out(String txt) {
    try {
      output.append(txt);
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid text sent to output");
    }
  }

  protected void newLine() {
    out("\n");
  }

  protected boolean validCell(int x, int y) {
    Map<Point, Cell> cells = model.getCells();
    return cells.get(new Point(x, y)) != null;
  }
}
