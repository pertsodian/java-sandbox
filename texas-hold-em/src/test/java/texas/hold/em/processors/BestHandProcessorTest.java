package texas.hold.em.processors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import texas.hold.em.domain.Deck;
import texas.hold.em.domain.Hand;
import texas.hold.em.domain.rule.Rule;

@RunWith(MockitoJUnitRunner.class)
public class BestHandProcessorTest {
  
  @Mock
  private Rule firstRule;
  @Mock
  private Rule secondRule;
  @Mock
  private Deck deck;
  
  
  private BestHandProcessor processor;
  
  @Before
  public void setUp() {
    processor = new BestHandProcessor(Arrays.asList(firstRule, secondRule));
  }
  
  @Test
  public void testFirstRuleMatch() {
    Mockito.when(firstRule.pick(deck)).thenReturn(new Hand());
    assertNotNull(processor.pickBestHand(deck));
  }
  
  @Test
  public void testSecondRuleMatch() {
    Mockito.when(firstRule.pick(deck)).thenReturn(null);
    Mockito.when(secondRule.pick(deck)).thenReturn(new Hand());
    assertNotNull(processor.pickBestHand(deck));
  }
  
  @Test
  public void testNoMatch() {
    Mockito.when(firstRule.pick(deck)).thenReturn(null);
    Mockito.when(secondRule.pick(deck)).thenReturn(null);
    assertNull(processor.pickBestHand(deck));
  }
}
