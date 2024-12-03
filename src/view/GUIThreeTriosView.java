package view;

import java.awt.Point;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.RenderingHints;
import java.awt.Font;

import javax.swing.border.Border;

import controller.PlayerActionFeatures;
import model.Cell;
import model.ReadThreeTrios;
import model.actor.Actor;
import model.card.AttackValue;
import model.card.Card;
import model.card.Direction;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * A swing interface for viewing a game of ThreeTrios.
 */
public class GUIThreeTriosView implements ThreeTriosView {
  private int height;
  private Actor p1;
  private Actor p2;
  private Map<Actor, HandCardPanel> selectedCard;
  private Map<Actor, JPanel> handPanelMap;

  private JFrame frame;
  private JPanel leftPanel;
  private JPanel rightPanel;
  private JPanel gridLayout;

  private ReadThreeTrios model;

  /**
   * A ThreeTrios view that utilizes a swing GUI.
   *
   * @param model a read-only model of the threetrios game.
   * @param w     the width of the window in pixels
   * @param h     the height of the window in pixels
   */
  public GUIThreeTriosView(ReadThreeTrios model, int w, int h) {
    if (model.getActors().size() > 2) {
      throw new IllegalArgumentException("GUI only supports up to two players");
    }
    this.height = h;

    this.model = model;

    frame = new JFrame();

    frame.setSize(w, h);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());

