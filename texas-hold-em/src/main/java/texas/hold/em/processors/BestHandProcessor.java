package texas.hold.em.processors;

import java.util.ArrayList;
import java.util.List;

import texas.hold.em.domain.Deck;
import texas.hold.em.domain.Hand;
import texas.hold.em.domain.rule.Rule;

public class BestHandProcessor {

  private final List<Rule> rules = new ArrayList<>();

  public BestHandProcessor(List<Rule> rules) {
    this.rules.clear();
    this.rules.addAll(rules);
  }

  public Hand pickBestHand(Deck deck) {
    for (Rule rule : rules) {
      Hand hand = rule.pick(deck);
      if (hand != null)
        return hand;
    }
    return null;
  }
}
