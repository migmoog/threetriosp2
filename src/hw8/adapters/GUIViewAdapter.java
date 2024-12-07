package hw8.adapters;

import controller.PlayerActionFeatures;
import cs3500.threetrios.provider.view.GraphicalView;
import model.ReadThreeTrios;
import view.ThreeTriosView;

/**
 * An adapter to make the providers graphical view work with our own.
 */
public class GUIViewAdapter implements ThreeTriosView {
  private GraphicalView view;
  private ReadThreeTrios observations;

  /**
   * Construct a view adapter.
   *
   * @param view  a class that implements our provider's graphical view.
   * @param model our own model.
   */
  public GUIViewAdapter(GraphicalView view, ReadThreeTrios model) {
    this.observations = model;
    this.view = view;
    view.setVisible();
  }

  @Override
  public void render() {
    view.update(
            observations.getPlayerTurn().getColor().toProvider(),
            observations.isGameOver()
    );
    if (observations.getPlayerTurn().getSelectedCardIdx() != -1) {
      view.highlightCard(
              new ProviderCardAdapter(
                      observations.getPlayerTurn().getHand().get(
                              observations.getPlayerTurn().getSelectedCardIdx()
                      ), observations
              )
      );
    } else {
      view.highlightCard(view.getHighlightedCard());
    }
  }

  @Override
  public void addFeaturesListener(PlayerActionFeatures features) {
    view.addFeatures(new ViewFeaturesAdapter(features, observations));
  }
}
