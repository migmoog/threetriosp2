import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.ObservationalTriadModel;
import cs3500.threetrios.model.PlayerColor;

/**
 * Displays the current game state as a graphical user interface.
 * May be resized and will still work.
 */
public class TripleTriadGraphicalView extends JFrame implements GraphicalView {

  private final TripleTriadPanel panel;
  private final ObservationalTriadModel model;

  /**
   * Creates a new view based off of the observational cs3500.threetrios.provider.model representing
   * the current game state.
   *
   * @param model - the current game, to render.
   */
  public TripleTriadGraphicalView(ObservationalTriadModel model) {
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.panel = new TripleTriadPanel(model);
    this.model = model;
    this.setContentPane(panel);
    this.pack();
  }

  @Override
  public void setVisible() {
    this.setVisible(true);
    this.panel.setVisible(true);
  }

  @Override
  public void update(PlayerColor myPlayer, boolean isGameOver) {
    this.repaint();
    if (isGameOver) {
      this.setTitle("Game Over");
    } else {
      this.setTitle("Player: " + ((myPlayer == PlayerColor.RED) ? "RED" : "BLUE") + ", "
              + ((model.getCurrentPlayer() == myPlayer) ? "" : "not ") + "currently playing.");
    }
  }

  @Override
  public void addFeatures(ViewFeatures viewFeatures) {
    panel.addMouseListener(new MouseInputAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        //check if the click is within the bounds of the hands
        double cardWidth = (double) panel.getWidth()
                / (TripleTriadGraphicalView.this.model.getBoardWidth() + 2);
        PlayerColor player;
        if (e.getX() <= (int) cardWidth) {
          player = PlayerColor.RED;
        } else if (e.getX() >= (int) (panel.getWidth() - cardWidth)) {
          player = PlayerColor.BLUE;
        } else {
          int col = (int) Math.floor((e.getX() - cardWidth) / cardWidth);
          double cardHeight = (double) panel.getHeight()
                  / TripleTriadGraphicalView.this.model.getBoardHeight();
          int row = (int) Math.floor(e.getY() / cardHeight);
          viewFeatures.boardTileSelected(row, col);
          return;
        }
        List<Card> hand = TripleTriadGraphicalView.this.model.getHand(player);
        if (hand.isEmpty()) {
          viewFeatures.handCardSelected(-1, player);
          return;
        }
        double cardHeight = (double) panel.getHeight() / (hand.size());
        int handIndex = (int) Math.floor(e.getY() / cardHeight);
        viewFeatures.handCardSelected(handIndex, player);
      }
    });
  }

  @Override
  public void highlightCard(Card card) {
    panel.setHighlightedCard(card);
  }

  @Override
  public Card getHighlightedCard() {
    return this.panel.getHighlightedCard();
  }

  @Override
  public void showError(String msg) {
    JOptionPane.showMessageDialog(this, msg);
  }

}
