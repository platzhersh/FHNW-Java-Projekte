package webfr.flashcards.server.data;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import webfr.flashcards.shared.Bookmark;
import webfr.flashcards.shared.Card;
import webfr.flashcards.shared.Cardbox;
import webfr.flashcards.shared.User;

@Entity
@NamedQueries({
  @NamedQuery(
      name="findBookmark",
      query = "SELECT b FROM PBookmark b WHERE b.box = :cb AND b.user = :user")
})
@Table(name="BOOKMARKS")
public class PBookmark {
  @SequenceGenerator(name="SEQUENCE_GENERATOR", sequenceName="seq", initialValue=1, allocationSize=1)

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE_GENERATOR")
  private int id;
  @ManyToOne
  @JoinColumn(name="CardboxId")
  private Cardbox box;
  @ManyToOne
  @JoinColumn(name="UserId")
  private User user;
  @Column(name="LastLearned")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastLearned;
  @Column(name="CurrentCard")
  private int currentCard;
//  @Column(name="CardOrder")
//  @Lob
  private int[] CardOrder;
  
  public Bookmark toBookmark() {
    return new Bookmark(box, CardOrder, currentCard, lastLearned);
  }
  
  public PBookmark(Bookmark bm, User u) {
    try {
      initCommon(bm, u);
      List<Card>cards = this.box.getCards();
      List<Card>ordered = getOrderedCards(bm);
      this.currentCard = ordered.indexOf(bm.getCurrentCard());
      this.CardOrder = getOrderAsIntArray(cards, ordered);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  protected PBookmark(Bookmark bm, User u, int current, int[] order) {
    try {
      initCommon(bm, u);
      this.currentCard = current;
      this.CardOrder = order;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  private void initCommon(Bookmark bm, User u) {
    this.box = bm.getBox();
    this.user = u;
    this.lastLearned = bm.getLastLearned();
  }
  
  private int[] getOrderAsIntArray(List<Card> cards, List<Card> ordered) {
    int[] result = new int[cards.size()];
    int i = 0;
    for(Card c: ordered) {
      result[i++] = cards.indexOf(c);
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  private List<Card> getOrderedCards(Bookmark bm) throws Exception {
    Class<Bookmark> clazz = Bookmark.class;
    Field f = clazz.getDeclaredField("order");
    f.setAccessible(true);
    return (List<Card>)f.get(bm);
  }
  
  protected User getUser() {
    return user;
  }
}
