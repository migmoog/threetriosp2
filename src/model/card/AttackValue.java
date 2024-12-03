package model.card;

/**
 * A value ranging from [1, 10] that a card can have in a game of three trios.
 */
public enum AttackValue {
  ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5),
  SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10);

  private final int value;

  AttackValue(int v) {
    value = v;
  }

  /**
   * Construct an attack value from an int.
   *
   * @param val value ranging from 1-10
   * @return AttackValue representation of the integer.
   * @throws IllegalArgumentException if val is not in range [1,10]
   */
  public static AttackValue fromInt(int val) {
    for (AttackValue av : values()) {
      if (av.value == val) {
        return av;
      }
    }

    throw new IllegalArgumentException("Value is not in range [1, 10]");
  }

  /**
   * Retrieve the integer of an attack value.
   *
   * @return value ranging from [1, 10]
   */
  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    if (value == 10) {
      return "A";
    }
    return String.valueOf(value);
  }

  /**
   * Check if this value is greater than an opposing one.
   *
   * @param other the opposing value.
   * @return true if the caller is greater.
   */
  public boolean greaterThan(AttackValue other) {
    return value > other.value;
  }
}
