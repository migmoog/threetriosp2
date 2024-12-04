package hw8.adapters;

import java.util.List;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Direction;
import cs3500.threetrios.provider.model.PlayerColor;
import model.ReadThreeTrios;
import model.actor.Actor;
import model.actor.Player;
import model.card.AttackValue;

public class CardAdapter implements Card {
  private model.card.Card ourCard;
  private ReadThreeTrios observations;
  private String name;

  public CardAdapter(model.card.Card card, ReadThreeTrios model) {
    this.ourCard = card;
    this.observations = model;
    for (int c = card.toString().length()-1; c >= 0; c--) {

    }
  }

  @Override
  public int getDamage(Direction dir) {
    return ourCard.getValue(model.card.Direction.fromProvider(dir)).getValue();
  }

  @Override
  public PlayerColor getPlayer() {
    Actor owner = getActor();
    return owner == null ? null : owner.getColor().toProvider();
  }

  @Override
  public void swapPlayer() {
    if(getPlayer() != null) {
      // if the card is owned on the board
      if (getActor().ownsCard(ourCard)) {
        getActor().loseCard(ourCard);
        getOtherActor().gainCard(ourCard);
      // if the card is owned in the hand
      } else {
        removeFromHand(getActor());
        addToHand(getOtherActor());
      }
    } else {
      throw new IllegalStateException("Card not owned by a player");
    }
  }

  // helper function that gets the actor who currently owns this card,
  // either in their hand or on the board
  private Actor getActor() {
    Actor owner = observations.whoOwns(ourCard);
    // if not, check if a player owns it in their hand
    if (owner == null) {
      List<Actor> actors = observations.getActors();
      for (Actor a : actors) {
        if (a.getHand().contains(ourCard)) {
          owner = a;
        }
      }
    }
    return owner;
  }

  private void removeFromHand(Actor player) {
    List<model.card.Card> handDeck = player.getHand();
    handDeck.remove(ourCard);
    player.setup(handDeck.size(), handDeck, player.getColor());
  }
  private void addToHand(Actor player) {
    List<model.card.Card> handDeck = player.getHand();
    handDeck.add(ourCard);
    player.setup(handDeck.size(), handDeck, player.getColor());
  }

  private Actor getOtherActor() {
    if (observations.getActors().size() != 2) {
      throw new IllegalArgumentException("can only swap card owner with 2 players");
    }
    for (Actor a : observations.getActors()) {
      if (!a.equals(getActor())) {
        return a;
      }
    }
    throw new IllegalArgumentException("Other player not found");
  }

  @Override
  public String getName() {
    return ourCard.toString();
  }

  @Override
  public Card copy() {
    return new CardAdapter(ourCard, observations);
  }
}