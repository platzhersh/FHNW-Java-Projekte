package webfr.flashcards.shared;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CARDS")
public class Card {
  @SequenceGenerator(name="SEQUENCE_GENERATOR", sequenceName="seq", initialValue=1, allocationSize=1)
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE_GENERATOR")
  @Column(name="Id")
  private Long id;
  @Column(name="Question", nullable=false)
  private String question = "";
  @Column(name="Answer", nullable=false)
  private String answer = "";
  @ManyToOne
  @JoinColumn(name="CardboxId")
  protected Cardbox owner;
  
  protected Card() {
    this.question = "";
    this.answer = "";
  }
  
  public Card(String question, String answer, Cardbox owner) {
    this.question = question;
    this.answer = answer;
    this.owner = owner;
  }
  
  public String getQuestion() {
    return question;
  }

  public String getAnswer() {
    return answer;
  }
  
  protected Long getId() {
    return id;
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
