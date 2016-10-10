package texas.hold.em.domain.rule;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import texas.hold.em.app.Utils;
import texas.hold.em.domain.Deck;

public class HighCardRuleTest {

  private final Rule rule = new HighCardRule();

  @Test
  public void testHighCardRule() {
    assertEquals("[AH, QS, 8C, 5D, 4D]",
        rule.pick(new Deck(Utils.generateInputCards("AH", "2H", "5D", "8C", "2S", "QS", "4D"))).toString());
  }
}
