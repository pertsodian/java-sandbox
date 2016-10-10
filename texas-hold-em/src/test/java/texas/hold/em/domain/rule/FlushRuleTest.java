package texas.hold.em.domain.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import texas.hold.em.app.Utils;
import texas.hold.em.domain.Deck;

public class FlushRuleTest {

  private final FlushRule rule = new FlushRule();

  @Test
  public void testFlushRule() {
    assertNull(rule.pick(new Deck(Utils.generateInputCards("AH", "2H", "5H", "8H", "3S", "QS", "4S"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("AH", "2H", "5H", "8H", "2S", "QS", "4D"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2H", "AH", "5H", "8H", "2C", "QS", "AD"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2H", "AH", "5H", "8C", "AC", "QC", "AD"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2H", "5H", "AH", "8C", "AC", "2D", "3D"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2H", "5H", "AH", "8C", "2C", "2S", "2D"))));
    assertEquals("[AH, KH, 6H, 4H, 2H]",
        rule.pick(new Deck(Utils.generateInputCards("2H", "5D", "AH", "8D", "4H", "6H", "KH"))).toString());
    assertEquals("[AH, KH, 6H, 4H, 2H]",
        rule.pick(new Deck(Utils.generateInputCards("2H", "5D", "AH", "8C", "4H", "6H", "KH"))).toString());
    assertEquals("[AH, KH, 8H, 6H, 4H]",
        rule.pick(new Deck(Utils.generateInputCards("2H", "5D", "AH", "8H", "4H", "6H", "KH"))).toString());
  }
}
