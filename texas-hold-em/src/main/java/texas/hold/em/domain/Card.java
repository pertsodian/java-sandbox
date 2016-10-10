package texas.hold.em.domain;

public class Card implements Comparable<Card>{
  private final Value value;
  private final Suit suit;

  public Card(String card) {
    this(Value.getValue(card.substring(0, card.length()-1)),
        Suit.getSuit(card.substring(card.length()-1, card.length())));
  }

  public Card(Value value, Suit suit) {
    this.value = value;
    this.suit = suit;
  }

  public Value getValue() {
    return value;
  }

  public Suit getSuit() {
    return suit;
  }

  @Override
  public String toString() {
    return value.toString() + suit.toString();
  }

  @Override
  public int compareTo(Card another) {
    if (this.value != another.value)
      return Integer.compare(another.value.getIntValue(), this.value.getIntValue());
    return this.suit.compareTo(another.suit);
  }
}
