package texas.hold.em.domain.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import texas.hold.em.app.Utils;
import texas.hold.em.domain.Deck;

public class StraightRuleTest {
  
  private final StraightRule rule = new StraightRule();

  @Test
  public void testStraightRule() {
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2C", "2D", "AS", "AC", "4H", "4D", "KH"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2C", "5D", "AS", "8C", "4H", "6D", "KH"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2C", "5D", "AS", "8C", "QH", "6D", "KH"))));
    assertNull(rule.pick(new Deck(Utils.generateInputCards("2C", "5D", "AS", "8C", "QH", "JD", "KH"))));
    assertEquals("[AD, KH, QC, JS, 10H]",
        rule.pick(new Deck(Utils.generateInputCards("10H", "AD", "7H", "8C", "QC", "JS", "KH"))).toString());
    assertEquals("[AH, KH, QC, JS, 10H]",
        rule.pick(new Deck(Utils.generateInputCards("10H", "AD", "AH", "AC", "QC", "JS", "KH"))).toString());
    assertEquals("[AD, KH, QC, JS, 10H]",
        rule.pick(new Deck(Utils.generateInputCards("10H", "AD", "10C", "JC", "QC", "JS", "KH"))).toString());
    assertEquals("[AD, KH, QC, JS, 10H]",
        rule.pick(new Deck(Utils.generateInputCards("10H", "AD", "7H", "9C", "QC", "JS", "KH"))).toString());
    assertEquals("[AH, KH, QC, JS, 10H]",
        rule.pick(new Deck(Utils.generateInputCards("10H", "AD", "AH", "9C", "QC", "JS", "KH"))).toString());
    assertEquals("[QC, JS, 10H, 9C, 8H]",
        rule.pick(new Deck(Utils.generateInputCards("10H", "AD", "8H", "9C", "QC", "JS", "7H"))).toString());
    assertEquals("[6H, 5C, 4S, 3H, 2H]",
        rule.pick(new Deck(Utils.generateInputCards("6H", "AD", "3H", "5C", "QC", "4S", "2H"))).toString());
    assertEquals("[5C, 4S, 3H, 2H, AD]",
        rule.pick(new Deck(Utils.generateInputCards("7H", "AD", "3H", "5C", "QC", "4S", "2H"))).toString());
  }
}
