package texas.hold.em.domain.rule;

import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.LongAdder;

import texas.hold.em.domain.Card;
import texas.hold.em.domain.Deck;
import texas.hold.em.domain.Hand;
import texas.hold.em.domain.Value;

public class FourOfAKindRule extends Rule {
  
  @Override
  public Hand pick(Deck deck) {
    TreeMap<Value, LongAdder> possibleQuadValues = findPossibleQuadValues(deck);
    if (possibleQuadValues.isEmpty())
      return null;
    
    Hand hand = new Hand();
    
    Value highestRankQuadValue = possibleQuadValues.lastKey();
    pickQuad(highestRankQuadValue, deck, hand);
    
    List<Card> cards = deck.getCards();
    Collections.sort(cards);
    hand.addCard(cards.get(0));
    
    return hand;
  }
  
}
