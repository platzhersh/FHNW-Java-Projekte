package webfr.flashcards.server.model;

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

@Entity
@NamedQueries({
  @NamedQuery(
      name="findAllCardsForCardbox",
      query = "SELECT c FROM Card c WHERE c.owner = :box")
})
@Table(name="CARDS")
public class Card {
  @SequenceGenerator(name="SEQUENCE_GENERATOR", sequenceName="seq", initialValue=1, allocationSize=1)
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE_GENERATOR")
  @Column(name="Id", nullable=false)
  private Long id;
  @Column(name="Question", nullable=false)
  private String question = "";
  @Column(name="Answer", nullable=false)
  private String answer = "";
  @ManyToOne
  @JoinColumn(name="CardboxId")
  private Cardbox owner;
  
  protected Card() {}
  
  public Card(String question, String answer, Cardbox owner) {
    this.question = question;
    this.answer = answer;
    this.owner = owner;
  }
  
  public void setQuestion(String q) {
    if (q != null) question = q;
    else throw new IllegalArgumentException();
  }

  public String getQuestion() {
    return question;
  }

  public void setAnswer(String a) {
    if (a != null) answer = a;
    else throw new IllegalArgumentException();
  }

  public String getAnswer() {
    return answer;
  }
  
  protected Long getId() {
    return id;
  }

  public Cardbox getOwner() {
    return owner;
  }
  
  @Override
  public boolean equals(Object o) {
    if (o instanceof Card) {
      Card c = (Card)o;
      return (question.equals(c.getQuestion()) && answer.equals(c.getAnswer()));
    } else {
      return false;
    }
  }

}
