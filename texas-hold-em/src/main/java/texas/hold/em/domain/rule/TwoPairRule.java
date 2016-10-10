package texas.hold.em.domain.rule;

import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.LongAdder;

import texas.hold.em.domain.Card;
import texas.hold.em.domain.Deck;
import texas.hold.em.domain.Hand;
import texas.hold.em.domain.Value;

public class TwoPairRule extends Rule {

  @Override
  public Hand pick(Deck deck) {
    TreeMap<Value, LongAdder> possiblePairValues = findPossiblePairValues(deck);
    if (possiblePairValues.size() < 2)
      return null;

    Hand hand = new Hand();
    
    Value highestRankPairValue = possiblePairValues.lastKey();
    possiblePairValues.remove(highestRankPairValue);
    pickPair(highestRankPairValue, deck, hand);

    Value secondHighestRankPairValue = possiblePairValues.lastKey();
    possiblePairValues.remove(secondHighestRankPairValue);
    pickPair(secondHighestRankPairValue, deck, hand);

    List<Card> cards = deck.getCards();
    Collections.sort(cards);
    hand.addCard(cards.get(0));
    
    return hand;
  }

}
