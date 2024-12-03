package model.actor;

/**
 * A color with a name and an associated RGB value.
 */
public enum Color {
  RED(255, 171, 173),
  ORANGE(255, 165, 0),
  YELLOW(255, 255, 0),
  GREEN(0, 255, 0),
  BLUE(71, 169, 255),
  PURPLE(255, 0, 255);

  private int r;
  private int g;
  private int b;

  Color(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  /**
   * Return the RGB value of this color.
   *
   * @return The r, g, b values in an array.
   */
  public int[] getRGB() {
    return new int[]{r, g, b};
  }

  public String toString() {
    return this.name();
  }
}
