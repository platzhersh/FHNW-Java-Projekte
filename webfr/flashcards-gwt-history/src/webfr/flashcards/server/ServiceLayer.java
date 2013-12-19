package webfr.flashcards.server;

import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import webfr.flashcards.server.data.Database;
import webfr.flashcards.server.data.PBookmark;
import webfr.flashcards.shared.Bookmark;
import webfr.flashcards.shared.Card;
import webfr.flashcards.shared.Cardbox;
import webfr.flashcards.shared.User;

public class ServiceLayer {
  @PersistenceContext(unitName="flashcardsDB")
  EntityManager em;
  
  private Database db;
  
  public ServiceLayer() {
    try {
      db = new Database("/data/dataset.xml");
      db.initDatabase();
      
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("flashcardsDB");
      em = emf.createEntityManager();
      
      initBookmarks();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  protected void initBookmarks() {
    User u = getUser("Christoph");
    Cardbox box1 = getCardboxById(1);
    Cardbox box2 = getCardboxById(2);
    Bookmark bm1 = new Bookmark(box1, new int[]{11, 7, 6, 5, 4, 1, 9, 0, 10, 3, 6, 8}, 2, box1.getVersion());
    Bookmark bm2 = 
        new Bookmark(box2, new int[]{0, 2, 3, 4, 5, 6, 1, 10, 7, 13, 8, 12, 9, 11}, 5, box2.getVersion());
    newBookmark(bm1, u);
    newBookmark(bm2, u);
  }

  protected ServiceLayer(Object o) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("flashcardsDB");
    em = emf.createEntityManager();    
  }

  public void newCardbox(Cardbox c) {
    em.getTransaction().begin();
    em.persist(c);
    em.getTransaction().commit();
  }
  
  @SuppressWarnings("unchecked")
  public List<Cardbox> getCardboxes() {
    return em.createNamedQuery("findAllCardboxes").getResultList();
  }
  
  public Cardbox getCardboxById(long id) {
    return em.find(Cardbox.class, id);
  }
  
//  public void updateCardboxOnly(Cardbox c) {
//    em.getTransaction().begin();
//    c.setVersion();
//    em.merge(c);
//    em.getTransaction().commit();
//  }
  
  public void updateCardboxWithCards(Cardbox c) {
    em.getTransaction().begin();
    @SuppressWarnings("unchecked")
    Class<Cardbox>cbclass = (Class<Cardbox>)c.getClass();
    try {
      Method m = cbclass.getDeclaredMethod("setVersion");
      m.setAccessible(true);
      m.invoke(c);
    } catch (Exception e) {
      // ignore new version setting
      e.printStackTrace();
    }
    em.merge(c);
    for (Card card : c.getCards()) {
      em.merge(card);
    }
    em.getTransaction().commit();
  }
  
  public void deleteCardbox(Cardbox c) {
    em.getTransaction().begin();
    em.remove(c);
    em.getTransaction().commit();
  }
  
  public void newCard(Card c) {
    em.getTransaction().begin();
    em.persist(c);
    em.getTransaction().commit();
  }
  
  public List<Card> getCardsFor(Cardbox c) {
    return c.getCards();
  }
  
  public void updateCard(Card c) {
    em.getTransaction().begin();
    em.merge(c);
    em.getTransaction().commit();
  }
  
  public void deleteCard(Card c) {
    em.getTransaction().begin();
    em.remove(c);
    em.getTransaction().commit();      
  }
  
  public User getUser(String name) {
    return (User)em.createNamedQuery("findUserByName")
        .setParameter("username", name)
        .getSingleResult();
  }
  
  public Bookmark getBookmark(Cardbox box, User u) {
    PBookmark pbm = (PBookmark)em.createQuery("SELECT b FROM PBookmark b WHERE b.box = :cardbox AND b.user = :user")
        .setParameter("cardbox", box)
        .setParameter("user", u)
        .getSingleResult();
    return pbm != null ? pbm.toBookmark() : null;
  }
  
  public void newBookmark(Bookmark bm, User u) {
    PBookmark pbm = new PBookmark(bm, u);
    em.getTransaction().begin();
    em.persist(pbm);
    em.getTransaction().commit();
  }
  
  public void updateBookmark(Bookmark bm, User u) {
    PBookmark pbm = new PBookmark(bm, u);
    em.getTransaction().begin();
    em.merge(pbm);
    em.getTransaction().commit();
  }
  
}
