package webfr.flashcards.shared;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Bookmark {
  private Cardbox box;
  private int currentCard;
  private List<Card> order = new LinkedList<Card>();
  private Date lastLearned;
  
  public Bookmark(Cardbox box, int[] ord, int current, Date lastLearned) {
    this.box = box;
    this.currentCard = current;
    this.lastLearned = lastLearned;
    
    if (ord == null) {
      for(Card c: box.getCards()) {
        order.add(c);
      }
    } else {
      List<Card>cards = box.getCards();
      for(int i: ord) {
        order.add(cards.get(i));
      }
    }    
  }
  
  public Cardbox getBox() {
    return box;
  }
  
  public void reset() {
    currentCard = 0;
    lastLearned = new Date();
  }
  
  public Card getCurrentCard() {
    int pos = (currentCard < order.size()) ? currentCard : order.size()-1;
    return (pos < 0) ? null : order.get(pos);
  }

  public Date getLastLearned() {
    return lastLearned;
  }

  public int getProgress() {
    return (currentCard < order.size()) ? currentCard : order.size();
  }
  
  public void wrongAnswer() {
    if (currentCard < order.size()) {
      Card c = order.get(currentCard);
      order.remove(currentCard);
      order.add(c);
    }
  }
  
  public void correctAnswer() {
    if (currentCard < order.size()) {
      currentCard++;
    }
  }
  
}
