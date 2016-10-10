package texas.hold.em.domain.rule;

import java.util.TreeMap;
import java.util.concurrent.atomic.LongAdder;

import texas.hold.em.domain.Deck;
import texas.hold.em.domain.Hand;
import texas.hold.em.domain.Value;

public class FullHouseRule extends Rule {

  @Override
  public Hand pick(Deck deck) {
    TreeMap<Value, LongAdder> possibleTripleValues = findPossibleTripleValues(deck);
    if (possibleTripleValues.isEmpty())
      return null;
    
    Hand hand = new Hand();
    
    Value highestRankTripleValue = possibleTripleValues.lastKey();
    pickTriple(highestRankTripleValue, deck, hand);
    
    TreeMap<Value, LongAdder> possiblePairValues = findPossiblePairValues(deck);
    possiblePairValues.remove(highestRankTripleValue);
    if (possiblePairValues.isEmpty())
      return null;
    
    Value highestRankPairValue = possiblePairValues.lastKey();
    pickPair(highestRankPairValue, deck, hand);
    
    return hand;
  }
  
}
