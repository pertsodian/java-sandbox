package texas.hold.em.domain.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import texas.hold.em.app.Utils;
import texas.hold.em.domain.Deck;

public class StraightFlushRuleTest {
  
  private final StraightFlushRule rule = new StraightFlushRule();

  @Test
  public void testStraightFlushRule() {
    assertNull(rule.pick(new Deck(Utils.generateInputCards("AH", "2H", "5H", "8H", "3S", "QS", "4S"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("AH", "2H", "5H", "8H", "2S", "QS", "4D"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2H", "AH", "5H", "8H", "2C", "QS", "AD"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2H", "AH", "5H", "8C", "AC", "QC", "AD"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2H", "5H", "AH", "8C", "AC", "2D", "3D"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2H", "5H", "AH", "8C", "2C", "2S", "2D"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2H", "5D", "AH", "8D", "4H", "6H", "QH"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2H", "5D", "AH", "8C", "4H", "6H", "KH"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2H", "5D", "AH", "8C", "QH", "6H", "KH"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2H", "5D", "AH", "8C", "QH", "JH", "KH"))));
    assertEquals("[AH, KH, QH, JH, 10H]",
        rule.pick(new Deck(Utils.generateInputCards("10H", "5D", "AH", "8C", "QH", "JH", "KH"))).toString());
    assertEquals("[QH, JH, 10H, 9H, 8H]",
        rule.pick(new Deck(Utils.generateInputCards("10H", "5D", "AH", "8H", "QH", "JH", "9H"))).toString());
    assertEquals("[QH, JH, 10H, 9H, 8H]",
        rule.pick(new Deck(Utils.generateInputCards("10H", "7H", "AH", "8H", "QH", "JH", "9H"))).toString());
    assertEquals("[JH, 10H, 9H, 8H, 7H]",
        rule.pick(new Deck(Utils.generateInputCards("10H", "7H", "AH", "8H", "KH", "JH", "9H"))).toString());
    assertEquals("[6H, 5H, 4H, 3H, 2H]",
        rule.pick(new Deck(Utils.generateInputCards("4H", "2H", "AH", "6H", "KH", "3H", "5H"))).toString());
    assertEquals("[5H, 4H, 3H, 2H, AH]",
        rule.pick(new Deck(Utils.generateInputCards("4H", "2H", "AH", "8H", "KH", "3H", "5H"))).toString());
  }
}
