package texas.hold.em.domain.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import texas.hold.em.app.Utils;
import texas.hold.em.domain.Deck;

public class TwoPairRuleTest {

  private final TwoPairRule rule = new TwoPairRule();

  @Test
  public void testTwoPairRule() {
    assertNull(rule.pick(new Deck(Utils.generateInputCards("AH", "2H", "5D", "8C", "3S", "QS", "4D"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("AH", "2H", "5D", "8C", "2S", "QS", "4D"))));
    assertEquals("[AH, AD, 2H, 2S, QS]",
        rule.pick(new Deck(Utils.generateInputCards("2H", "AH", "5D", "8C", "2S", "QS", "AD"))).toString());
  }
}
