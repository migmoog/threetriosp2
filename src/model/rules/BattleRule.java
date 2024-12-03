package model.rules;

import model.card.Card;
import model.card.Direction;

/**
 * A rule that will be applied to cards in the battle phase.
 * (i.e., in Three Trios, the rule is that the greater number wins)
 */
public interface BattleRule {
  /**
   * Checks if the attacker defeats the defender in a battle phase.
   * (i.e., in Th
   *
   * @param attacker  the offense
   * @param defender  the defense
   * @param attackDir the direction the offense is attacking
   * @return if the attacker won the encounter.
   */
  boolean fight(Card attacker, Card defender, Direction attackDir);
}
