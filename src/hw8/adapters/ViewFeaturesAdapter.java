package hw8.adapters;

import java.awt.*;

import controller.PlayerActionFeatures;
import cs3500.threetrios.provider.model.PlayerColor;
import cs3500.threetrios.provider.view.ViewFeatures;
import model.ReadThreeTrios;
import model.actor.Actor;

public class ViewFeaturesAdapter implements ViewFeatures {
  PlayerActionFeatures features;
  ReadThreeTrios observations;

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
