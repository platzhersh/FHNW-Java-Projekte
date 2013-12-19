package webfr.flashcards.client.view;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import webfr.flashcards.shared.Card;
import webfr.flashcards.shared.Cardbox;

public class DataProviderUtility {
  private DataProviderUtility(){}
  
  public static List<Cardbox> provideCardboxes() {
    LinkedList<Cardbox> boxes = new LinkedList<Cardbox>();
    boxes.add(new Cardbox("Box 1", "Description 1", new Date()));
    boxes.add(new Cardbox("Box 2", "Description 2", new Date()));
    boxes.add(new Cardbox("Box 3", "Description 3", new Date()));
    boxes.add(new Cardbox("Box 4", "Description 4", new Date()));

    for (Cardbox box : boxes) {
      List<Card>cards = box.getCards();
      for (int i = 0; i < 10; i++) {
        cards.add(new Card(box.getTitle() + ": Q" + i, "A" + i, box));
      }
    }
    return boxes;
  }
}