    Border border = BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1);

    leftPanel = new JPanel();
    leftPanel.setPreferredSize(new Dimension(w / 8, height));

    rightPanel = new JPanel();
    rightPanel.setPreferredSize(new Dimension(w / 8, height));

    JPanel middlePanel = new JPanel();
    middlePanel.setBorder(border);

    frame.add(leftPanel, BorderLayout.WEST);
    frame.add(rightPanel, BorderLayout.EAST);
    frame.add(middlePanel, BorderLayout.CENTER);

    frame.setResizable(true);
    frame.setMinimumSize(new Dimension(300, 100));
    frame.setVisible(true);
    setTitle("Three Trios");

    handPanelMap = new HashMap<>();

    this.p1 = model.getActors().get(0);
    this.p2 = model.getActors().get(1);

    gridLayout = makeGridPanel(model.getCells(), model.getWidth(),
            model.getHeight(), model.getActors());
    middlePanel.setLayout(new BorderLayout());
    middlePanel.add(gridLayout, BorderLayout.CENTER);
    createHandPanels();
  }

  private void createHandPanels() {
    handPanelMap.put(p1, leftPanel);
    handPanelMap.put(p2, rightPanel);

    for (Actor p : handPanelMap.keySet()) {
      JPanel panel = handPanelMap.get(p);
      panel.setLayout(new GridLayout(0, 1));
      for (int i = 0; i < p.getHand().size(); i++) {
        CardPanel cardPanel = makeCardPanel(p.getHand().get(i), p, true);
        panel.add(cardPanel);
      }
    }
  }

  @Override
  public void render() {
    if (!model.getActors().isEmpty()) {
      frame.setVisible(true);

      updateHands();
      updateGrid();

      frame.revalidate();
      frame.repaint();
    } else {
      throw new IllegalStateException("Game has not started");
    }
  }

  private void updateHands() {
    for (Actor p : handPanelMap.keySet()) {
      JPanel panel = handPanelMap.get(p);
      for (Component c : panel.getComponents()) {
        if (c instanceof HandCardPanel) {
          ((HandCardPanel) c).highlight(false);
          if (p.getSelectedCardIdx() != -1 && p.getHand().get(
                  p.getSelectedCardIdx()).equals(((HandCardPanel) c).getCard())) {
            ((HandCardPanel) c).highlight(true);
          }
          c.repaint();
          if (!p.getHand().contains(((HandCardPanel) c).getCard())) {
            panel.remove(c);
          }
        }
      }
      panel.revalidate();
      panel.repaint();
    }
  }

  private void updateGrid() {
    for (Component c : gridLayout.getComponents()) {
      if (c instanceof GridCellPanel) {
        Cell dataCell = model.getCells().get(new Point(((GridCellPanel) c).getPoint()));
        if (dataCell == null) {
          ((GridCellPanel) c).deactivate();
        } else {
          ((GridCellPanel) c).activate();
          if (dataCell.hasCard()) {
            ((GridCellPanel) c).setCard(dataCell.getCard());
          } else {
            ((GridCellPanel) c).removeCard();
          }
        }
        c.revalidate();
        c.repaint();
      }
    }
  }

  private CardPanel makeCardPanel(Card card, Actor player, boolean isHand) {
    CardPanel cardPanel;
    if (isHand) {
      cardPanel = new HandCardPanel(card, player);
    } else {
      cardPanel = new CardPanel(card, player.getColor());
    }
    cardPanel.setFontSize(height / model.getHeight() / 8);
    return cardPanel;
  }

  private JPanel makeGridPanel(Map<Point, Cell> grid, int width, int height, List<Actor> players) {
    JPanel gridPanel = new JPanel(new GridLayout(height, width));
    for (int j = 0; j < model.getHeight(); j++) {
      for (int i = 0; i < model.getWidth(); i++) {
        GridCellPanel cellPanel = new GridCellPanel(i, j);
        if (grid.containsKey(new Point(i, j))) {
          Cell cell = grid.get(new Point(i, j));
          cellPanel.activate();
          if (cell.hasCard()) {
            cellPanel.setCard(cell.getCard());
          }
        }
        gridPanel.add(cellPanel);
      }
    }
    return gridPanel;
  }

  @Override
  public void addFeaturesListener(PlayerActionFeatures features) {
    // add mouse-press detection for handCards
    for (Actor p : handPanelMap.keySet()) {
      JPanel playerPanel = handPanelMap.get(p);
      for (Component c : playerPanel.getComponents()) {
        if (c instanceof HandCardPanel) {
          HandCardPanel cardPanel = (HandCardPanel) c;
          cardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              for (int i = 0; i < p.getHand().size(); i++) {
                if (p.getHand().get(i).equals(((HandCardPanel) c).getCard())) {
                  features.selectCard(p, i);
                }
              }
            }
          });
        }
      }
    }
    // Add mouse-press detection for grid cells
    for (Component c : gridLayout.getComponents()) {
      if (c instanceof GridCellPanel) {
        GridCellPanel cellPanel = (GridCellPanel) c;
        cellPanel.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            features.selectCell(cellPanel.getPoint());
          }
        });
      }
    }
  }

  protected class HandCardPanel extends CardPanel {
    private Actor player;

    public HandCardPanel(Card card, Actor player) {
      super(card, player.getColor());
      this.player = player;
    }

    public Actor getPlayer() {
      return this.player;
    }
  }

  protected class GridCellPanel extends JPanel {
    protected int x;
    protected int y;
    CardPanel cardPanel;
    private boolean active;
    private boolean hasCard;

    public GridCellPanel(int x, int y) {
      this.active = false;
      this.hasCard = false;
      this.x = x;
      this.y = y;

      setLayout(new BorderLayout(0, 0));
    }

    public void activate() {
      this.active = true;
    }

    public void deactivate() {
      this.active = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (!hasCard) {
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1));
        if (active) {
          setBackground(Color.YELLOW);
        } else {
          setBackground(Color.LIGHT_GRAY);
        }
      } else if (cardPanel != null) {
        cardPanel.repaint();
      }
    }

    public void setCard(Card card) {
      if (!this.active) {
        throw new IllegalStateException("Can't add card to inactive cell");
      }
      if (hasCard) {
        this.remove(cardPanel);
      }
      this.hasCard = true;
      Actor p = model.whoOwns(card);
      this.cardPanel = makeCardPanel(card, p, false);
      this.add(cardPanel, BorderLayout.CENTER);
    }

    public void removeCard() {
      if (this.hasCard) {
        this.remove(cardPanel);
        this.hasCard = false;
        this.cardPanel = null;
      }
    }

    public Point getPoint() {
      return new Point(x, y);
    }
  }

  protected class CardPanel extends JPanel {
    private Card card;
    private model.actor.Color color;
    private int fontSize;
    private boolean highlighted;

    public CardPanel(Card card, model.actor.Color color) {
      this.card = card;
      this.color = color;
      fontSize = 10;
    }

    public Card getCard() {
      return card;
    }

    public void setFontSize(int fontSize) {
      this.fontSize = fontSize;
    }

    public void highlight(boolean isHighlighted) {
      highlighted = isHighlighted;
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      Graphics2D g2d = (Graphics2D) g;

      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
              RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
              RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

      g2d.setColor(Color.BLACK);
      g2d.setFont(new Font("Monospace", Font.BOLD, fontSize)); // Font type, style, and size

      int cardWidth = getWidth();
      int cardHeight = getHeight();

      FontMetrics fm = g2d.getFontMetrics();
      int textWidth = fm.stringWidth("A");
      int textHeight = fm.getHeight();

      g2d.translate(cardWidth / 2 - textWidth / 2, cardHeight / 2 + textHeight / 4);
      g2d.drawString(card.getValue(Direction.NORTH).toString(), 0, -cardHeight / 4);
      g2d.drawString(card.getValue(Direction.EAST).toString(), cardWidth / 4, 0);
      g2d.drawString(card.getValue(Direction.WEST).toString(), -cardWidth / 4, 0);
      g2d.drawString(card.getValue(Direction.SOUTH).toString(), 0, +cardHeight / 4);

      g2d.translate(-(cardWidth / 2 - textWidth / 2), -(cardHeight / 2 + textHeight / 4));
      if (highlighted) {
        highlightColors();
      } else {
        defaultColors();
      }
    }

    private void defaultColors() {
      int[] pClr = color.getRGB();
      setBackground(new Color(pClr[0], pClr[1], pClr[2]));
      setBorder(
              BorderFactory.createLineBorder(new Color(
                      max(0, pClr[0] - 50),
                      max(0, pClr[1] - 50),
                      max(0, pClr[2] - 50))));
    }

    private void highlightColors() {
      int[] pClr = color.getRGB();
      setBackground(new Color(
              min(255, pClr[0] + 50),
              min(255, pClr[1] + 50),
              min(255, pClr[2] + 50)));
      setBorder(BorderFactory.createLineBorder(new Color(
              min(255, pClr[0] + 10),
              min(255, pClr[1] + 10),
              min(255, pClr[2] + 10)), 5));
    }
  }
  public void setVisibility(boolean visible) {
    frame.setVisible(visible);
  }

  public void setTitle(String title) {
    frame.setTitle(title);
  }
  public void showMessage(String message) {
    JOptionPane.showMessageDialog(this.frame, message);
  }
}
