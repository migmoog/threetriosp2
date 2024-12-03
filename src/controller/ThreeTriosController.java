package controller;

import java.awt.Point;

import javax.swing.JOptionPane;

import model.Cell;
import model.ThreeTriosGame;
import model.actor.Actor;
import view.ThreeTriosView;

/**
 * Create a controller to mediate between the ThreeTrios view and model.
 */
public class ThreeTriosController implements PlayerActionFeatures, ModelStatusFeatures {
  private ThreeTriosView view;
  private ThreeTriosGame model;
  private Actor player;

  private boolean hasSelectedCard;

  /**
   * Create the controller with a view and model to listen from.
   *
   * @param view  the view to connect with.
   * @param model the model to connect with
   */
  public ThreeTriosController(Actor player, ThreeTriosView view, ThreeTriosGame model) {
    this.view = view;
    this.model = model;
    this.player = player;

    // add this controller as a listener for the view and model
    view.addFeaturesListener(this);
    model.addFeaturesListener(this);
  }

  @Override
  public void selectCard(Actor ownsCard, int index) {
    // check that it is this player's turn, and the clicked card is their own
    if (model.getPlayerTurn().equals(player) && player.equals(ownsCard)) {
      // if card is already selected, deselect it
      if (player.getSelectedCardIdx() == index) {
        hasSelectedCard = false;
        player.selectCard(-1);
      } else {
        hasSelectedCard = true;
        player.selectCard(index);
      }
      // re-render board to update highlight on selected card
      view.render();
    }
  }

  @Override
  public void selectCell(Point location) {
    if (model.getPlayerTurn().equals(player) && !model.isGameOver()) {
      if (hasSelectedCard) {
        if (model.getCells().get(location) != null) {
          Cell selectedCell = model.getCells().get(location);
          if (selectedCell.hasCard()) {
            JOptionPane.showMessageDialog(null,
                    "Card is already placed here",
                    "Invalid Move", JOptionPane.ERROR_MESSAGE);
          } else {
            player.selectCell(location.x, location.y);
            model.placeCard();
            model.battle();
          }
        } else {
          JOptionPane.showMessageDialog(null,
                  "You can only place cards on yellow cells",
                  "Invalid Move", JOptionPane.ERROR_MESSAGE);
        }
      } else {
        JOptionPane.showMessageDialog(null,
                "Please select a card from your hand first",
                "Invalid Move", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  @Override
  public void beginTurn(Actor actor) {
    if (model.getPlayerTurn().equals(player) && !model.isGameOver()) {
      hasSelectedCard = false;
      actor.start();
    }
  }

  @Override
  public void endTurn(Actor actor) {
    if (model.getPlayerTurn().equals(player)) {
      hasSelectedCard = false;
      if (model.isGameOver()) {
        JOptionPane.showMessageDialog(null,
                model.getActors().get(model.winningPlayerIndex()).getColor() + " won!",
                "GAME OVER",
                JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  @Override
  public void updateBoard() {
    view.render();
  }
}
