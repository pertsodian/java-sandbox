package texas.hold.em.domain.rule;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import texas.hold.em.domain.Card;
import texas.hold.em.domain.Deck;
import texas.hold.em.domain.Hand;
import texas.hold.em.domain.Suit;
import texas.hold.em.domain.Value;

public abstract class Rule {

  private static final int PAIR = 2;
  private static final int TRIPLE = 3;
  private static final int QUAD = 4;
  private static final int QUINT = 5;
  
  public abstract Hand pick(Deck deck);
  
  /* *********** STRAIGHT ********** */
  protected boolean isStraight(List<Card> cards) {
    int[] diff = new int[cards.size()-1];
    IntStream.range(0, cards.size()-1).forEach(i ->
      diff[i] = cards.get(i).getValue().getIntValue() - cards.get(i+1).getValue().getIntValue());
    return !Arrays.stream(diff).filter(i -> i != 1).findAny().isPresent();
  }
  
  /* *********** FLUSH ********** */
  protected TreeMap<Suit, LongAdder> countBySuit(Deck deck) {
    TreeMap<Suit, LongAdder> suitMap = new TreeMap<>();
    deck.getCards().stream().map(card -> card.getSuit())
        .forEach(suit -> suitMap.computeIfAbsent(suit, k -> new LongAdder()).increment());
    return suitMap;
  }
  
  protected Optional<Suit> findPossibleFlushSuit(Deck deck) {
    return filterSuitByCount(deck, QUINT);
  }
  
  private Optional<Suit> filterSuitByCount(Deck deck, int count) {
    return countBySuit(deck).entrySet().stream()
        .filter(entry -> entry.getValue().intValue() >= count)
        .map(entry -> entry.getKey()).findAny();
  }
  
  /* *********** PAIR, TRIPLE, QUAD ********** */
  protected TreeMap<Value, LongAdder> countByValue(Deck deck) {
    TreeMap<Value, LongAdder> valueMap = new TreeMap<>();
    deck.getCards().stream().map(card -> card.getValue())
        .forEach(value -> valueMap.computeIfAbsent(value, k -> new LongAdder()).increment());
    return valueMap;
  }

  protected TreeMap<Value, LongAdder> findPossiblePairValues(Deck deck) {
    return filterValueByCount(deck, PAIR);
  }
  
  protected TreeMap<Value, LongAdder> findPossibleTripleValues(Deck deck) {
    return filterValueByCount(deck, TRIPLE);
  }
  
  protected TreeMap<Value, LongAdder> findPossibleQuadValues(Deck deck) {
    return filterValueByCount(deck, QUAD);
  }
  
  private TreeMap<Value, LongAdder> filterValueByCount(Deck deck, int count) {
    return countByValue(deck).entrySet().stream()
        .filter(entry -> entry.getValue().intValue() >= count)
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a,b) -> a, TreeMap::new));
  }
  
  /* *********** PICK CARD ********** */  
  protected void pickPair(Value value, Deck deck, Hand hand) {
    pickCard(value, deck, hand, PAIR);
  }
  
  protected void pickTriple(Value value, Deck deck, Hand hand) {
    pickCard(value, deck, hand, TRIPLE);
  }
  
  protected void pickQuad(Value value, Deck deck, Hand hand) {
    pickCard(value, deck, hand, QUAD);
  }
  
  private void pickCard(Value value, Deck deck, Hand hand, int times) {
    while (times-- > 0)
      deck.pickCardFromDeckToHand(value, hand);
  }
}
