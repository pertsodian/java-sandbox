package texas.hold.em.domain.rule;

import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.LongAdder;

import texas.hold.em.domain.Card;
import texas.hold.em.domain.Deck;
import texas.hold.em.domain.Hand;
import texas.hold.em.domain.Value;

public class ThreeOfAKindRule extends Rule {

  @Override
  public Hand pick(Deck deck) {
    TreeMap<Value, LongAdder> possibleTripleValues = findPossibleTripleValues(deck);
    if (possibleTripleValues.isEmpty())
      return null;
    
    Hand hand = new Hand();
    
    Value highestRankTripleValue = possibleTripleValues.lastKey();
    pickTriple(highestRankTripleValue, deck, hand);
    
    List<Card> cards = deck.getCards();
    Collections.sort(cards);
    hand.addCards(cards.subList(0, Hand.HAND_SIZE-3));
    
    return hand;
  }

}
