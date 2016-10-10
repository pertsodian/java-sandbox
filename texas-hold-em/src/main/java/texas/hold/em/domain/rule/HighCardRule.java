package texas.hold.em.domain.rule;

import java.util.Collections;
import java.util.List;

import texas.hold.em.domain.Card;
import texas.hold.em.domain.Deck;
import texas.hold.em.domain.Hand;

public class HighCardRule extends Rule {

  /**
   * Pick top 5
   */
  @Override
  public Hand pick(Deck deck) {
    List<Card> cards = deck.getCards();
    Collections.sort(cards);

    Hand hand = new Hand();
    hand.addCards(cards.subList(0, Hand.HAND_SIZE));

    return hand;
  }

}
