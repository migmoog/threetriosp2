package controller;

import java.util.ArrayList;
import java.util.List;

import model.actor.Actor;

/**
 * A class representing multiple model status features that can execute notifications in bulk.
 */
public class ModelStatusListeners implements ModelStatusFeatures {
  private List<ModelStatusFeatures> listeners;

  public ModelStatusListeners() {
    listeners = new ArrayList<>();
  }

  @Override
  public void beginTurn(Actor actor) {
    for (ModelStatusFeatures listener : listeners) {
      listener.beginTurn(actor);
    }
  }

  @Override
  public void endTurn(Actor actor) {
    for (ModelStatusFeatures listener : listeners) {
      listener.endTurn(actor);
    }
  }

  @Override
  public void updateBoard() {
    for (ModelStatusFeatures listener : listeners) {
      listener.updateBoard();
    }
  }

  /**
   * Add a listener to send notifications to.
   *
   * @param listener the listener to add.
   */
  public void add(ModelStatusFeatures listener) {
    listeners.add(listener);
  }
}
