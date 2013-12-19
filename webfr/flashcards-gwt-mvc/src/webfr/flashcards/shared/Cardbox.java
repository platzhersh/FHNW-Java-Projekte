/**
 * 
 */
package webfr.flashcards.shared;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
  @NamedQuery(
      name="findAllCardboxes",
      query = "SELECT c FROM Cardbox c"),
  @NamedQuery(
      name="findCardboxById",
      query = "SELECT c FROM Cardbox c WHERE c.id = :id" )
})
@Table(name="CARDBOXES")
public class Cardbox {
  @SequenceGenerator(name="SEQUENCE_GENERATOR", sequenceName="seq", initialValue=1, allocationSize=1)
  @Id 
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE_GENERATOR")
  @Column(name="Id", nullable=false)
  private Long id;
  @Column(name="Title", nullable=false, length=50)
  private String title = "";
  @Column(name="Description", nullable=false, length=1000)
  private String description = "";
  @Column(name="Version", nullable=false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date version = new Date();
  @OneToMany(mappedBy="owner", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
  private List<Card> cards = new LinkedList<Card>();
  
  protected Cardbox() { }
  
  public Cardbox(String title, String description, Date version) {
    this.title = title;
    this.description = description;
    this.version = version;
  }
  
  public void setTitle(String title) {
    if (title != null && title.length() <= 50) {
      this.title = title;
      setVersion();
    } else {
      throw new IllegalArgumentException();
    }
  }

  public String getTitle() {
    return title;
  }

  public void setDescription(String desc) {
    if (desc != null && desc.length() <= 1000) {
      this.description = desc;
      setVersion();
    } else {
      throw new IllegalArgumentException();
    }
  }

  public String getDescription() {
    return description;
  }

  private void setVersion() {
    version = new Date();
  }

  public Date getVersion() {
    return version;
  }

  public List<Card> getCards() {
    return cards;
  }
  
  protected Long getId() {
    return id;
  }

}
