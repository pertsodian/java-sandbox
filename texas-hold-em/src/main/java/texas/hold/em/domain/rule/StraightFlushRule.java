package texas.hold.em.domain.rule;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import texas.hold.em.domain.Card;
import texas.hold.em.domain.Deck;
import texas.hold.em.domain.Hand;
import texas.hold.em.domain.Suit;
import texas.hold.em.domain.Value;

public class StraightFlushRule extends Rule {
  
  @Override
  public Hand pick(Deck deck) {
    Optional<Suit> possibleFlushSuit = findPossibleFlushSuit(deck);
    if (!possibleFlushSuit.isPresent())
      return null;
    
    Suit flushSuit = possibleFlushSuit.get();
    List<Card> cards = deck.getCards();
    Collections.sort(cards);
    List<Card> flushCards = cards.stream()
        .filter(card -> flushSuit == card.getSuit())
        .collect(Collectors.toList());
    
    Hand hand = new Hand();
    for (int i = 0; i <= flushCards.size()-Hand.HAND_SIZE; i++) {
      List<Card> possibleStraightFlushCards = flushCards.subList(i, i+Hand.HAND_SIZE);
      if (isStraight(possibleStraightFlushCards)) {
        hand.addCards(possibleStraightFlushCards);
        return hand;
      }   
    }
    if (Value.ACE == flushCards.get(0).getValue()) {
      List<Card> possibleStraightFlushCards = flushCards.subList(flushCards.size()-Hand.HAND_SIZE+1, flushCards.size());
      possibleStraightFlushCards.add(new Card(Value.ONE, flushSuit));
      if (isStraight(possibleStraightFlushCards)) {
        hand.addCards(possibleStraightFlushCards);
        return hand;
      } 
    }
    return null;
  }
  
}
