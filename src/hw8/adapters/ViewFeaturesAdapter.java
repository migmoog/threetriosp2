package hw8.adapters;

import java.awt.*;

import controller.PlayerActionFeatures;
import cs3500.threetrios.provider.model.PlayerColor;
import cs3500.threetrios.provider.view.ViewFeatures;
import model.ReadThreeTrios;
import model.actor.Actor;

/**
 * Adapter to use our model with the provider's "features" interface.
 */
public class ViewFeaturesAdapter implements ViewFeatures {
  PlayerActionFeatures features;
  ReadThreeTrios observations;

  /**
   * Constructs the view features adapter.
   * @param features the features to add on top of this one.
   * @param model the model to observe for our features.
   */
  public ViewFeaturesAdapter(PlayerActionFeatures features, ReadThreeTrios model){
    this.features = features;
    this.observations = model;
  }

  @Override
  public void handCardSelected(int index, PlayerColor player) {
    for (Actor a : observations.getActors()) {
      if (a.getColor().toProvider() == player) {
        features.selectCard(a, index);
        break;
      }
    }
  }

  @Override
  public void boardTileSelected(int row, int col) {
    features.selectCell(new Point(col, row));
  }
}
