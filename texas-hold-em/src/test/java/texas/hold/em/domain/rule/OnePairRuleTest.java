package texas.hold.em.domain.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import texas.hold.em.app.Utils;
import texas.hold.em.domain.Deck;

public class OnePairRuleTest {

  private final OnePairRule rule = new OnePairRule();

  @Test
  public void testOnePairRule() {
    assertNull(rule.pick(new Deck(Utils.generateInputCards("AH", "2H", "5D", "8C", "3S", "QS", "4D"))));
    assertEquals("[2H, 2S, AH, QS, 8C]",
        rule.pick(new Deck(Utils.generateInputCards("AH", "2H", "5D", "8C", "2S", "QS", "4D"))).toString());
  }
}
