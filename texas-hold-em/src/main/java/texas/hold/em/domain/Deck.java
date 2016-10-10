package texas.hold.em.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

public class Deck {
  public static final int DECK_SIZE = 7;

  public final Set<Card> cards = new TreeSet<>();

  public Deck(List<Card> cards) {
    this.cards.clear();
    this.cards.addAll(cards);
    if (this.cards.size() != DECK_SIZE) {
      this.cards.clear();
      throw new IllegalArgumentException("Invalid input cards");
    }
  }

  public List<Card> getCards() {
    return new ArrayList<>(cards);
  }

  public void pickCardFromDeckToHand(Value value, Hand hand) {
    Card card = removeCard(value);
    if (card == null)
      throw new IllegalArgumentException("Deck doesn't contain any cards of value: " + value);
    hand.addCard(card);
  }

  public Card removeCard(Value value) {
    Card foundCard = findCard(value);
    if (foundCard != null)
      cards.remove(foundCard);
    return foundCard;
  }

  public Card findCard(Value value) {
    try {
      return cards.stream().filter(card -> value == card.getValue()).findAny().get();
    }
    catch (NoSuchElementException e) {
      return null;
    }
  }
}
