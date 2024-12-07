package cs3500.threetrios.provider.viewimpl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.PlayerColor;
import cs3500.threetrios.provider.model.Tile;
import cs3500.threetrios.provider.model.Direction;
import cs3500.threetrios.provider.model.ObservationalTriadModel;

/**
 * TripleTriadPanel is a JPanel that deals with rendering the model's current state
 * into a Swing GUI. Using the supplied model and the window's bounds, it dynamically
 * displays the current game state.
 */
public class TripleTriadPanel extends JPanel {

  private final ObservationalTriadModel model;
  private Card highlightedCard;

  /**
   * Creates a new TripleTriadPanel with the supplied cs3500.threetrios.provider.model.
   *
   * @param model - the current game to render.
   */
  public TripleTriadPanel(ObservationalTriadModel model) {
    this.model = model;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    //I'm just going to work in physical coordinates
    //top left is (0,0)
    //there are width + 2 cards across the screen
    //each card has a height of {heightPx / numCards}, width of widthPx
    int widthCards = model.getBoardWidth() + 2;
    Rectangle bounds = this.getBounds();
    //card height will be changed with the size of the screen.
    drawHand(g, PlayerColor.RED, 0,
            (double) bounds.width / widthCards, bounds.height);
    drawHand(g, PlayerColor.BLUE, bounds.width - ((double) bounds.width / widthCards),
            (double) bounds.width / widthCards, bounds.height);
    drawBoard(g, (double) bounds.width / widthCards, bounds);
    if (model.isGameOver()) {
      drawGameOverScreen(g);
    }
  }

  private void drawGameOverScreen(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
    //GAME OVER
    //Winner: BLUE
    //Final Scores:
    //5:7 (colored red, black, blue)
    //'Final Scores:' width should be 1/3 of the window
    int iterator = 0;
    while (g2d.getFontMetrics().stringWidth("Final Scores:") < this.getBounds().width / 2) {
      iterator += 1;
      g2d.setFont(new Font("lol", Font.PLAIN, iterator));
    }
    g2d.setColor(new Color(60, 60, 60, 200));
    g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    g2d.setColor(Color.black);
    FontMetrics metrics = g2d.getFontMetrics();
    Optional<PlayerColor> winningPlayer = model.getWinningPlayer();
    String winnerString = "Tie game!";
    if (winningPlayer.isPresent()) {
      winnerString = "Winner: "
              + ((winningPlayer.get() == PlayerColor.RED) ? "RED" : "BLUE");
    }
    String redScore = Integer.toString(model.getPlayerScore(PlayerColor.RED));
    String blueScore = Integer.toString(model.getPlayerScore(PlayerColor.BLUE));
    g2d.drawString("GAME OVER", this.getWidth() / 2 - metrics.stringWidth("GAME OVER") / 2,
            this.getHeight() / 2 - 3 * metrics.getHeight() / 2);
    g2d.drawString(winnerString, this.getWidth() / 2 - metrics.stringWidth(winnerString) / 2,
            this.getHeight() / 2 - metrics.getHeight() / 2);
    g2d.drawString("Final Scores:",
            this.getWidth() / 2 - metrics.stringWidth("Final Scores:") / 2,
            this.getHeight() / 2 + metrics.getHeight() / 2);
    g2d.setColor(Color.RED);
    g2d.drawString(redScore, this.getWidth() / 2
                    - metrics.stringWidth(redScore) / 2 - 2 * metrics.stringWidth(":"),
            this.getHeight() / 2 + 3 * metrics.getHeight() / 2);
    g2d.setColor(Color.BLUE);
    g2d.drawString(blueScore, this.getWidth() / 2
                    - metrics.stringWidth(blueScore) / 2 + 2 * metrics.stringWidth(":"),
            this.getHeight() / 2 + 3 * metrics.getHeight() / 2);
    g2d.setColor(Color.BLACK);
    g2d.drawString(":", this.getWidth() / 2 - metrics.stringWidth(":") / 2,
            this.getHeight() / 2 + 3 * metrics.getHeight() / 2);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(400, 400);
  }

  private void drawHand(Graphics g, PlayerColor color, double leftBound,
                        double cardWidth, double height) {
    Graphics2D g2d = (Graphics2D) g.create();
    List<Card> hand = model.getHand(color);
    double cardHeight = height / hand.size();
    for (int cardIndex = 0; cardIndex < hand.size(); cardIndex++) {
      drawCard(g2d, hand.get(cardIndex), leftBound, cardHeight * cardIndex, cardWidth, cardHeight);
    }
  }

  private void drawBoard(Graphics g, double cardWidth, Rectangle bounds) {
    Graphics2D g2d = (Graphics2D) g.create();
    double cardHeight = (double) bounds.height / model.getBoardHeight();
    List<List<Tile>> board = model.getBoard();
    for (int row = 0; row < board.size(); row++) {
      for (int col = 0; col < board.get(0).size(); col++) {
        Card card = board.get(row).get(col).getCard();
        if (card == null) {
          Rectangle tile = new Rectangle((int) (cardWidth + col * cardWidth),
                  (int) (row * cardHeight), (int) cardWidth, (int) cardHeight);
          g2d.setColor((board.get(row).get(col).isHole()) ? Color.GRAY : Color.YELLOW);
          g2d.fillRect(tile.x, tile.y, tile.width, tile.height);
          g2d.setColor(Color.black);
          g2d.drawRect(tile.x, tile.y, tile.width, tile.height);
        } else {
          drawCard(g2d, card, cardWidth + col * cardWidth, row * cardHeight, cardWidth, cardHeight);
        }
      }
    }
  }

  private void drawCard(Graphics g, Card card, double x, double y, double width, double height) {
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setColor((card.getPlayer() == PlayerColor.RED) ? Color.RED : Color.BLUE);
    if (card.equals(highlightedCard)) {
      g2d.setPaint(new GradientPaint((float) x, (float) y, Color.WHITE, (float) (x + width),
              (float) (y + height), g2d.getColor()));
    }
    g2d.fillRect((int) x, (int) y, (int) width, (int) height);
    g2d.setColor(Color.black);
    g2d.drawRect((int) x, (int) y, (int) width, (int) height);
    g2d.drawString(hexParsedInteger(card.getDamage(Direction.NORTH)),
            (int) (x + width / 2), (int) (y + height / 4));
    g2d.drawString(hexParsedInteger(card.getDamage(Direction.SOUTH)),
            (int) (x + width / 2), (int) (y + 3 * height / 4));
    g2d.drawString(hexParsedInteger(card.getDamage(Direction.EAST)),
            (int) (x + 3 * width / 4), (int) (y + height / 2));
    g2d.drawString(hexParsedInteger(card.getDamage(Direction.WEST)),
            (int) (x + width / 4), (int) (y + height / 2));
  }

  private String hexParsedInteger(int toParse) {
    return (toParse == 10) ? "A" : Integer.toString(toParse);
  }

  /**
   * Changes the highlighted card. If the highlighted card is identical to the card given, it
   * will remove the highlight from the currently highlighted card.
   *
   * @param card - the card to toggle highlight of.
   */
  public void setHighlightedCard(Card card) {
    if (this.highlightedCard == card) {
      this.highlightedCard = null;
      return;
    }
    this.highlightedCard = card;
  }

  public Card getHighlightedCard() {
    return this.highlightedCard;
  }


}
