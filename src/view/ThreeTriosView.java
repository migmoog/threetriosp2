package view;

import controller.PlayerActionFeatures;

/**
 * An interface laying out the functions of a Three Trios GUI.
 */
public interface ThreeTriosView {

  /**
   * Render the current game to the screen.
   */
  public void render();

  /**
   * Connect a listener for feature notifications emitted by the view.
   *
   * @param features the object that listens for feature notifications.
   */
  public void addFeaturesListener(PlayerActionFeatures features);
}