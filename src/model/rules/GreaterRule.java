package model.rules;

import model.card.AttackValue;
import model.card.Card;
import model.card.Direction;

/**
 * A battle rule that determines victory if the attacker is greater than the defender.
 */
public class GreaterRule implements BattleRule {
  @Override
  public boolean fight(Card attacker, Card defender, Direction attackDir) {
    AttackValue offense = attacker.getValue(attackDir);
    AttackValue defense = defender.getValue(attackDir.getNeighbor());

    return offense.greaterThan(defense);
  }
}
