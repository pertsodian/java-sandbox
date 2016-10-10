package texas.hold.em.app;

import java.util.ArrayList;
import java.util.List;

import texas.hold.em.domain.Card;

public class Utils {

  public static List<Card> generateInputCards(String... args) {
    List<Card> cards = new ArrayList<>();
    for (String card : args) {
      cards.add(new Card(card));
    }
    return cards;
  }
}
