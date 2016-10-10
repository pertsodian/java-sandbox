package texas.hold.em.domain.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import texas.hold.em.app.Utils;
import texas.hold.em.domain.Deck;

public class FullHouseRuleTest {
  
  private final FullHouseRule rule = new FullHouseRule();

  @Test
  public void testFullHouseRule() {
    assertNull(rule.pick(new Deck(Utils.generateInputCards("AH", "2H", "5D", "8C", "3S", "QS", "4D"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("AH", "2H", "5D", "8C", "2S", "QS", "4D"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2H", "AH", "5D", "8C", "2S", "QS", "AD"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2H", "AH", "5D", "8C", "AC", "QS", "AD"))));
    assertEquals("[2H, 2S, 2D, AH, AC]",
        rule.pick(new Deck(Utils.generateInputCards("2H", "5D", "AH", "8C", "AC", "2S", "2D"))).toString());
    assertEquals("[AH, AC, AD, 2H, 2S]",
        rule.pick(new Deck(Utils.generateInputCards("2H", "AD", "AH", "8C", "AC", "2S", "2D"))).toString());
  }
}
