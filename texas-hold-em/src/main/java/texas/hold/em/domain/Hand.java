package texas.hold.em.domain;

import java.util.ArrayList;
import java.util.List;

public class Hand {
  public static final int HAND_SIZE = 5;

  public final List<Card> cards = new ArrayList<>();

  public List<Card> getCards() {
    return cards;
  }

  public void addCards(List<Card> cards) {
    if (this.cards.size() + cards.size() > HAND_SIZE)
      throw new IllegalArgumentException("Hand is already full");
    this.cards.addAll(cards);
  }

  public void addCard(Card card) {
    if (cards.size() < HAND_SIZE)
      cards.add(card);
    else
      throw new IllegalArgumentException("Hand is already full");
  }

  @Override
  public String toString() {
    return cards.toString();
  }
}
