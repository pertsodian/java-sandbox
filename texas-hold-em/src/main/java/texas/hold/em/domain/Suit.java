package texas.hold.em.domain;

public enum Suit {
  HEART("H"), SPADES("S"), CLUB("C"), DIAMOND("D");

  private String displayValue;

  private Suit(String displayValue) {
    this.displayValue = displayValue;
  }
  
  public static Suit getSuit(String strValue) {
    for (Suit suit : Suit.values())
      if (suit.displayValue.equals(strValue))
        return suit;
    throw new IllegalArgumentException("Invalid value for Suit: " + strValue);
  }

  @Override
  public String toString() {
    return displayValue;
  }
}
