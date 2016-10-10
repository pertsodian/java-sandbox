package texas.hold.em.domain;

public enum Value {
  ONE("A", 1), TWO("2", 2), THREE("3", 3), FOUR("4", 4), FIVE("5", 5), SIX("6", 6), SEVEN("7", 7),
  EIGHT("8", 8), NINE("9",9), TEN("10", 10), JACK("J", 11), QUEEN("Q", 12), KING("K", 13), ACE("A", 14);

  private String displayValue;
  private int intValue;

  private Value(String displayValue, int intValue) {
    this.displayValue = displayValue;
    this.intValue = intValue;
  }

  public int getIntValue() {
    return intValue;
  }

  public static Value getValue(String strValue) {
    for (Value value : Value.values())
      if (value != ONE && value.displayValue.equals(strValue))
        return value;
    throw new IllegalArgumentException("Invalid value for Value: " + strValue);
  }

  @Override
  public String toString() {
    return displayValue;
  }
}
