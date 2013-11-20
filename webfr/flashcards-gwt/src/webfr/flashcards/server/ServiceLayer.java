package webfr.flashcards.server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import webfr.flashcards.server.data.Database;
import webfr.flashcards.server.model.Card;
import webfr.flashcards.server.model.Cardbox;

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
      
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  protected ServiceLayer(Object o) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("flashcardsDB");
    em = emf.createEntityManager();    
  }

  public void newCardbox(Cardbox c) {
    em.getTransaction().begin();
    em.persist(c);
    for (Card card : c.getCards()) {
      em.persist(card);
    }
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
    c.setVersion();
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
    if (c.getOwner() == null || c.getOwner().getCards().contains(c)) {
      em.getTransaction().begin();
      em.persist(c);
      em.getTransaction().commit();
    } else {
      throw new IllegalArgumentException("new card is not contained in a (owning) cardbox!");
    }
  }
  
  @SuppressWarnings("unchecked")
  public List<Card> getCardsFor(Cardbox c) {
    Query q = em.createNamedQuery("findAllCardsForCardbox");
    q.setParameter("box", c);
    return q.getResultList();
  }
  
  public void updateCard(Card c) {
    em.getTransaction().begin();
    em.merge(c);
    em.getTransaction().commit();
  }
  
  public void deleteCard(Card c) {
    if (c.getOwner() != null && !c.getOwner().getCards().contains(c)) {
      em.getTransaction().begin();
      em.remove(c);
      em.getTransaction().commit();      
    }
  }
  
}
