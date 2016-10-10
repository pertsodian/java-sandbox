package texas.hold.em.domain.rule;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import texas.hold.em.domain.Card;
import texas.hold.em.domain.Deck;
import texas.hold.em.domain.Hand;
import texas.hold.em.domain.Suit;

public class FlushRule extends Rule {

  @Override
  public Hand pick(Deck deck) {
    Optional<Suit> possibleFlushSuit = findPossibleFlushSuit(deck);
    if (!possibleFlushSuit.isPresent())
      return null;
    
    Suit flushSuit = possibleFlushSuit.get();
    List<Card> cards = deck.getCards();
    Collections.sort(cards);
    List<Card> possibleFlushCards = cards.stream()
        .filter(card -> flushSuit == card.getSuit())
        .collect(Collectors.toList());
    
    Hand hand = new Hand();
    hand.addCards(possibleFlushCards.subList(0, Hand.HAND_SIZE));
    
    return hand;
  }
  
}
