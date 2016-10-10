package texas.hold.em.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import texas.hold.em.app.Utils;

public class DeskTest {

  @Rule
  public final ExpectedException expectedException = ExpectedException.none();
  
  @Test
  public void testInvalidDesk() {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("Invalid input cards");
    new Deck(Utils.generateInputCards("AH", "AH", "5D", "8C", "2S", "QS", "4D"));
  }
  
  @Test
  public void testPickCardFromDeckToHand_Valid() {
    Hand hand = new Hand();
    Deck deck = new Deck(Utils.generateInputCards("AH", "8H", "5D", "7C", "2S", "QS", "4D"));
    deck.pickCardFromDeckToHand(Value.EIGHT, hand);
    
    assertEquals(1, hand.getCards().size());
    assertEquals("8H", hand.getCards().get(0).toString());
  }
  
  @Test
  public void testPickCardFromDeckToHand_Invalid() {
    Hand hand = new Hand();
    Deck deck = new Deck(Utils.generateInputCards("AH", "8H", "5D", "8C", "2S", "QS", "4D"));
    
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("Deck doesn't contain any cards of value: 7");
    deck.pickCardFromDeckToHand(Value.SEVEN, hand);
  }
}
