package texas.hold.em.domain.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import texas.hold.em.domain.Card;
import texas.hold.em.domain.Deck;
import texas.hold.em.domain.Hand;
import texas.hold.em.domain.Value;

public class StraightRule extends Rule {

  @Override
  public Hand pick(Deck deck) {
    TreeMap<Value, LongAdder> valueMap = countByValue(deck);
    if (valueMap.size() < Hand.HAND_SIZE)
      return null;
    
    Hand hand = new Hand();
    List<Value> values = new ArrayList<>(valueMap.descendingKeySet());
    for (int i = 0; i <= values.size()-Hand.HAND_SIZE; i++) {
      List<Card> possibleStraightCards =
          values.subList(i, i+Hand.HAND_SIZE).stream()
              .map(value -> deck.findCard(value)).collect(Collectors.toList());
      if (isStraight(possibleStraightCards)) {
        hand.addCards(possibleStraightCards);
        return hand;
      }   
    }
    if (Value.ACE == values.get(0)) {
      List<Card> possibleStraightCards =
          values.subList(values.size()-Hand.HAND_SIZE+1, values.size()).stream()
              .map(value -> deck.findCard(value)).collect(Collectors.toList());
      Card ace = deck.findCard(Value.ACE);
      possibleStraightCards.add(new Card(Value.ONE, ace.getSuit()));
      if (isStraight(possibleStraightCards)) {
        hand.addCards(possibleStraightCards);
        return hand;
      } 
    }
    return null;
  }
  
}
