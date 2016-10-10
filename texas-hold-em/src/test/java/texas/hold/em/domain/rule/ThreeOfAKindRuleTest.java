package texas.hold.em.domain.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import texas.hold.em.app.Utils;
import texas.hold.em.domain.Deck;

public class ThreeOfAKindRuleTest {

  private final ThreeOfAKindRule rule = new ThreeOfAKindRule();
  
  @Test
  public void testThreeOfAKindRule() {
    assertNull(rule.pick(new Deck(Utils.generateInputCards("AH", "2H", "5D", "8C", "3S", "QS", "4D"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("AH", "2H", "5D", "8C", "2S", "QS", "4D"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2H", "AH", "5D", "8C", "2S", "QS", "AD"))));
    assertEquals("[AH, AC, AD, QS, 8C]",
        rule.pick(new Deck(Utils.generateInputCards("2H", "AH", "5D", "8C", "AC", "QS", "AD"))).toString());
  }
}
