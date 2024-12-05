package hw8.adapters;

import java.util.List;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.PlayerColor;
import model.ReadThreeTrios;
import model.actor.Actor;
import model.card.AttackValue;
import cs3500.threetrios.provider.model.Direction;

/**
 * An adapter to use the provider's card interface with our own.
 */
public class OurCardAdapter implements model.card.Card {
  private Card providerCard;

  /**
   * Construct a card adapter.
   * @param providerCard the provider's card to use.
   */
  public OurCardAdapter(Card providerCard) {
    this.providerCard = providerCard;
  }

  @Override
  public int getScore() {
    return providerCard.getDamage(Direction.NORTH) +
            providerCard.getDamage(Direction.SOUTH) +
            providerCard.getDamage(Direction.EAST) +
            providerCard.getDamage(Direction.WEST);
  }

  @Override
  public AttackValue getValue(model.card.Direction dir) {
    return AttackValue.fromInt(providerCard.getDamage(dir.toProvider()));
  }
}
