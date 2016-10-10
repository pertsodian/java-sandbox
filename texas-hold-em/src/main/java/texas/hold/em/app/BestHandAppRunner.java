package texas.hold.em.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import texas.hold.em.domain.Deck;
import texas.hold.em.domain.Hand;
import texas.hold.em.domain.rule.FlushRule;
import texas.hold.em.domain.rule.FourOfAKindRule;
import texas.hold.em.domain.rule.FullHouseRule;
import texas.hold.em.domain.rule.HighCardRule;
import texas.hold.em.domain.rule.OnePairRule;
import texas.hold.em.domain.rule.Rule;
import texas.hold.em.domain.rule.StraightFlushRule;
import texas.hold.em.domain.rule.StraightRule;
import texas.hold.em.domain.rule.ThreeOfAKindRule;
import texas.hold.em.domain.rule.TwoPairRule;
import texas.hold.em.processors.BestHandProcessor;

public class BestHandAppRunner {

  private final BestHandProcessor processor;

  public BestHandAppRunner(BestHandProcessor processor) {
    this.processor = processor;
  }

  public String process(String input) {
    Hand hand = processor.pickBestHand(new Deck(Utils.generateInputCards(input.split(" "))));
    return hand.toString();
  }

  public static void main(String[] args) {
    List<Rule> rules = new ArrayList<>();
    // Rules need to be added by importance order
    rules.add(new StraightFlushRule());
    rules.add(new FourOfAKindRule());
    rules.add(new FullHouseRule());
    rules.add(new FlushRule());
    rules.add(new StraightRule());
    rules.add(new ThreeOfAKindRule());
    rules.add(new TwoPairRule());
    rules.add(new OnePairRule());
    rules.add(new HighCardRule());
    BestHandProcessor processor = new BestHandProcessor(rules);

    BestHandAppRunner appRunner = new BestHandAppRunner(processor);
    Scanner scanner = new Scanner(System.in);
    String input = scanner.nextLine();
    while (!"END".equals(input)) {
      System.out.println(appRunner.process(input));
      input = scanner.nextLine();
    }
    scanner.close();
  }
}
